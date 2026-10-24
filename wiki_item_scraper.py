#!/usr/bin/env python3
"""
RS Wiki item scraper -> reviewable SQL that links equipment rows to game item IDs.

Replaces wiki_equipment_update_scraper.py. Two phases, each reviewable on its own:

  fetch  Pulls wiki data into JSON files. Never touches the database.
         - Bucket infobox_bonuses: which pages are equippable
         - Bucket infobox_item: one row per version, with its item IDs
         - raw wikitext of each equippable page, keeping only its Infobox Bonuses template,
           so stat columns keep the same format as existing rows
         - Category:Equipment members, for a coverage report

  plan   Reads the database in a read-only session, diffs it against the fetched data and
         writes reports plus apply.sql. Never writes to the database.

Applying is deliberately manual. Review the reports and apply.sql, run it on a dev copy first:
  psql -X -v ON_ERROR_STOP=1 -d <dev copy> -f wiki-scrape/plan/apply.sql
apply.sql runs in one transaction and rolls back if any invariant check fails.

Rules:
- Equippable items only: pages with an Infobox Bonuses template.
- Existing rows keep their id. Only scraped columns are updated, and only when the wiki has a
  value. effects, class, slot, style, type, title and id are never written for existing rows.
- Rows that were never scraped (no raw_infobox, i.e. inserted by hand) only get wiki_key and item
  IDs. What the wiki would change on them goes to manual_rows_withheld.csv.
- Rows are keyed by wiki_key: the page title, or "Page#version" for extra stat versions.
  The first run matches existing rows by exact title, then by wiki redirect. An exact title
  beats a redirect; the redirect row is reported as superseded.
- A page whose versions differ in stats is split only when every item version names a stat
  version. The page row keeps version 1; the others are new rows linked to it. Otherwise the
  page keeps one row with version 1's stats and all its IDs, and versions_collapsed.csv says so.
- Broken versions, auras and sigils are left out and reported.
- Dyed, augmented, ornament and upgraded variants get their own row, linked to the root item
  through variant_of_id. New variant rows copy effects and curated enums from the root.
- Anything ambiguous goes to a report instead of being guessed. Resolve it with --overrides.

Wiki access follows https://runescape.wiki/w/Help:APIs and RS:PLUGIN: descriptive
User-Agent with contact, Bucket/MediaWiki API only, sequential throttled requests, maxlag.
Wiki content is CC BY-NC-SA 3.0.

Install:
  python3 -m venv .venv && .venv/bin/pip install -r requirements-wiki-scraper.txt

Run:
  .venv/bin/python wiki_item_scraper.py fetch
  DB_URL=postgresql://... .venv/bin/python wiki_item_scraper.py plan

Test:
  .venv/bin/python wiki_item_scraper.py fetch --page "Abyssal whip" --page "Ring of slaying"
  .venv/bin/python -m unittest test_wiki_item_scraper
"""

import argparse
import csv
import json
import math
import os
import re
import time
from collections import Counter, defaultdict
from dataclasses import dataclass, field
from datetime import datetime, timezone
from pathlib import Path
from typing import Any, Dict, Iterable, List, Optional, Set, Tuple

try:
    from dotenv import load_dotenv
except ModuleNotFoundError:
    pass
else:
    load_dotenv()

API = "https://runescape.wiki/api.php"
USER_AGENT = "rotdb-item-scraper/1.0 (+https://github.com/justice-ma/rotdb; discord .wxrl)"
BUCKET_PAGE_SIZE = 5000
WIKITEXT_BATCH = 50
ENUM_DIR = Path(__file__).resolve().parent / "src/main/java/com/rotdb/shared/combat/domain/model/enums"

# Written on existing rows when the wiki has a value. Everything else on an existing row is left alone.
SCRAPED_COLS = [
    "name",
    "tier",
    "damagetier",
    "accuracytier",
    "strength",
    "ranged",
    "magic",
    "necromancy",
    "prayer",
    "requirements_raw",
    "req_strength",
    "raw_infobox",
    "accuracy",
    "accuracy_tier",
    "armour",
    "armour_damage_tier",
    "armour_tier",
    "attack_range",
    "damage",
    "damage_bonus",
    "damage_tier",
    "defensive_bonus",
    "equippable",
    "images",
    "invtier",
    "level_requirement",
    "life",
    "members",
    "pvm_reduction",
    "pvp_reduction",
    "requirements",
    "speed",
]

# Scraped once, then converted to system enums by hand. Only filled on new rows.
CURATED_COLS = ["class", "slot", "style", "type"]
ENUM_FILES = {"class": "CombatStyles", "slot": "Slots", "style": "WeaponStyle", "type": "EquipmentType", "effects": "Effect"}

# Columns that differ between versions without making them different equipment.
IDENTITY_EXCLUDED = {"name", "raw_infobox", "images"}

VARCHAR_LIMITS = {
    "title": 255,
    "name": 255,
    "class": 255,
    "slot": 255,
    "style": 255,
    "type": 255,
    "requirements_raw": 255,
    "level_requirement": 255,
    "requirements": 255,
    "images": 1000,
    "wiki_key": 512,
    "wiki_page": 255,
    "version_anchor": 255,
    "item_name": 255,
}

# Same title conventions the wiki's own Module:Variant uses. Anything else shaped like
# "Base (suffix)" is reported as a possible variant instead of being linked.
VARIANT_RULES = [
    ("dye", re.compile(r"(?P<base>.+) \((?:blood|ice|shadow|third age|barrows|aurora|soul)\)", re.IGNORECASE)),
    ("ornament", re.compile(r"(?P<base>.+) \((?:or|g|t|i)\)", re.IGNORECASE)),
    ("upgrade", re.compile(r"(?P<base>.+?) \+ ?[1-5]")),
    ("augmented", re.compile(r"augmented (?P<base>.+)", re.IGNORECASE)),
]
_POSSIBLE_VARIANT = re.compile(r"(?P<base>.+) \((?P<suffix>[^()]+)\)")
_BROKEN = re.compile(r"\bbroken\b", re.IGNORECASE)

# Learned raw->enum conversions are only trusted when this share of existing rows agrees.
ENUM_MAPPING_MIN_SHARE = 0.9
ENUM_MAPPING_MIN_ROWS = 3


# --- wiki value normalization (ported from wiki_equipment_update_scraper.py so formats match) ---

def normalize_key(raw: Any) -> str:
    """Normalize wiki infobox keys before all lookups.

    Older data can contain keys such as "armour\n"; wiki templates may also use
    underscores. Keep this centralized so extraction and lookup agree.
    """
    return re.sub(r"\s+", " ", str(raw).strip().lower().replace("_", " "))


def norm_title(raw: str) -> str:
    return re.sub(r"\s+", " ", raw.replace("_", " ")).strip().lower()


def first_present(params: Dict[str, str], *keys: str) -> Optional[str]:
    for k in keys:
        normalized = normalize_key(k)
        if normalized in params:
            v = params.get(normalized)
            if v is not None:
                v = str(v).strip()
                if v != "":
                    return v
    return None


def to_int(raw: Optional[str]) -> Optional[int]:
    f = to_float(raw)
    return None if f is None else int(f)


def to_float(raw: Optional[str]) -> Optional[float]:
    if raw is None:
        return None
    s = str(raw).strip().replace(",", "")
    if not s:
        return None
    try:
        f = float(s)
    except ValueError:
        return None
    return f if math.isfinite(f) else None


def clean_text(raw: Optional[str]) -> Optional[str]:
    if raw is None:
        return None
    v = " ".join(str(raw).strip().split())
    if not v:
        return None
    if v.lower() in {"n/a", "na", "unknown"}:
        return None
    return v


_req_strength_re = re.compile(r"\{\{\s*sc\s*\|\s*strength\s*\|\s*(\d+)\s*\}\}", re.IGNORECASE)


def parse_strength_requirement(requirements_raw: Optional[str]) -> Optional[int]:
    if not requirements_raw:
        return None
    m = _req_strength_re.search(requirements_raw)
    return int(m.group(1)) if m else None


def normalize_slot(raw_slot: Optional[str]) -> Optional[str]:
    s = clean_text(raw_slot)
    if not s:
        return None

    s = re.sub(r"\s+", " ", s.lower().replace("-", " ")).strip()

    mapping = {
        "head": "HEAD",
        "body": "BODY",
        "torso": "BODY",
        "chest": "BODY",
        "gloves": "GLOVES",
        "hands": "GLOVES",
        "legs": "LEGS",
        "boots": "BOOTS",
        "feet": "BOOTS",
        "pocket": "POCKET",
        "off hand": "OFFHAND",
        "offhand": "OFFHAND",
        "main hand": "MAINHAND",
        "mainhand": "MAINHAND",
        "weapon": "MAINHAND",
        "ammo": "AMMO",
        "ammunition": "AMMO",
        "ring": "RING",
        "neck": "NECK",
        "cape": "CAPE",
        "quiver": "QUIVER",
        "shield": "OFFHAND",
        "2h": "TWOHANDED",
        "2 handed": "TWOHANDED",
        "two handed": "TWOHANDED",
        "two hand": "TWOHANDED",
    }

    if s in mapping:
        return mapping[s]
    if "2h" in s or "two hand" in s:
        return "TWOHANDED"
    return s.upper().replace(" ", "_")


def normalize_class(raw_class: Optional[str]) -> Optional[str]:
    s = clean_text(raw_class)
    if not s:
        return None
    s = s.lower()
    for needle, value in (("melee", "MELEE"), ("magic", "MAGIC"), ("ranged", "RANGED"), ("necromancy", "NECROMANCY")):
        if needle in s:
            return value
    if s in {"all", "hybrid", "any", "none"}:
        return "ALL"
    return None


def normalize_weapon_style(raw_type: Optional[str], raw_style: Optional[str], raw_name: Optional[str] = None) -> str:
    joined = " ".join([x for x in [clean_text(raw_type), clean_text(raw_style), clean_text(raw_name)] if x]).lower()

    if not joined:
        return "NONE"
    if "slash" in joined:
        return "SLASH"
    if "stab" in joined:
        return "STAB"
    if "crush" in joined or "maul" in joined or "hammer" in joined:
        return "CRUSH"
    if "arrow" in joined or "bow" in joined:
        return "ARROW"
    if "bolt" in joined or "crossbow" in joined:
        return "BOLT"
    if "thrown" in joined or "javelin" in joined or "knife" in joined or "dart" in joined:
        return "THROWN"
    if "magic" in joined or "spell" in joined or "staff" in joined or "wand" in joined or "orb" in joined:
        return "SPELL"
    return "NONE"


def normalize_equipment_type(raw_type: Optional[str]) -> Optional[str]:
    s = clean_text(raw_type)
    if not s:
        return None
    return re.sub(r"[\s,]+", " ", s.lower().replace("-", " ")).strip().upper().replace(" ", "_")


def scraped_values(title: str, params: Dict[str, str]) -> Dict[str, Any]:
    requirements_raw = clean_text(first_present(params, "requirements", "requirement", "reqs"))

    members = None
    members_raw = clean_text(first_present(params, "members"))
    if members_raw:
        m = members_raw.lower()
        if m in {"yes", "true", "member", "members"}:
            members = True
        elif m in {"no", "false", "free", "free-to-play", "f2p"}:
            members = False

    return {
        "name": clean_text(first_present(params, "name")) or title,
        "tier": to_int(first_present(params, "tier", "weapontier", "weapon tier")),
        "damagetier": to_int(first_present(params, "damagetier", "damage tier")),
        "accuracytier": to_int(first_present(params, "accuracytier", "accuracy tier")),
        "strength": to_float(first_present(params, "strength", "str")),
        "ranged": to_float(first_present(params, "ranged")),
        "magic": to_float(first_present(params, "magic")),
        "necromancy": to_float(first_present(params, "necromancy")),
        "prayer": to_float(first_present(params, "prayer")),
        "requirements_raw": requirements_raw,
        "req_strength": parse_strength_requirement(requirements_raw),
        "raw_infobox": params,
        "accuracy": to_float(first_present(params, "accuracy")),
        "accuracy_tier": to_int(first_present(params, "accuracy tier", "accuracytier")),
        "armour": to_float(first_present(params, "armour", "armor")),
        "armour_damage_tier": to_int(first_present(params, "armour damage tier", "armor damage tier")),
        "armour_tier": to_int(first_present(params, "armour tier", "armor tier")),
        "attack_range": to_float(first_present(params, "attack range", "range")),
        "damage": to_float(first_present(params, "damage")),
        "damage_bonus": to_float(first_present(params, "damage bonus")),
        "damage_tier": to_int(first_present(params, "damage tier", "damagetier")),
        "defensive_bonus": to_float(first_present(params, "defensive bonus")),
        "equippable": True,
        "images": clean_text(first_present(params, "image", "image1", "images")),
        "invtier": to_int(first_present(params, "invention tier", "invtier")),
        "level_requirement": clean_text(first_present(params, "level requirement")),
        "life": to_float(first_present(params, "life")),
        "members": members,
        "pvm_reduction": to_float(first_present(params, "pvm reduction")),
        "pvp_reduction": to_float(first_present(params, "pvp reduction")),
        "requirements": requirements_raw,
        "speed": to_float(first_present(params, "speed")),
    }


def raw_curated(params: Dict[str, str]) -> Dict[str, Optional[str]]:
    return {
        "class": clean_text(first_present(params, "class", "clazz", "combat style", "combatstyle")),
        "slot": clean_text(first_present(params, "slot", "equipment slot", "equip slot")),
        "style": clean_text(first_present(params, "style", "attack style", "weaponstyle")),
        "type": clean_text(first_present(params, "type", "weapon type")),
    }


# --- infobox versions and variants ---

_VERSION_KEY = re.compile(r"version(\d+)")
_SUFFIXED_KEY = re.compile(r"(.*\D)(\d+)")


def template_params(template_wikitext: str) -> Dict[str, str]:
    import mwparserfromhell

    tpl = mwparserfromhell.parse(template_wikitext).filter_templates(recursive=False)[0]
    params: Dict[str, str] = {}
    for p in tpl.params:
        k = normalize_key(p.name)
        if k:
            params[k] = str(p.value).strip()
    return params


def split_versions(params: Dict[str, str]) -> List[Tuple[Optional[str], Dict[str, str]]]:
    """Resolve a versioned infobox into one param set per version.

    Wiki infoboxes use |param = shared and |paramN = override for version N. Unversioned
    infoboxes come back as a single (None, params) entry.
    """
    anchors: Dict[int, str] = {}
    for key, value in params.items():
        m = _VERSION_KEY.fullmatch(key)
        if m and clean_text(value):
            anchors[int(m.group(1))] = clean_text(value)
    if not anchors:
        return [(None, params)]

    shared: Dict[str, str] = {}
    per_version: Dict[int, Dict[str, str]] = defaultdict(dict)
    for key, value in params.items():
        if _VERSION_KEY.fullmatch(key):
            continue
        m = _SUFFIXED_KEY.fullmatch(key)
        if m and int(m.group(2)) in anchors:
            if str(value).strip():
                per_version[int(m.group(2))][m.group(1).strip()] = value
        else:
            shared[key] = value

    return [(anchors[i], {**shared, **per_version[i]}) for i in sorted(anchors)]


def is_broken(label: Optional[str]) -> bool:
    return bool(label) and bool(_BROKEN.search(label))


def variant_parent(title: str) -> Optional[Tuple[str, str]]:
    for kind, rx in VARIANT_RULES:
        m = rx.fullmatch(title)
        if m:
            return kind, m.group("base").strip()
    return None


def resolve_variant_root(page: str, pages_by_norm: Dict[str, str]) -> Optional[Tuple[str, Optional[str], str]]:
    """Follow variant title rules up to the root page.

    Returns (kind, root_page, base_title), with root_page None when a base page is missing,
    or None when the page isn't a variant.
    """
    parent = variant_parent(page)
    if not parent:
        return None
    kind, base = parent
    current, seen = page, {page}
    while parent:
        target = pages_by_norm.get(norm_title(parent[1]))
        if target is None:
            return kind, None, parent[1]
        if target in seen:
            break
        seen.add(target)
        current = target
        parent = variant_parent(current)
    return kind, current, base


# --- wiki records ---

@dataclass
class WikiRecord:
    wiki_key: str
    page: str
    anchors: List[Optional[str]]
    values: Dict[str, Any]
    raw_curated: Dict[str, Optional[str]]
    revid: Optional[int]
    item_ids: List[Tuple[int, Optional[str], Optional[str]]] = field(default_factory=list)
    variant_kind: Optional[str] = None
    root_key: Optional[str] = None


def identity(values: Dict[str, Any], curated: Dict[str, Optional[str]]) -> Tuple:
    return tuple((c, values.get(c)) for c in SCRAPED_COLS if c not in IDENTITY_EXCLUDED) + tuple(sorted(curated.items()))


def is_unsupported_slot(raw_slot: Optional[str]) -> bool:
    s = (raw_slot or "").lower()
    return "aura" in s or "sigil" in s


def build_page_records(page: str, params: Dict[str, str], revid: Optional[int],
                       item_versions: List[Dict[str, Any]], reports: "Reports") -> List[WikiRecord]:
    """One record per distinct stat version, keyed so the page title is always the primary record.

    Versions are only split when every item version names one of the stat versions. Otherwise
    an item ID can't pick a stat version, so the page keeps one record with version 1's stats.
    """
    versions = []
    for anchor, resolved in split_versions(params):
        if is_broken(anchor):
            reports.add("excluded", page, anchor, "broken version")
        elif is_unsupported_slot(raw_curated(resolved)["slot"]):
            reports.add("excluded", page, anchor, "aura/sigil slot, not in the Slots enum")
        else:
            versions.append((anchor, resolved))
    if not versions:
        return []

    groups: Dict[Tuple, List[Tuple[Optional[str], Dict[str, Any], Dict[str, Optional[str]]]]] = {}
    for anchor, resolved in versions:
        values = scraped_values(page, resolved)
        curated = raw_curated(resolved)
        groups.setdefault(identity(values, curated), []).append((anchor, values, curated))
    group_list = list(groups.values())

    live_items: List[Tuple[Optional[str], Optional[str], List[int]]] = []
    for version in item_versions:
        anchor = version.get("version_anchor") or None
        item_name = version.get("item_name")
        ids = []
        for raw_id in version.get("item_id") or []:
            try:
                ids.append(int(raw_id))
            except (TypeError, ValueError):
                reports.add("excluded", page, anchor, f"unparseable item id {raw_id!r}")
        if is_broken(anchor) or is_broken(item_name):
            for item_id in ids:
                reports.add("excluded", page, anchor, f"broken version, item id {item_id}")
            continue
        live_items.append((anchor, item_name, ids))

    bonus_anchors = {(a or "").lower() for a, _ in versions}
    split = bool(live_items) and all((a or "").lower() in bonus_anchors for a, _, _ in live_items)
    if len(group_list) > 1 and not split:
        firsts = [g[0] for g in group_list]
        differing = [c for c in SCRAPED_COLS + CURATED_COLS if c not in IDENTITY_EXCLUDED
                     and len({repr(cur[c] if c in CURATED_COLS else vals.get(c)) for _, vals, cur in firsts}) > 1]
        reports.add("versions_collapsed", page, group_list[0][0][0], [m[0] for g in group_list[1:] for m in g], differing)
        group_list = group_list[:1]

    item_names = {(a or "").lower(): n for a, n, _ in live_items}

    def version_name(anchor: Optional[str]) -> str:
        name = item_names.get((anchor or "").lower())
        return name if name and norm_title(name) != norm_title(page) else f"{page} ({anchor})"

    records = []
    for i, members in enumerate(group_list):
        anchor0, values0, curated0 = members[0]
        values = dict(values0)
        if i == 0:
            values["raw_infobox"] = params
        if len(group_list) == 1:
            values["name"] = clean_text(first_present(params, "name")) or page
        else:
            values["name"] = version_name(anchor0)
        record = WikiRecord(page if i == 0 else f"{page}#{anchor0}", page, [m[0] for m in members], values, curated0, revid)
        if i > 0:
            record.variant_kind, record.root_key = "version", page
        records.append(record)

    for anchor, item_name, ids in live_items:
        target = records[0]
        if len(records) > 1:
            target = next(r for r in records if (anchor or "").lower() in {(a or "").lower() for a in r.anchors})
        for item_id in ids:
            target.item_ids.append((item_id, anchor, item_name))

    return records


# --- reports ---

REPORT_HEADERS = {
    "rows_added": ["wiki_key", "title", "name", "class", "slot", "style", "type", "variant_kind", "root_wiki_key", "root_effects_now", "item_ids"],
    "rows_changed": ["id", "title", "column", "old", "new"],
    "manual_rows_withheld": ["id", "title", "column", "db_value", "wiki_value"],
    "db_rows_unmatched": ["id", "title", "effects", "reason"],
    "ambiguous": ["id", "title", "detail"],
    "id_conflicts": ["item_id", "wiki_keys"],
    "rows_without_id": ["id", "wiki_key", "title", "effects", "reason"],
    "variant_tag_gaps": ["id", "wiki_key", "title", "kind", "root_title", "missing_tags", "extra_tags"],
    "possible_variants": ["page", "suffix_or_reason", "base", "base_effects", "own_effects"],
    "excluded": ["page", "version", "reason"],
    "versions_collapsed": ["page", "kept_version", "hidden_versions", "differing_columns"],
    "enum_mapping": ["column", "raw_value", "chosen", "agreeing_rows", "total_rows", "accepted", "all_values"],
    "enum_unmapped": ["wiki_key", "column", "raw_value"],
    "enum_disagreements": ["id", "title", "column", "db_value", "wiki_value"],
    "coverage": ["page", "issue"],
}


class Reports:
    def __init__(self) -> None:
        self.rows: Dict[str, List[Tuple]] = defaultdict(list)

    def add(self, name: str, *row: Any) -> None:
        assert name in REPORT_HEADERS, name
        self.rows[name].append(row)

    def write(self, out_dir: Path) -> Dict[str, int]:
        counts = {}
        for name, header in REPORT_HEADERS.items():
            with open(out_dir / f"{name}.csv", "w", encoding="utf-8", newline="") as f:
                w = csv.writer(f)
                w.writerow(header)
                for row in self.rows.get(name, []):
                    w.writerow([csv_cell(v) for v in row])
            counts[name] = len(self.rows.get(name, []))
        return counts


def csv_cell(v: Any) -> Any:
    if v is None:
        return ""
    if isinstance(v, (dict, list, tuple, set)):
        return json.dumps(sorted(v) if isinstance(v, set) else v, ensure_ascii=False, default=str)
    return v


# --- wiki client ---

class WikiClient:
    def __init__(self, api: str, user_agent: str, sleep_s: float) -> None:
        import requests

        self.api = api
        self.sleep_s = sleep_s
        self.session = requests.Session()
        self.session.headers.update({"User-Agent": user_agent})
        self.last_request = 0.0
        self.requests_made = 0

    def get(self, params: Dict[str, str]) -> Dict[str, Any]:
        params = {**params, "format": "json", "maxlag": "5"}
        for attempt in range(6):
            wait = self.sleep_s - (time.monotonic() - self.last_request)
            if wait > 0:
                time.sleep(wait)
            self.last_request = time.monotonic()
            self.requests_made += 1
            r = self.session.get(self.api, params=params, timeout=(10, 60))

            if r.status_code in (429, 500, 502, 503, 504):
                self._back_off(r, attempt)
                continue
            r.raise_for_status()
            data = r.json()
            error = data.get("error")
            if isinstance(error, dict) and error.get("code") == "maxlag":
                self._back_off(r, attempt)
                continue
            if error:
                raise RuntimeError(f"wiki API error for {params}: {error}")
            return data
        raise RuntimeError(f"wiki API kept failing for {params}")

    def _back_off(self, response: Any, attempt: int) -> None:
        try:
            wait = float(response.headers.get("Retry-After", ""))
        except ValueError:
            wait = 0
        wait = max(wait, min(60.0, 5.0 * 2 ** attempt))
        print(f"  backing off {wait:.0f}s (HTTP {response.status_code})", flush=True)
        time.sleep(wait)

    def bucket(self, name: str, fields: List[str], order_by: str) -> List[Dict[str, Any]]:
        rows: List[Dict[str, Any]] = []
        select = ",".join(f"'{f}'" for f in fields)
        while True:
            query = (f"bucket('{name}').select({select}).orderBy('{order_by}','asc')"
                     f".limit({BUCKET_PAGE_SIZE}).offset({len(rows)}).run()")
            page = self.get({"action": "bucket", "query": query}).get("bucket", [])
            rows.extend(page)
            print(f"  {name}: {len(rows)} rows", flush=True)
            if len(page) < BUCKET_PAGE_SIZE:
                return rows

    def category_members(self, category: str) -> List[str]:
        titles: List[str] = []
        cont: Dict[str, str] = {}
        while True:
            data = self.get({"action": "query", "list": "categorymembers", "cmtitle": category,
                             "cmnamespace": "0", "cmlimit": "max", "formatversion": "2", **cont})
            titles.extend(m["title"] for m in data.get("query", {}).get("categorymembers", []))
            if "continue" not in data:
                return titles
            cont = data["continue"]

    def wikitext(self, titles: List[str]) -> Dict[str, Tuple[Optional[int], str]]:
        data = self.get({"action": "query", "prop": "revisions", "rvprop": "content|ids", "rvslots": "main",
                         "titles": "|".join(titles), "formatversion": "2"})
        out = {}
        for page in data.get("query", {}).get("pages", []):
            revs = page.get("revisions") or []
            if revs:
                out[page["title"]] = (revs[0].get("revid"), revs[0]["slots"]["main"].get("content", ""))
        return out

    def redirect_targets(self, titles: List[str]) -> Dict[str, Optional[str]]:
        data = self.get({"action": "query", "titles": "|".join(titles), "redirects": "1", "formatversion": "2"})
        query = data.get("query", {})
        normalized = {n["from"]: n["to"] for n in query.get("normalized", [])}
        redirects = {r["from"]: r["to"] for r in query.get("redirects", [])}
        return {t: redirects.get(normalized.get(t, t)) for t in titles}

    def namespaces(self) -> List[str]:
        data = self.get({"action": "query", "meta": "siteinfo", "siprop": "namespaces|namespacealiases", "formatversion": "2"})
        names = set()
        for ns in data["query"]["namespaces"].values():
            if ns["id"] != 0:
                names.update(n for n in (ns.get("name"), ns.get("canonical")) if n)
        names.update(a["alias"] for a in data["query"].get("namespacealiases", []))
        return sorted(names)


def chunks(items: List[Any], size: int) -> Iterable[List[Any]]:
    for i in range(0, len(items), size):
        yield items[i:i + size]


# --- fetch ---

def cmd_fetch(args: argparse.Namespace) -> None:
    import mwparserfromhell

    out = Path(args.out_dir)
    out.mkdir(parents=True, exist_ok=True)
    client = WikiClient(args.api, args.user_agent, args.sleep)

    print("Fetching namespaces...", flush=True)
    namespaces = client.namespaces()
    ns_prefixes = {n.lower() for n in namespaces}

    def mainspace(title: str) -> bool:
        return ":" not in title or title.split(":", 1)[0].lower() not in ns_prefixes

    print("Fetching Bucket infobox_bonuses...", flush=True)
    bonuses = [r for r in client.bucket("infobox_bonuses", ["page_name", "page_name_sub"], "page_name_sub")
               if mainspace(r["page_name"])]
    print("Fetching Bucket infobox_item...", flush=True)
    items = [r for r in client.bucket("infobox_item", ["page_name", "page_name_sub", "version_anchor", "item_name",
                                                       "item_id", "item_id_historical"], "page_name_sub")
             if mainspace(r["page_name"])]
    print("Fetching Category:Equipment...", flush=True)
    category = client.category_members("Category:Equipment")

    pages = sorted({r["page_name"] for r in bonuses})
    if args.page:
        pages = [p for p in pages if p in set(args.page)]
    if args.max_pages is not None:
        pages = pages[:args.max_pages]

    print(f"Fetching wikitext for {len(pages)} equippable pages...", flush=True)
    wikitext: Dict[str, Dict[str, Any]] = {}
    for batch in chunks(pages, WIKITEXT_BATCH):
        for title, (revid, text) in client.wikitext(batch).items():
            templates = [str(t) for t in mwparserfromhell.parse(text).filter_templates(recursive=True)
                         if normalize_key(t.name) == "infobox bonuses"]
            wikitext[title] = {"revid": revid, "templates": templates}
        print(f"  wikitext: {len(wikitext)}/{len(pages)}", flush=True)

    def dump(name: str, value: Any) -> None:
        with open(out / name, "w", encoding="utf-8") as f:
            json.dump(value, f, ensure_ascii=False, indent=1, sort_keys=True)
            f.write("\n")

    dump("bonuses_bucket.json", bonuses)
    dump("item_bucket.json", items)
    dump("category_equipment.json", category)
    dump("bonuses_wikitext.json", wikitext)
    dump("meta.json", {
        "fetched_at": datetime.now(timezone.utc).isoformat(timespec="seconds"),
        "api": args.api,
        "user_agent": args.user_agent,
        "requests": client.requests_made,
        "partial": bool(args.page or args.max_pages is not None),
        "counts": {"bonuses_rows": len(bonuses), "item_rows": len(items), "category_pages": len(category),
                   "wikitext_pages": len(wikitext)},
        "licence": "RuneScape Wiki content, CC BY-NC-SA 3.0",
    })
    print(f"Done: {client.requests_made} requests, data in {out}", flush=True)


# --- plan ---

def load_enums(enum_dir: Path) -> Dict[str, List[str]]:
    out = {}
    for col, cls in ENUM_FILES.items():
        text = (enum_dir / f"{cls}.java").read_text(encoding="utf-8")
        body = text[text.index("{") + 1:]
        body = body[:min(i for i in (body.find(";"), body.find("}")) if i >= 0)]
        out[col] = re.findall(r"\b([A-Z][A-Z0-9_]*)\b", body)
    return out


def connect_readonly(db_url: str) -> Any:
    import psycopg2

    conn = psycopg2.connect(db_url, options="-c default_transaction_read_only=on")
    conn.set_session(readonly=True)
    return conn


@dataclass
class DbState:
    database: str
    rows: Dict[int, Dict[str, Any]]
    has_v11: bool
    item_ids: Dict[int, Tuple[int, str]]


def load_db(db_url: str) -> DbState:
    from psycopg2.extras import RealDictCursor

    conn = connect_readonly(db_url)
    try:
        with conn.cursor(cursor_factory=RealDictCursor) as cur:
            cur.execute("select current_database() as db")
            database = cur.fetchone()["db"]
            cur.execute("select column_name from information_schema.columns "
                        "where table_schema = current_schema() and table_name = 'equipment'")
            columns = {r["column_name"] for r in cur.fetchall()}
            cur.execute("select to_regclass('equipment_item_id') is not null as has_table")
            has_v11 = {"wiki_key", "variant_of_id"} <= columns and cur.fetchone()["has_table"]

            select = ["id", "title", "effects"] + CURATED_COLS + SCRAPED_COLS
            if has_v11:
                select += ["wiki_key", "variant_of_id"]
            cur.execute(f"select {', '.join(select)} from equipment")
            rows = {r["id"]: dict(r) for r in cur.fetchall()}
            for r in rows.values():
                r.setdefault("wiki_key", None)
                r.setdefault("variant_of_id", None)

            item_ids = {}
            if has_v11:
                cur.execute("select item_id, equipment_id, source from equipment_item_id")
                item_ids = {r["item_id"]: (r["equipment_id"], r["source"]) for r in cur.fetchall()}
        return DbState(database, rows, has_v11, item_ids)
    finally:
        conn.close()


def learn_enum_mapping(rows: Iterable[Dict[str, Any]], valid: Dict[str, List[str]],
                       reports: Reports) -> Dict[str, Dict[str, str]]:
    """Learn how raw wiki values were converted to system enums on existing rows."""
    seen: Dict[str, Dict[str, Counter]] = {c: defaultdict(Counter) for c in CURATED_COLS}
    for row in rows:
        raw = row.get("raw_infobox")
        if not isinstance(raw, dict):
            continue
        curated = raw_curated({normalize_key(k): str(v) for k, v in raw.items()})
        for col in CURATED_COLS:
            if row.get(col) is not None:
                seen[col][(curated[col] or "").lower()][row[col]] += 1

    mapping: Dict[str, Dict[str, str]] = {c: {} for c in CURATED_COLS}
    for col in CURATED_COLS:
        for raw_value, counter in sorted(seen[col].items()):
            chosen, agreeing = counter.most_common(1)[0]
            total = sum(counter.values())
            accepted = (chosen in valid[col] and total >= ENUM_MAPPING_MIN_ROWS
                        and agreeing / total >= ENUM_MAPPING_MIN_SHARE)
            if accepted:
                mapping[col][raw_value] = chosen
            reports.add("enum_mapping", col, raw_value, chosen, agreeing, total, accepted, dict(counter))
    return mapping


def curated_for(record: WikiRecord, mapping: Dict[str, Dict[str, str]],
                valid: Dict[str, List[str]]) -> Dict[str, Optional[str]]:
    raw = record.raw_curated
    out: Dict[str, Optional[str]] = {}
    for col in CURATED_COLS:
        key = (raw[col] or "").lower()
        if key in mapping[col]:
            value: Optional[str] = mapping[col][key]
        elif col == "class":
            value = normalize_class(raw[col])
        elif col == "slot":
            value = normalize_slot(raw[col])
        elif col == "style":
            value = normalize_weapon_style(raw["type"], raw["style"], record.page)
        else:
            value = normalize_equipment_type(raw[col])
        out[col] = value if value in valid[col] else None
    return out


def same_value(old: Any, new: Any) -> bool:
    if isinstance(old, (int, float)) and isinstance(new, (int, float)) and not isinstance(old, bool):
        return float(old) == float(new)
    return old == new


def too_long(col: str, value: Any) -> bool:
    return isinstance(value, str) and col in VARCHAR_LIMITS and len(value) > VARCHAR_LIMITS[col]


def load_data(data_dir: Path) -> Dict[str, Any]:
    def load(name: str) -> Any:
        with open(data_dir / name, encoding="utf-8") as f:
            return json.load(f)

    return {name: load(f"{name}.json") for name in
            ("meta", "bonuses_bucket", "item_bucket", "category_equipment", "bonuses_wikitext")}


def build_records(data: Dict[str, Any], reports: Reports) -> Dict[str, WikiRecord]:
    items_by_page: Dict[str, List[Dict[str, Any]]] = defaultdict(list)
    for row in data["item_bucket"]:
        items_by_page[row["page_name"]].append(row)

    records: Dict[str, WikiRecord] = {}
    for page, entry in sorted(data["bonuses_wikitext"].items()):
        templates = entry["templates"]
        if page.lower().endswith("(broken)"):
            reports.add("excluded", page, None, "broken item page")
            continue
        if len(templates) != 1:
            reports.add("excluded", page, None, f"{len(templates)} Infobox Bonuses templates on the page")
            continue
        for record in build_page_records(page, template_params(templates[0]), entry["revid"],
                                         items_by_page.get(page, []), reports):
            records[record.wiki_key] = record

    # Every page's primary record is keyed by the page title, so a variant's root is its root page.
    pages_by_norm = {norm_title(r.page): r.page for r in records.values()}
    for r in records.values():
        resolved = resolve_variant_root(r.page, pages_by_norm)
        if not resolved:
            continue
        kind, root_page, base = resolved
        if root_page is None:
            if r.wiki_key == r.page:
                reports.add("possible_variants", r.page, f"{kind}: base page not found", base, None, None)
            continue
        r.variant_kind, r.root_key = kind, root_page

    category = set(data["category_equipment"])
    bonus_pages = {r["page_name"] for r in data["bonuses_bucket"]}
    for page in sorted(category - bonus_pages):
        reports.add("coverage", page, "in Category:Equipment without an Infobox Bonuses")
    for page in sorted(bonus_pages - category):
        reports.add("coverage", page, "has Infobox Bonuses but not in Category:Equipment")
    for page in sorted(bonus_pages - set(data["bonuses_wikitext"])):
        reports.add("coverage", page, "wikitext not fetched")
    return records


def match_rows(db: DbState, records: Dict[str, WikiRecord], fetched_pages: Set[str], redirects: Dict[str, Optional[str]],
               overrides: Dict[str, str], reports: Reports) -> Tuple[Dict[int, str], Set[str]]:
    """Link existing rows to wiki records. Returns (row id -> wiki_key, blocked pages).

    A title match links to the page's primary record. When several rows claim one record, the
    row that matched exactly (title, wiki_key or override) wins over rows that matched by redirect.
    """
    primary = {norm_title(r.page): r.wiki_key for r in records.values() if r.wiki_key == r.page}
    excluded_pages = {norm_title(p) for p in fetched_pages} - set(primary)

    candidates: Dict[int, Tuple[str, bool]] = {}
    for row_id, row in sorted(db.rows.items()):
        if str(row_id) in overrides:
            key = overrides[str(row_id)]
            if key in records:
                candidates[row_id] = (key, True)
            else:
                reports.add("ambiguous", row_id, row["title"], f"override points at unknown wiki_key {key!r}")
            continue
        if row["wiki_key"]:
            if row["wiki_key"] in records:
                candidates[row_id] = (row["wiki_key"], True)
            else:
                reports.add("db_rows_unmatched", row_id, row["title"], row["effects"], "wiki_key no longer on the wiki")
            continue

        key = primary.get(norm_title(row["title"]))
        if key:
            candidates[row_id] = (key, True)
            continue
        target = redirects.get(row["title"])
        key = primary.get(norm_title(target)) if target else None
        if key:
            candidates[row_id] = (key, False)
        elif norm_title(row["title"]) in excluded_pages:
            reports.add("db_rows_unmatched", row_id, row["title"], row["effects"], "wiki page excluded, see excluded.csv")
        else:
            reports.add("db_rows_unmatched", row_id, row["title"], row["effects"], "no equippable wiki page with this title")

    claims: Dict[str, List[Tuple[int, bool]]] = defaultdict(list)
    for row_id, (key, exact) in candidates.items():
        claims[key].append((row_id, exact))
    links: Dict[int, str] = {}
    blocked: Set[str] = set()
    for key, claimants in claims.items():
        exact = [row_id for row_id, is_exact in claimants if is_exact]
        if len(claimants) == 1:
            links[claimants[0][0]] = key
        elif len(exact) == 1:
            links[exact[0]] = key
            for row_id, is_exact in claimants:
                if not is_exact:
                    row = db.rows[row_id]
                    reports.add("db_rows_unmatched", row_id, row["title"], row["effects"],
                                f"superseded: title redirects to {key!r}, which row {exact[0]} matches exactly")
        else:
            row_ids = [row_id for row_id, _ in claimants]
            for row_id in row_ids:
                reports.add("ambiguous", row_id, db.rows[row_id]["title"], f"several rows match wiki_key {key!r}: {row_ids}")
            blocked.add(records[key].page)
    return links, blocked


def cmd_plan(args: argparse.Namespace) -> None:
    data_dir, out = Path(args.data_dir), Path(args.out_dir)
    out.mkdir(parents=True, exist_ok=True)
    reports = Reports()
    data = load_data(data_dir)
    valid = load_enums(Path(args.enum_dir))
    overrides = {}
    if args.overrides:
        with open(args.overrides, encoding="utf-8") as f:
            overrides = json.load(f).get("link", {})

    print("Building wiki records...", flush=True)
    records = build_records(data, reports)
    print(f"  {len(records)} records", flush=True)

    print("Reading database (read-only session)...", flush=True)
    db = load_db(args.db_url)
    print(f"  {db.database}: {len(db.rows)} equipment rows, V11 applied: {db.has_v11}", flush=True)

    redirects: Dict[str, Optional[str]] = {}
    redirect_cache = data_dir / "redirects.json"
    if redirect_cache.exists():
        redirects = json.loads(redirect_cache.read_text(encoding="utf-8"))
    fetched_pages = set(data["bonuses_wikitext"])
    pages_norm = {norm_title(p) for p in fetched_pages}
    unresolved = sorted(r["title"] for r in db.rows.values()
                        if not r["wiki_key"] and norm_title(r["title"]) not in pages_norm and r["title"] not in redirects)
    if unresolved and not args.offline:
        print(f"Resolving redirects for {len(unresolved)} unmatched titles...", flush=True)
        client = WikiClient(args.api, args.user_agent, args.sleep)
        for batch in chunks(unresolved, WIKITEXT_BATCH):
            redirects.update(client.redirect_targets(batch))
        redirect_cache.write_text(json.dumps(redirects, ensure_ascii=False, indent=1, sort_keys=True) + "\n", encoding="utf-8")

    links, blocked = match_rows(db, records, fetched_pages, redirects, overrides, reports)
    row_by_key = {key: row_id for row_id, key in links.items()}
    linked_keys = set(links.values())
    new_records = [r for r in records.values() if r.wiki_key not in linked_keys and r.page not in blocked]
    new_records.sort(key=lambda r: (r.root_key is not None, r.wiki_key))
    available_roots = linked_keys | {r.wiki_key for r in new_records if r.root_key is None}
    mapping = learn_enum_mapping(db.rows.values(), valid, reports)

    def root_row(r: WikiRecord) -> Optional[Dict[str, Any]]:
        return db.rows.get(row_by_key.get(r.root_key)) if r.root_key else None

    for rec in records.values():
        if rec.root_key and rec.root_key not in available_roots:
            reports.add("ambiguous", row_by_key.get(rec.wiki_key), rec.page,
                        f"variant root {rec.root_key!r} is neither linked nor inserted; not linked, no tags copied")
            rec.root_key = None

    # Existing rows: scraped columns, wiki_key, variant links.
    updates: List[Tuple[int, Dict[str, Any]]] = []
    variant_links: List[Tuple[int, str]] = []
    change_counts: Counter = Counter()
    for row_id, key in sorted(links.items()):
        row, rec = db.rows[row_id], records[key]
        never_scraped = row.get("raw_infobox") is None
        changes: Dict[str, Any] = {}
        for col in SCRAPED_COLS:
            new = rec.values.get(col)
            if new is None or same_value(row.get(col), new):
                continue
            if never_scraped:
                reports.add("manual_rows_withheld", row_id, row["title"], col, row.get(col), new)
                continue
            if too_long(col, new):
                reports.add("excluded", rec.page, None, f"{col} longer than {VARCHAR_LIMITS[col]} chars; kept existing value on row {row_id}")
                continue
            changes[col] = new
            change_counts[col] += 1
            if col != "raw_infobox":
                reports.add("rows_changed", row_id, row["title"], col, row.get(col), new)
        if row["wiki_key"] != key:
            changes["wiki_key"] = key
        if changes:
            updates.append((row_id, changes))

        wiki_curated = curated_for(rec, mapping, valid)
        for col in CURATED_COLS:
            if wiki_curated[col] is not None and row.get(col) is not None and wiki_curated[col] != row[col]:
                reports.add("enum_disagreements", row_id, row["title"], col, row[col], wiki_curated[col])

        if rec.root_key and rec.root_key != key:
            root_id = row_by_key.get(rec.root_key)
            if row["variant_of_id"] is None:
                variant_links.append((row_id, rec.root_key))
            elif root_id is not None and row["variant_of_id"] != root_id:
                reports.add("ambiguous", row_id, row["title"], f"variant_of_id is {row['variant_of_id']}, wiki rules say {root_id}")
            root = root_row(rec)
            if root is not None:
                missing = set(root["effects"] or []) - set(row["effects"] or [])
                extra = set(row["effects"] or []) - set(root["effects"] or [])
                if missing:
                    reports.add("variant_tag_gaps", row_id, key, row["title"], rec.variant_kind, root["title"], missing, extra)

    # New rows: roots first so variants can copy from them inside the same transaction.
    inserts: List[Tuple[WikiRecord, Dict[str, Optional[str]]]] = []
    for rec in new_records:
        if too_long("title", rec.page) or too_long("name", rec.values["name"]) or too_long("wiki_key", rec.wiki_key):
            reports.add("excluded", rec.page, None, "title, name or wiki_key too long for its column")
            continue
        for col in SCRAPED_COLS:
            if too_long(col, rec.values.get(col)):
                reports.add("excluded", rec.page, None, f"{col} longer than {VARCHAR_LIMITS[col]} chars; left NULL")
                rec.values[col] = None
        curated = curated_for(rec, mapping, valid)
        for col in CURATED_COLS:
            if curated[col] is None and rec.raw_curated[col] is not None:
                reports.add("enum_unmapped", rec.wiki_key, col, rec.raw_curated[col])
        inserts.append((rec, curated))
        root = root_row(rec)
        reports.add("rows_added", rec.wiki_key, rec.page, rec.values["name"], curated["class"], curated["slot"],
                    curated["style"], curated["type"], rec.variant_kind, rec.root_key,
                    root["effects"] if root else None, [i[0] for i in rec.item_ids])
    inserted_keys = {r.wiki_key for r, _ in inserts}

    # Possible variants the title rules don't cover, so tags can be checked by hand.
    tagged_titles = {norm_title(r["title"]): r for r in db.rows.values() if r["effects"]}
    for rec in records.values():
        m = _POSSIBLE_VARIANT.fullmatch(rec.page)
        if rec.variant_kind or rec.wiki_key != rec.page or not m:
            continue
        base = tagged_titles.get(norm_title(m.group("base")))
        if base:
            own_id = row_by_key.get(rec.wiki_key)
            own = db.rows[own_id]["effects"] if own_id else None
            reports.add("possible_variants", rec.page, m.group("suffix"), base["title"], base["effects"], own)

    # Item IDs.
    planned_ids: Dict[int, Tuple[WikiRecord, Optional[str], Optional[str]]] = {}
    claimed: Dict[int, Set[str]] = defaultdict(set)
    first_version: Dict[Tuple[int, str], Tuple[Optional[str], Optional[str]]] = {}
    for rec in records.values():
        for item_id, anchor, item_name in rec.item_ids:
            claimed[item_id].add(rec.wiki_key)
            first_version.setdefault((item_id, rec.wiki_key), (anchor, item_name))
    for item_id, keys in sorted(claimed.items()):
        if len(keys) > 1:
            reports.add("id_conflicts", item_id, keys)
            continue
        key = next(iter(keys))
        if key not in linked_keys and key not in inserted_keys:
            reports.add("rows_without_id", None, key, records[key].page, None, f"item id {item_id} not assigned: row is ambiguous or blocked")
            continue
        existing = db.item_ids.get(item_id)
        if existing:
            equipment_id, source = existing
            if source != "wiki" or equipment_id != row_by_key.get(key):
                reports.add("ambiguous", equipment_id, db.rows.get(equipment_id, {}).get("title"),
                            f"item id {item_id} already mapped ({source}); wiki now says {key!r}; left as is")
                continue
        planned_ids[item_id] = (records[key], *first_version[(item_id, key)])

    keys_with_ids = {rec.wiki_key for rec, _, _ in planned_ids.values()}
    mapped_rows = {equipment_id for equipment_id, _ in db.item_ids.values()}
    for row_id, row in sorted(db.rows.items()):
        key = links.get(row_id)
        if key in keys_with_ids or row_id in mapped_rows:
            continue
        reason = "no wiki link" if key is None else "wiki record has no item id"
        reports.add("rows_without_id", row_id, key, row["title"], row["effects"], reason)
    for rec, _ in inserts:
        if rec.wiki_key not in keys_with_ids:
            reports.add("rows_without_id", None, rec.wiki_key, rec.page, None, "new row; wiki record has no item id")

    spot_dye = pick_dyed_spot_check(inserts, root_row)
    run_ts = datetime.now(timezone.utc).isoformat(timespec="seconds")
    sql = render_apply_sql(db, data["meta"], run_ts, links, updates, inserts, variant_links, planned_ids, valid, spot_dye)
    (out / "apply.sql").write_text(sql, encoding="utf-8")

    counts = reports.write(out)
    summary = {
        "database": db.database,
        "v11_applied": db.has_v11,
        "planned_at": run_ts,
        "wiki_fetched_at": data["meta"]["fetched_at"],
        "wiki_fetch_partial": data["meta"]["partial"],
        "wiki_records": len(records),
        "existing_rows": len(db.rows),
        "existing_rows_linked": len(links),
        "existing_rows_updated": len(updates),
        "changed_columns": dict(change_counts.most_common()),
        "rows_added": len(inserts),
        "variant_links_on_existing_rows": len(variant_links),
        "item_ids_planned": len(planned_ids),
        "blocked_pages": sorted(blocked),
        "dyed_spot_check": spot_dye,
        "reports": counts,
    }
    with open(out / "summary.json", "w", encoding="utf-8") as f:
        json.dump(summary, f, indent=2, sort_keys=True, ensure_ascii=False, default=str)
        f.write("\n")
    print(json.dumps({k: v for k, v in summary.items() if k != "blocked_pages"}, indent=2, default=str), flush=True)
    print(f"Wrote reports and apply.sql to {out}", flush=True)


def pick_dyed_spot_check(inserts: List[Tuple[WikiRecord, Dict[str, Optional[str]]]], root_row: Any) -> Optional[Dict[str, Any]]:
    candidates = []
    for rec, curated in inserts:
        root = root_row(rec)
        if rec.variant_kind == "dye" and root and root["effects"]:
            weapon = curated["slot"] in {"MAINHAND", "TWOHANDED", "OFFHAND"}
            candidates.append((not weapon, rec.wiki_key, rec.root_key, root["effects"]))
    if not candidates:
        return None
    _, key, root_key, effects = min(candidates)
    return {"variant": key, "root": root_key, "root_effects": effects}


# --- SQL rendering ---

def sql_literal(value: Any) -> str:
    if value is None:
        return "NULL"
    if isinstance(value, bool):
        return "TRUE" if value else "FALSE"
    if isinstance(value, (int, float)):
        return repr(value)
    if isinstance(value, dict):
        return "'" + json.dumps(value, ensure_ascii=False).replace("'", "''") + "'::jsonb"
    return "'" + str(value).replace("'", "''") + "'"


def sql_array(values: Iterable[str]) -> str:
    return "ARRAY[" + ", ".join(sql_literal(v) for v in values) + "]::text[]"


def render_apply_sql(db: DbState, meta: Dict[str, Any], run_ts: str, links: Dict[int, str],
                     updates: List[Tuple[int, Dict[str, Any]]], inserts: List[Tuple[WikiRecord, Dict[str, Optional[str]]]],
                     variant_links: List[Tuple[int, str]],
                     planned_ids: Dict[int, Tuple[WikiRecord, Optional[str], Optional[str]]],
                     valid: Dict[str, List[str]], spot_dye: Optional[Dict[str, Any]]) -> str:
    ts = sql_literal(run_ts) + "::timestamptz"
    out: List[str] = [
        f"-- Generated by wiki_item_scraper.py plan at {run_ts}",
        f"-- Planned against database {db.database!r}; wiki data fetched {meta['fetched_at']}.",
        "-- Run: psql -X -v ON_ERROR_STOP=1 -d <database> -f apply.sql",
        "-- One transaction. Every check below raises, which rolls everything back.",
        "",
        "BEGIN;",
        "SET LOCAL lock_timeout = '10s';",
        "",
        "DO $$ BEGIN",
        "    IF to_regclass('equipment_item_id') IS NULL THEN",
        "        RAISE EXCEPTION 'V11__add_equipment_item_ids has not been applied';",
        "    END IF;",
        "END $$;",
        "",
        "-- Rows this plan touches by id must have the same title as when it was planned.",
        "CREATE TEMP TABLE _plan_expected (id bigint PRIMARY KEY, title text NOT NULL) ON COMMIT DROP;",
    ]
    touched = sorted({row_id for row_id, _ in updates} | {row_id for row_id, _ in variant_links} | set(links))
    for batch in chunks(touched, 1000):
        out.append("INSERT INTO _plan_expected VALUES\n    " + ",\n    ".join(
            f"({row_id}, {sql_literal(db.rows[row_id]['title'])})" for row_id in batch) + ";")
    out += [
        "DO $$ DECLARE n int; BEGIN",
        "    SELECT count(*) INTO n FROM _plan_expected x LEFT JOIN equipment e ON e.id = x.id AND e.title = x.title WHERE e.id IS NULL;",
        "    IF n > 0 THEN RAISE EXCEPTION 'plan does not match this database: % rows differ by id/title', n; END IF;",
        "END $$;",
        "",
        "CREATE TEMP TABLE _manual_before ON COMMIT DROP AS",
        "    SELECT id, effects, class, slot, style, type FROM equipment;",
        "",
        f"-- 1. Existing rows: scraped columns and wiki_key only ({len(updates)} rows).",
    ]
    for row_id, changes in updates:
        sets = ", ".join(f"{col} = {sql_literal(value)}" for col, value in changes.items())
        out.append(f"UPDATE equipment SET {sets} WHERE id = {row_id};")

    out += ["", f"-- 2. New rows ({len(inserts)}). Variants copy effects and non-null enums from their root."]
    insert_cols = ["title", "wiki_key"] + SCRAPED_COLS
    for rec, curated in inserts:
        values = [sql_literal(rec.page), sql_literal(rec.wiki_key)] + [sql_literal(rec.values.get(c)) for c in SCRAPED_COLS]
        if rec.root_key:
            cols = insert_cols + CURATED_COLS + ["effects", "variant_of_id"]
            values += [f"COALESCE(r.{c}, {sql_literal(curated[c])})" for c in CURATED_COLS] + ["r.effects", "r.id"]
            out.append(f"INSERT INTO equipment ({', '.join(cols)})\nSELECT {', '.join(values)}\n"
                       f"FROM (VALUES (1)) AS one (x) LEFT JOIN equipment r ON r.wiki_key = {sql_literal(rec.root_key)};")
        else:
            cols = insert_cols + CURATED_COLS
            values += [sql_literal(curated[c]) for c in CURATED_COLS]
            out.append(f"INSERT INTO equipment ({', '.join(cols)})\nVALUES ({', '.join(values)});")

    out += ["", f"-- 3. Variant links on existing rows ({len(variant_links)})."]
    for row_id, root_key in variant_links:
        out.append(f"UPDATE equipment SET variant_of_id = (SELECT id FROM equipment WHERE wiki_key = {sql_literal(root_key)}) "
                   f"WHERE id = {row_id} AND variant_of_id IS NULL;")

    out += ["", f"-- 4. Item IDs ({len(planned_ids)}). A conflicting existing mapping is left alone and fails check 6c."]
    id_rows = sorted(planned_ids.items())
    for batch in chunks(id_rows, 1000):
        values = ",\n    ".join(
            f"({item_id}, {sql_literal(rec.wiki_key)}, {sql_literal(rec.page)}, {sql_literal(anchor)}, {sql_literal(name)})"
            for item_id, (rec, anchor, name) in batch)
        out += [
            "INSERT INTO equipment_item_id (item_id, equipment_id, wiki_page, version_anchor, item_name, source, last_seen_at)",
            f"SELECT v.item_id, e.id, v.wiki_page, v.version_anchor, v.item_name, 'wiki', {ts}",
            f"FROM (VALUES\n    {values}\n) AS v (item_id, wiki_key, wiki_page, version_anchor, item_name)",
            "JOIN equipment e ON e.wiki_key = v.wiki_key",
            "ON CONFLICT (item_id) DO UPDATE SET last_seen_at = EXCLUDED.last_seen_at, wiki_page = EXCLUDED.wiki_page,",
            "    version_anchor = EXCLUDED.version_anchor, item_name = EXCLUDED.item_name",
            "WHERE equipment_item_id.equipment_id = EXCLUDED.equipment_id AND equipment_item_id.source = 'wiki';",
        ]

    enum_checks = " OR ".join(f"({c} IS NOT NULL AND {c} <> ALL ({sql_array(valid[c])}))" for c in CURATED_COLS)
    out += [
        "",
        "-- 5. Manual data on existing rows is untouched: same effects (so same count per item) and same enums.",
        "DO $$ DECLARE n int; BEGIN",
        "    SELECT count(*) INTO n FROM _manual_before b LEFT JOIN equipment e ON e.id = b.id WHERE e.id IS NULL;",
        "    IF n > 0 THEN RAISE EXCEPTION '% existing rows disappeared', n; END IF;",
        "    SELECT count(*) INTO n FROM _manual_before b JOIN equipment e ON e.id = b.id",
        "    WHERE e.effects IS DISTINCT FROM b.effects OR e.class IS DISTINCT FROM b.class OR e.slot IS DISTINCT FROM b.slot",
        "       OR e.style IS DISTINCT FROM b.style OR e.type IS DISTINCT FROM b.type;",
        "    IF n > 0 THEN RAISE EXCEPTION 'manual columns changed on % existing rows', n; END IF;",
        "    SELECT count(*) INTO n FROM _manual_before b JOIN equipment e ON e.id = b.id",
        "    WHERE coalesce(cardinality(e.effects), 0) <> coalesce(cardinality(b.effects), 0);",
        "    IF n > 0 THEN RAISE EXCEPTION 'manual effect count changed on % existing rows', n; END IF;",
        "END $$;",
        "",
        "-- 6. New rows and item IDs.",
        "DO $$ DECLARE n int; BEGIN",
        f"    SELECT count(*) INTO n FROM equipment WHERE id NOT IN (SELECT id FROM _manual_before) AND ({enum_checks});",
        "    IF n > 0 THEN RAISE EXCEPTION '6a: % new rows have values outside the Java enums', n; END IF;",
        "    SELECT count(*) INTO n FROM equipment v JOIN equipment r ON r.id = v.variant_of_id",
        "    WHERE v.id NOT IN (SELECT id FROM _manual_before) AND v.effects IS DISTINCT FROM r.effects;",
        "    IF n > 0 THEN RAISE EXCEPTION '6b: % new variant rows do not carry their root''s effects', n; END IF;",
        f"    SELECT count(*) INTO n FROM equipment_item_id WHERE last_seen_at = {ts};",
        f"    IF n <> {len(planned_ids)} THEN RAISE EXCEPTION '6c: expected {len(planned_ids)} item ids written, got %', n; END IF;",
        "END $$;",
        "",
        "-- 7. Spot checks.",
        "DO $$ BEGIN",
        "    IF (SELECT e.wiki_key FROM equipment_item_id i JOIN equipment e ON e.id = i.equipment_id WHERE i.item_id = 4151)",
        "       IS DISTINCT FROM 'Abyssal whip' THEN",
        "        RAISE EXCEPTION 'spot check: item 4151 does not resolve to Abyssal whip';",
        "    END IF;",
        "    IF (SELECT array_agg(i.item_id ORDER BY i.item_id) FROM equipment_item_id i JOIN equipment e ON e.id = i.equipment_id",
        "        WHERE e.wiki_key = 'Ring of slaying') IS DISTINCT FROM ARRAY[13281, 13282, 13283, 13284, 13285, 13286, 13287, 13288] THEN",
        "        RAISE EXCEPTION 'spot check: Ring of slaying does not have item ids 13281-13288 on one row';",
        "    END IF;",
    ]
    if spot_dye:
        out += [
            f"    IF (SELECT effects FROM equipment WHERE wiki_key = {sql_literal(spot_dye['variant'])})",
            f"       IS DISTINCT FROM (SELECT effects FROM equipment WHERE wiki_key = {sql_literal(spot_dye['root'])})",
            f"       OR (SELECT cardinality(effects) FROM equipment WHERE wiki_key = {sql_literal(spot_dye['root'])}) IS NULL THEN",
            f"        RAISE EXCEPTION 'spot check: {spot_dye['variant'].replace(chr(39), chr(39) * 2)} does not carry its root''s effects';",
            "    END IF;",
        ]
    else:
        out.append("    RAISE WARNING 'no dyed item with a tagged root in this plan; covered only by check 6b';")
    out += [
        "    RAISE NOTICE 'checks passed: % equipment rows, % item ids', (SELECT count(*) FROM equipment), (SELECT count(*) FROM equipment_item_id);",
        "END $$;",
        "",
        "COMMIT;",
        "",
    ]
    return "\n".join(out)


def main() -> None:
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--api", default=API, help="MediaWiki api.php URL")
    ap.add_argument("--user-agent", default=USER_AGENT, help="User-Agent header (keep a contact in it)")
    ap.add_argument("--sleep", type=float, default=1.0, help="Minimum seconds between wiki requests")
    sub = ap.add_subparsers(dest="command", required=True)

    fetch = sub.add_parser("fetch", help="Pull wiki data to JSON files. No database access.")
    fetch.add_argument("--out-dir", default="wiki-scrape/data")
    fetch.add_argument("--page", action="append", help="Only fetch wikitext for this page. Can be repeated.")
    fetch.add_argument("--max-pages", type=int, default=None, help="Only fetch wikitext for the first N pages")
    fetch.set_defaults(func=cmd_fetch)

    plan = sub.add_parser("plan", help="Diff fetched data against the database (read-only). Writes reports and apply.sql.")
    plan.add_argument("--data-dir", default="wiki-scrape/data")
    plan.add_argument("--out-dir", default="wiki-scrape/plan")
    plan.add_argument("--db-url", default=os.environ.get("DB_URL"), help="libpq URL or conninfo. Defaults to DB_URL")
    plan.add_argument("--overrides", help='JSON file: {"link": {"<equipment id>": "<wiki_key>"}}')
    plan.add_argument("--offline", action="store_true", help="Skip wiki redirect lookups for unmatched titles")
    plan.add_argument("--enum-dir", default=str(ENUM_DIR), help="Directory with the Java enums")
    plan.set_defaults(func=cmd_plan)

    args = ap.parse_args()
    if args.command == "plan" and not args.db_url:
        raise SystemExit("Missing database URL. Set DB_URL or pass --db-url.")
    args.func(args)


if __name__ == "__main__":
    main()
