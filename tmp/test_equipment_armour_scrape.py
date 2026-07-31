#!/usr/bin/env python3
import argparse
import json
import re
import time

import mwparserfromhell
import requests


def normalize_key(key: str) -> str:
    return " ".join(str(key).strip().lower().split())


def clean_value(value):
    if value is None:
        return None
    s = " ".join(str(value).strip().split())
    return s or None


def to_float(value):
    value = clean_value(value)
    if value is None:
        return None
    value = value.replace(",", "")
    try:
        return float(value)
    except ValueError:
        return None


def fetch_wikitext(api, title, user_agent):
    session = requests.Session()
    session.headers.update({"User-Agent": user_agent})

    res = session.get(
        api,
        params={
            "action": "query",
            "format": "json",
            "prop": "revisions",
            "rvprop": "content",
            "rvslots": "main",
            "titles": title,
        },
        timeout=(10, 20),
    )
    res.raise_for_status()

    pages = res.json().get("query", {}).get("pages", {})
    page = next(iter(pages.values()))
    revs = page.get("revisions", [])
    if not revs:
        return ""

    return revs[0]["slots"]["main"].get("*", "")


def extract_infobox(wikitext, template_name="Infobox Bonuses"):
    code = mwparserfromhell.parse(wikitext)

    for tpl in code.filter_templates(recursive=True):
        name = str(tpl.name).strip().lower()
        if name != template_name.lower():
            continue

        params = {}
        for p in tpl.params:
            key = normalize_key(p.name)
            value = clean_value(p.value)
            if key and value is not None:
                params[key] = value

        return params

    return None


def sql_literal(value):
    if value is None:
        return "NULL"
    if isinstance(value, (int, float)):
        return str(value)
    return "'" + str(value).replace("'", "''") + "'"


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--api", default="https://runescape.wiki/api.php")
    ap.add_argument("--title", action="append", required=True)
    ap.add_argument("--sleep", type=float, default=0.2)
    ap.add_argument(
        "--user-agent",
        default="rotdb-equipment-armour-test/1.0 (local development)",
    )
    args = ap.parse_args()

    updates = []

    for title in args.title:
        wikitext = fetch_wikitext(args.api, title, args.user_agent)
        params = extract_infobox(wikitext)

        if not params:
            print(f"{title}: no Infobox Bonuses")
            continue

        armour = to_float(params.get("armour") or params.get("armor"))
        life = to_float(params.get("life"))

        print(json.dumps({
            "title": title,
            "armour": armour,
            "life": life,
            "raw_armour": params.get("armour") or params.get("armor"),
            "raw_life": params.get("life"),
        }, indent=2))

        updates.append(
            "UPDATE equipment\n"
            f"SET armour = {sql_literal(armour)},\n"
            f"    life = {sql_literal(life)}\n"
            f"WHERE lower(trim(title)) = lower(trim({sql_literal(title)}));"
        )

        time.sleep(args.sleep)

    print("\n-- SQL")
    print("BEGIN;")
    for stmt in updates:
        print(stmt)
        print()
    print("COMMIT;")


if __name__ == "__main__":
    main()
