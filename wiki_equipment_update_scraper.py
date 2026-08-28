#!/usr/bin/env python3
"""
<<<<<<< HEAD
RS Wiki Equipment scraper -> generates INSERT SQL for equipment missing from DB.
=======
RS Wiki Equipment scraper -> generates reviewable SQL from wiki equipment data.
>>>>>>> origin/main

Behavior:
- queries only Category:Equipment
- parses only Template: Infobox Bonuses
- compares wiki titles against existing DB equipment titles
- skips titles containing:
    Augmented
    (soul)
    (aurora)
    (barrows)
    (ice)
    (shadow)
    (third age)
    (blood)
- writes reviewable INSERT statements only for titles not already in DB
- generated INSERT statements also re-check title existence when applied

Install:
  pip install -r requirements-wiki-scraper.txt

Env:
  DB_URL=postgresql://user:password@host:port/database

Run:
  python3 wiki_equipment_update_scraper.py --api "https://runescape.wiki/api.php"

Test:
  python3 wiki_equipment_update_scraper.py --api "https://runescape.wiki/api.php" --max-pages 50
"""

import argparse
import json
import os
from pathlib import Path
import re
import time
from typing import Dict, Any, List, Optional, Set

try:
    from dotenv import load_dotenv
except ModuleNotFoundError:
    pass
else:
    load_dotenv()

IGNORED_SUBSTRINGS = [
    "augmented",
    "(soul)",
    "(aurora)",
    "(barrows)",
    "(ice)",
    "(shadow)",
    "(third age)",
    "(blood)",
]

def should_skip_title(title: str) -> bool:
    t = title.lower()

    # Augmented + dyes
    if re.search(r"\((aurora|barrows|blood|ice|shadow|soul|third age)\)", t):
        return True

    if "augmented" in t:
        return True

    # Slayer helmets
    if "slayer helmet" in t:
        return True

    # Cosmetic / ornament variants
    if re.search(r"\((or|g|t|s|broken)\)", t):
        return True

    return False

<<<<<<< HEAD
=======

def normalize_key(raw: Any) -> str:
    """Normalize wiki infobox keys before all lookups.

    Older data can contain keys such as "armour\n"; wiki templates may also use
    underscores. Keep this centralized so extraction and lookup agree.
    """
    return re.sub(r"\s+", " ", str(raw).strip().lower().replace("_", " "))

>>>>>>> origin/main
def should_skip_slot(slot: Optional[str]) -> bool:
    if not slot:
        return False

    s = slot.lower()

    if "aura" in s:
        return True
    if "sigil" in s:
        return True

    return False


def first_present(params: Dict[str, str], *keys: str) -> Optional[str]:
    for k in keys:
<<<<<<< HEAD
        if k in params:
            v = params.get(k)
=======
        normalized = normalize_key(k)
        if normalized in params:
            v = params.get(normalized)
>>>>>>> origin/main
            if v is not None:
                v = str(v).strip()
                if v != "":
                    return v
    return None


def to_int(raw: Optional[str]) -> Optional[int]:
    if raw is None:
        return None
    s = str(raw).strip()
    if not s:
        return None
    s = s.replace(",", "")
    try:
        return int(float(s))
    except ValueError:
        return None


def to_float(raw: Optional[str]) -> Optional[float]:
    if raw is None:
        return None
    s = str(raw).strip()
    if not s:
        return None
    s = s.replace(",", "")
    try:
        return float(s)
    except ValueError:
        return None


def clean_text(raw: Optional[str]) -> Optional[str]:
    if raw is None:
        return None
    v = " ".join(str(raw).strip().split())
    if not v:
        return None
    if v.lower() in {"n/a", "na", "unknown"}:
        return None
    return v


def normalize_slot(raw_slot: Optional[str]) -> Optional[str]:
    s = clean_text(raw_slot)
    if not s:
        return None

    s = s.lower().replace("-", " ")
    s = re.sub(r"\s+", " ", s).strip()

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


def normalize_combat_style(raw_style: Optional[str]) -> Optional[str]:
    s = clean_text(raw_style)
    if not s:
        return None

    s = s.lower()

    if "melee" in s:
        return "MELEE"
    if "magic" in s or "spell" in s:
        return "MAGIC"
    if "ranged" in s or "range" in s:
        return "RANGED"
    if "necromancy" in s or "necromantic" in s:
        return "NECROMANCY"
    if s in {"all", "hybrid", "any"}:
        return "ALL"

    return s.upper()


def normalize_weapon_style(raw_type: Optional[str], raw_style: Optional[str], raw_name: Optional[str] = None) -> str:
    joined = " ".join(
        [x for x in [clean_text(raw_type), clean_text(raw_style), clean_text(raw_name)] if x]
    ).lower()

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


<<<<<<< HEAD
=======
def normalize_equipment_type(raw_type: Optional[str]) -> Optional[str]:
    s = clean_text(raw_type)
    if not s:
        return None

    s = s.lower().replace("-", " ")
    s = re.sub(r"\s+", " ", s).strip()

    mapping = {
        "tank": "tank",
        "power": "power",
        "hybrid": "hybrid",
        "all": "all",
        "shield": "shield",
        "defender": "defender",
        "rebounder": "rebounder",
        "repriser": "repriser",
    }

    return mapping.get(s, s)


>>>>>>> origin/main
def extract_infobox_params(wikitext: str, template_name: str) -> Optional[Dict[str, str]]:
    if not wikitext:
        return None

    import mwparserfromhell

    code = mwparserfromhell.parse(wikitext)
    for tpl in code.filter_templates(recursive=True):
        name = str(tpl.name).strip()
        if name.lower() == template_name.lower():
            params: Dict[str, str] = {}
            for p in tpl.params:
<<<<<<< HEAD
                k = str(p.name).strip()
=======
                k = normalize_key(p.name)
>>>>>>> origin/main
                v = str(p.value).strip()
                if k:
                    params[k] = v
            return params

    return None


def category_members(
    api: str,
    category: str,
    user_agent: str,
    sleep_s: float,
    max_pages: Optional[int]
) -> List[str]:
    import requests

    session = requests.Session()
    session.headers.update({"User-Agent": user_agent})

    titles: List[str] = []
    cmcontinue = None

    while True:
        params = {
            "action": "query",
            "format": "json",
            "list": "categorymembers",
            "cmtitle": category,
            "cmlimit": "500",
            "cmnamespace": "0",
        }
        if cmcontinue:
            params["cmcontinue"] = cmcontinue

        r = session.get(api, params=params, timeout=(10, 20))
        r.raise_for_status()
        data = r.json()

        batch = data.get("query", {}).get("categorymembers", [])
        print(f"Fetched {len(batch)} titles from category batch", flush=True)

        for item in batch:
            title = item["title"]
            if should_skip_title(title):
                print(f"  skip filtered title: {title}", flush=True)
                continue

            titles.append(title)
            if max_pages is not None and len(titles) >= max_pages:
                return titles

        cmcontinue = data.get("continue", {}).get("cmcontinue")
        if not cmcontinue:
            break

        time.sleep(sleep_s)

    return titles


def fetch_wikitext_batch(api: str, titles: List[str], user_agent: str) -> Dict[str, str]:
    import requests

    session = requests.Session()
    session.headers.update({"User-Agent": user_agent})

    joined = "|".join(titles)
    params = {
        "action": "query",
        "format": "json",
        "prop": "revisions",
        "rvprop": "content",
        "rvslots": "main",
        "titles": joined,
    }

    r = session.get(api, params=params, timeout=(10, 20))
    r.raise_for_status()
    data = r.json()

    out: Dict[str, str] = {}
    pages = data.get("query", {}).get("pages", {})
    for _, page in pages.items():
        title = page.get("title", "")
        revs = page.get("revisions", [])
        if not revs:
            out[title] = ""
            continue
        out[title] = revs[0]["slots"]["main"].get("*", "")
    return out


_req_strength_re = re.compile(r"\{\{\s*sc\s*\|\s*strength\s*\|\s*(\d+)\s*\}\}", re.IGNORECASE)


def parse_strength_requirement(requirements_raw: Optional[str]) -> Optional[int]:
    if not requirements_raw:
        return None
    m = _req_strength_re.search(requirements_raw)
    if not m:
        return None
    return int(m.group(1))


def normalize_equipment_row(title: str, params: Dict[str, str]) -> Optional[Dict[str, Any]]:
<<<<<<< HEAD
    raw_infobox = {k.strip().lower(): str(v).strip() for k, v in params.items() if k and v is not None}

    name = clean_text(first_present(params, "name")) or title

    raw_class = clean_text(first_present(params, "class", "clazz", "combat style", "combatstyle"))
    raw_slot = clean_text(first_present(params, "slot", "equipment slot", "equip slot"))
    raw_style = clean_text(first_present(params, "style", "attack style", "weaponstyle"))
    raw_type = clean_text(first_present(params, "type", "weapon type"))

    tier = to_int(first_present(params, "tier", "weapontier", "weapon tier"))

    damage_tier_legacy = to_int(first_present(params, "damageTier", "damagetier", "damage tier"))
    accuracy_tier_legacy = to_int(first_present(params, "accuracyTier", "accuracytier", "accuracy tier"))

    strength = to_float(first_present(params, "strength", "str"))
    ranged = to_float(first_present(params, "ranged"))
    magic = to_float(first_present(params, "magic"))
    necromancy = to_float(first_present(params, "necromancy"))
    prayer = to_float(first_present(params, "prayer"))

    requirements_raw = clean_text(first_present(params, "requirements", "requirement", "reqs"))
    req_strength = parse_strength_requirement(requirements_raw)

    accuracy = to_float(first_present(params, "accuracy"))
    armour = to_float(first_present(params, "armour", "armor"))
    attack_range = to_float(first_present(params, "attack range", "range"))
    damage = to_float(first_present(params, "damage"))
    damage_bonus = to_float(first_present(params, "damage bonus"))
    defensive_bonus = to_float(first_present(params, "defensive bonus"))
    life = to_float(first_present(params, "life"))
    pvm_reduction = to_float(first_present(params, "pvm reduction"))
    pvp_reduction = to_float(first_present(params, "pvp reduction"))
    speed = to_float(first_present(params, "speed"))

    accuracy_tier = to_int(first_present(params, "accuracy tier", "accuracytier", "accuracyTier"))
    armour_damage_tier = to_int(first_present(params, "armour damage tier", "armor damage tier"))
    armour_tier = to_int(first_present(params, "armour tier", "armor tier"))
    damage_tier = to_int(first_present(params, "damage tier", "damagetier", "damageTier"))
    invtier = to_int(first_present(params, "invention tier", "invtier"))

    members_raw = clean_text(first_present(params, "members"))
=======
    normalized_params = {
        normalize_key(k): str(v).strip()
        for k, v in params.items()
        if k and v is not None
    }
    raw_infobox = normalized_params

    name = clean_text(first_present(normalized_params, "name")) or title

    raw_class = clean_text(first_present(normalized_params, "class", "clazz", "combat style", "combatstyle"))
    raw_slot = clean_text(first_present(normalized_params, "slot", "equipment slot", "equip slot"))
    raw_style = clean_text(first_present(normalized_params, "style", "attack style", "weaponstyle"))
    raw_type = clean_text(first_present(normalized_params, "type", "weapon type"))

    tier = to_int(first_present(normalized_params, "tier", "weapontier", "weapon tier"))

    damage_tier_legacy = to_int(first_present(normalized_params, "damageTier", "damagetier", "damage tier"))
    accuracy_tier_legacy = to_int(first_present(normalized_params, "accuracyTier", "accuracytier", "accuracy tier"))

    strength = to_float(first_present(normalized_params, "strength", "str"))
    ranged = to_float(first_present(normalized_params, "ranged"))
    magic = to_float(first_present(normalized_params, "magic"))
    necromancy = to_float(first_present(normalized_params, "necromancy"))
    prayer = to_float(first_present(normalized_params, "prayer"))

    requirements_raw = clean_text(first_present(normalized_params, "requirements", "requirement", "reqs"))
    req_strength = parse_strength_requirement(requirements_raw)

    accuracy = to_float(first_present(normalized_params, "accuracy"))
    armour = to_float(first_present(normalized_params, "armour", "armor"))
    attack_range = to_float(first_present(normalized_params, "attack range", "range"))
    damage = to_float(first_present(normalized_params, "damage"))
    damage_bonus = to_float(first_present(normalized_params, "damage bonus"))
    defensive_bonus = to_float(first_present(normalized_params, "defensive bonus"))
    life = to_float(first_present(normalized_params, "life"))
    pvm_reduction = to_float(first_present(normalized_params, "pvm reduction"))
    pvp_reduction = to_float(first_present(normalized_params, "pvp reduction"))
    speed = to_float(first_present(normalized_params, "speed"))

    accuracy_tier = to_int(first_present(normalized_params, "accuracy tier", "accuracytier", "accuracyTier"))
    armour_damage_tier = to_int(first_present(normalized_params, "armour damage tier", "armor damage tier"))
    armour_tier = to_int(first_present(normalized_params, "armour tier", "armor tier"))
    damage_tier = to_int(first_present(normalized_params, "damage tier", "damagetier", "damageTier"))
    invtier = to_int(first_present(normalized_params, "invention tier", "invtier"))

    members_raw = clean_text(first_present(normalized_params, "members"))
>>>>>>> origin/main
    members = None
    if members_raw:
        m = members_raw.lower()
        if m in {"yes", "true", "member", "members"}:
            members = True
        elif m in {"no", "false", "free", "free-to-play", "f2p"}:
            members = False

    slot = normalize_slot(raw_slot)

    if should_skip_slot(slot):
        return None

    row = {
        "title": title,
        "name": name,

        "class": raw_class,
        "slot": normalize_slot(raw_slot),
        "tier": tier,
<<<<<<< HEAD
        "style": normalize_combat_style(raw_style),
        "type": normalize_weapon_style(raw_type, raw_style, title),
=======
        "style": normalize_weapon_style(raw_type, raw_style, title),
        "type": normalize_equipment_type(raw_type),
>>>>>>> origin/main

        "damagetier": damage_tier_legacy,
        "accuracytier": accuracy_tier_legacy,

        "strength": strength,
        "ranged": ranged,
        "magic": magic,
        "necromancy": necromancy,
        "prayer": prayer,

        "requirements_raw": requirements_raw,
        "req_strength": req_strength,
        "raw_infobox": raw_infobox,
        "effects": None,

        "accuracy": accuracy,
        "accuracy_tier": accuracy_tier,
        "armour": armour,
        "armour_damage_tier": armour_damage_tier,
        "armour_tier": armour_tier,
        "attack_range": attack_range,
        "damage": damage,
        "damage_bonus": damage_bonus,
        "damage_tier": damage_tier,
        "defensive_bonus": defensive_bonus,
        "equippable": True,
<<<<<<< HEAD
        "ids": clean_text(first_present(params, "id", "ids")),
        "images": clean_text(first_present(params, "image", "image1", "images")),
        "invtier": invtier,
        "level_requirement": clean_text(first_present(params, "level requirement")),
=======
        "ids": clean_text(first_present(normalized_params, "id", "ids")),
        "images": clean_text(first_present(normalized_params, "image", "image1", "images")),
        "invtier": invtier,
        "level_requirement": clean_text(first_present(normalized_params, "level requirement")),
>>>>>>> origin/main
        "life": life,
        "members": members,
        "pvm_reduction": pvm_reduction,
        "pvp_reduction": pvp_reduction,
        "requirements": requirements_raw,
        "speed": speed,
<<<<<<< HEAD
        "versions": clean_text(first_present(params, "version", "versions")),
=======
        "versions": clean_text(first_present(normalized_params, "version", "versions")),
>>>>>>> origin/main
    }

    return row


def sql_literal(value: Any) -> str:
    if value is None:
        return "NULL"
    if isinstance(value, bool):
        return "TRUE" if value else "FALSE"
    if isinstance(value, (int, float)):
        return str(value)
    escaped = str(value).replace("'", "''")
    return f"'{escaped}'"


def json_literal(value: Any) -> str:
    if value is None:
        return "NULL"
    escaped = json.dumps(value, ensure_ascii=False).replace("'", "''")
    return f"'{escaped}'::jsonb"


def build_insert_sql(row: Dict[str, Any]) -> str:
    cols = [
        "title",
        "name",
        "class",
        "slot",
        "tier",
        "style",
        "type",
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
        "effects",
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
        "ids",
        "images",
        "invtier",
        "level_requirement",
        "life",
        "members",
        "pvm_reduction",
        "pvp_reduction",
        "requirements",
        "speed",
        "versions",
    ]

    vals = []
    for col in cols:
        if col == "raw_infobox":
            vals.append(json_literal(row.get(col)))
        elif col == "effects":
            vals.append("NULL")
        else:
            vals.append(sql_literal(row.get(col)))

    title_literal = sql_literal(row.get("title"))

    return (
        f"INSERT INTO equipment ({', '.join(cols)})\n"
        f"SELECT {', '.join(vals)}\n"
        "WHERE NOT EXISTS (\n"
        "    SELECT 1\n"
        "    FROM equipment\n"
        f"    WHERE lower(trim(title)) = lower(trim({title_literal}))\n"
        ");"
    )


<<<<<<< HEAD
=======
UPDATE_COLS = [
    "name",
    "class",
    "slot",
    "tier",
    "style",
    "type",
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
    "ids",
    "images",
    "invtier",
    "level_requirement",
    "life",
    "members",
    "pvm_reduction",
    "pvp_reduction",
    "requirements",
    "speed",
    "versions",
]


def sql_value_for_col(row: Dict[str, Any], col: str) -> str:
    if col == "raw_infobox":
        return json_literal(row.get(col))
    return sql_literal(row.get(col))


def build_update_sql(row: Dict[str, Any], only_missing: bool, update_cols: List[str]) -> Optional[str]:
    assignments = []
    missing_checks = []

    for col in update_cols:
        value = row.get(col)

        # Keep existing values when the wiki does not provide a value. This
        # avoids turning previously curated data into NULL during broad refreshes.
        if value is None:
            continue

        assignments.append(f"    {col} = {sql_value_for_col(row, col)}")
        if col != "raw_infobox":
            missing_checks.append(f"{col} IS NULL")

    if not assignments:
        return None

    title_literal = sql_literal(row.get("title"))
    sql = (
        "UPDATE equipment\n"
        "SET\n"
        f"{',\n'.join(assignments)}\n"
        f"WHERE lower(trim(title)) = lower(trim({title_literal}))"
    )

    if only_missing and missing_checks:
        sql += "\n  AND (" + " OR ".join(missing_checks) + ")"

    return sql + ";"


>>>>>>> origin/main
def load_existing_titles(db_url: str) -> Set[str]:
    import psycopg2

    conn = psycopg2.connect(db_url)
    try:
        with conn.cursor() as cur:
            cur.execute("select lower(trim(title)) from equipment where title is not null")
            return {row[0] for row in cur.fetchall()}
    finally:
        conn.close()


def ensure_parent_dir(path: str) -> None:
    parent = Path(path).expanduser().resolve().parent
    parent.mkdir(parents=True, exist_ok=True)


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--api", required=True, help="MediaWiki api.php URL")
    ap.add_argument("--db-url", default=os.environ.get("DB_URL"), help="PostgreSQL connection URL. Defaults to DB_URL env var")
    ap.add_argument("--category", default="Category:Equipment", help='Category title')
    ap.add_argument("--template", default="Infobox Bonuses", help='Infobox template name')
    ap.add_argument("--out", default="insert_new_equipment.sql", help="Output SQL path")
    ap.add_argument("--misses", default="new_equipment_misses.txt", help="Miss log path")
    ap.add_argument("--summary", default="equipment_scrape_summary.json", help="Summary JSON output path")
    ap.add_argument("--max-pages", type=int, default=None, help="Stop after N pages")
    ap.add_argument("--batch", type=int, default=50, help="How many pages per wikitext request")
    ap.add_argument("--sleep", type=float, default=0.2, help="Sleep between API calls")
    ap.add_argument("--user-agent", default="rotdb-weekly-scraper/1.0 (https://github.com/wxrl/rotdb)", help="User-Agent header")
<<<<<<< HEAD
=======
    ap.add_argument("--update-existing", action="store_true", help="Generate UPDATE statements for existing equipment titles")
    ap.add_argument("--only-missing", action="store_true", help="With --update-existing, update only rows where at least one generated column is NULL")
    ap.add_argument("--update-col", action="append", choices=UPDATE_COLS, help="With --update-existing, limit UPDATE statements to this column. Can be passed multiple times.")
    ap.add_argument("--title", action="append", help="Scrape one exact wiki title. Can be passed multiple times. Bypasses category enumeration.")
>>>>>>> origin/main
    args = ap.parse_args()

    if not args.db_url:
        raise SystemExit("Missing database URL. Set DB_URL or pass --db-url.")

    print("Loading existing DB titles...", flush=True)
    existing_titles = load_existing_titles(args.db_url)
    print(f"Loaded {len(existing_titles)} existing DB titles", flush=True)

<<<<<<< HEAD
    titles = category_members(args.api, args.category, args.user_agent, args.sleep, args.max_pages)
    print(f"Found {len(titles)} filtered titles in {args.category}", flush=True)

    total = 0
    inserts = []
=======
    if args.title:
        titles = [title for title in args.title if not should_skip_title(title)]
        print(f"Using {len(titles)} explicit title(s)", flush=True)
    else:
        titles = category_members(args.api, args.category, args.user_agent, args.sleep, args.max_pages)
        print(f"Found {len(titles)} filtered titles in {args.category}", flush=True)

    total = 0
    inserts = []
    updates = []
>>>>>>> origin/main
    misses = []

    for i in range(0, len(titles), args.batch):
        chunk = titles[i:i + args.batch]
        print(f"Fetching batch {i + 1}-{i + len(chunk)}", flush=True)
        texts = fetch_wikitext_batch(args.api, chunk, args.user_agent)

        for title, wikitext in texts.items():
            total += 1

            normalized_title = title.lower().strip()
<<<<<<< HEAD
            if normalized_title in existing_titles:
=======
            is_existing = normalized_title in existing_titles
            if is_existing and not args.update_existing:
>>>>>>> origin/main
                continue

            params = extract_infobox_params(wikitext, args.template)
            if not params:
                misses.append((title, "no infobox bonuses template"))
                continue

            row = normalize_equipment_row(title, params)
            if row is None:
                print(f"  skip slot filtered: {title}", flush=True)
                continue

<<<<<<< HEAD
=======
            if is_existing:
                update_cols = args.update_col if args.update_col else UPDATE_COLS
                update = build_update_sql(row, args.only_missing, update_cols)
                if update:
                    updates.append(update)
                    print(f"  UPDATE -> {title}", flush=True)
                continue

>>>>>>> origin/main
            inserts.append(build_insert_sql(row))
            print(f"  NEW -> {title}", flush=True)

        time.sleep(args.sleep)

    ensure_parent_dir(args.out)
    ensure_parent_dir(args.misses)
    ensure_parent_dir(args.summary)

    with open(args.out, "w", encoding="utf-8") as f:
        f.write("BEGIN;\n\n")
<<<<<<< HEAD
=======
        for stmt in updates:
            f.write(stmt)
            f.write("\n\n")
>>>>>>> origin/main
        for stmt in inserts:
            f.write(stmt)
            f.write("\n\n")
        f.write("COMMIT;\n")

    with open(args.misses, "w", encoding="utf-8") as f:
        for title, reason in misses:
            f.write(f"{title} :: {reason}\n")

    summary = {
        "category": args.category,
        "template": args.template,
        "existing_db_titles": len(existing_titles),
        "filtered_wiki_titles": len(titles),
        "processed_missing_title_candidates": total,
        "insert_statements": len(inserts),
<<<<<<< HEAD
=======
        "update_statements": len(updates),
>>>>>>> origin/main
        "misses": len(misses),
        "output_sql": args.out,
        "misses_log": args.misses,
    }

    with open(args.summary, "w", encoding="utf-8") as f:
        json.dump(summary, f, indent=2, sort_keys=True)
        f.write("\n")

<<<<<<< HEAD
    print(f"Generated {len(inserts)} INSERT statements", flush=True)
    print(f"Processed {total} missing-title candidates", flush=True)
=======
    print(f"Generated {len(updates)} UPDATE statements", flush=True)
    print(f"Generated {len(inserts)} INSERT statements", flush=True)
    print(f"Processed {total} title candidates", flush=True)
>>>>>>> origin/main
    print(f"Wrote SQL to {args.out}", flush=True)
    print(f"Wrote misses to {args.misses}", flush=True)
    print(f"Wrote summary to {args.summary}", flush=True)


if __name__ == "__main__":
    main()
