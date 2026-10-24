import unittest

from wiki_item_scraper import (
    DbState,
    Reports,
    WikiRecord,
    build_page_records,
    match_rows,
    resolve_variant_root,
    split_versions,
    template_params,
)

WHIP = """{{Infobox Bonuses
|class = melee
|slot = weapon
|tier = 70
|damage = 672.0
|style = Slash
}}"""

SCYTHE = """{{Infobox Bonuses
|version1 = new
|version2 = used
|version3 = broken
|class = melee
|slot = 2h
|tier = 90
|tier3 = 0
|damage = 2011.5
|damage3 = 0
}}"""

SPLIT = """{{Infobox Bonuses
|version1 = (1)
|version2 = (2)
|slot = ring
|armour1 = 10
|armour2 = 20
}}"""


def item(anchor, ids, name=None):
    return {"version_anchor": anchor, "item_id": ids, "item_name": name}


class SplitVersionsTest(unittest.TestCase):
    def test_unversioned_is_one_entry(self):
        versions = split_versions(template_params(WHIP))
        self.assertEqual([None], [anchor for anchor, _ in versions])

    def test_suffixed_params_override_shared(self):
        versions = dict(split_versions(template_params(SCYTHE)))
        self.assertEqual("2011.5", versions["new"]["damage"])
        self.assertEqual("0", versions["broken"]["damage"])
        self.assertNotIn("damage3", versions["new"])


class BuildPageRecordsTest(unittest.TestCase):
    def test_single_version_page(self):
        records = build_page_records("Abyssal whip", template_params(WHIP), 1, [item(None, [4151])], Reports())
        self.assertEqual(["Abyssal whip"], [r.wiki_key for r in records])
        self.assertEqual([4151], [i[0] for i in records[0].item_ids])
        self.assertEqual(672.0, records[0].values["damage"])

    def test_identical_versions_share_a_row_and_broken_is_left_out(self):
        reports = Reports()
        items = [item("new", [31725]), item("used", [31727]), item("broken", [31728])]
        records = build_page_records("Noxious scythe", template_params(SCYTHE), 1, items, reports)
        self.assertEqual(["Noxious scythe"], [r.wiki_key for r in records])
        self.assertEqual([31725, 31727], sorted(i[0] for i in records[0].item_ids))
        self.assertTrue(any("31728" in row[2] for row in reports.rows["excluded"]))

    def test_versions_with_different_stats_split_when_ids_line_up(self):
        items = [item("(1)", [1], "Ring (1)"), item("(2)", [2], "Ring (2)")]
        records = build_page_records("Ring", template_params(SPLIT), 1, items, Reports())
        self.assertEqual({"Ring": [1], "Ring#(2)": [2]}, {r.wiki_key: [i[0] for i in r.item_ids] for r in records})
        sibling = next(r for r in records if r.wiki_key == "Ring#(2)")
        self.assertEqual(("Ring (2)", "Ring", 20.0), (sibling.values["name"], sibling.root_key, sibling.values["armour"]))

    def test_ids_on_another_axis_collapse_to_version_one(self):
        reports = Reports()
        items = [item("charged", [7]), item("uncharged", [8])]
        records = build_page_records("Ring", template_params(SPLIT), 1, items, reports)
        self.assertEqual(["Ring"], [r.wiki_key for r in records])
        self.assertEqual(([7, 8], 10.0), ([i[0] for i in records[0].item_ids], records[0].values["armour"]))
        self.assertEqual([("Ring", "(1)", ["(2)"], ["armour"])], reports.rows["versions_collapsed"])

    def test_auras_are_left_out(self):
        reports = Reports()
        aura = "{{Infobox Bonuses\n|slot = aura\n}}"
        self.assertEqual([], build_page_records("Aura", template_params(aura), 1, [item(None, [5])], reports))
        self.assertEqual(1, len(reports.rows["excluded"]))


class MatchRowsTest(unittest.TestCase):
    def test_exact_title_beats_redirect(self):
        rows = {
            1: {"id": 1, "title": "Oak longbow", "wiki_key": None, "effects": None},
            2: {"id": 2, "title": "Oak shieldbow", "wiki_key": None, "effects": None},
        }
        db = DbState("test", rows, False, {})
        records = {"Oak longbow": WikiRecord("Oak longbow", "Oak longbow", [None], {}, {}, 1)}
        reports = Reports()
        links, blocked = match_rows(db, records, {"Oak longbow"}, {"Oak shieldbow": "Oak longbow"}, {}, reports)
        self.assertEqual(({1: "Oak longbow"}, set()), (links, blocked))
        self.assertIn("superseded", reports.rows["db_rows_unmatched"][0][3])


class VariantRootTest(unittest.TestCase):
    PAGES = {p.lower(): p for p in [
        "Noxious scythe", "Noxious scythe (shadow)", "Augmented noxious scythe",
        "Adamant 2h sword", "Adamant 2h sword + 1", "Amulet of fury (or)", "Amulet of fury",
    ]}

    def test_rules(self):
        self.assertEqual(("dye", "Noxious scythe", "Noxious scythe"),
                         resolve_variant_root("Noxious scythe (shadow)", self.PAGES))
        self.assertEqual(("augmented", "Noxious scythe", "noxious scythe"),
                         resolve_variant_root("Augmented noxious scythe", self.PAGES))
        self.assertEqual("Adamant 2h sword", resolve_variant_root("Adamant 2h sword + 1", self.PAGES)[1])
        self.assertEqual("Amulet of fury", resolve_variant_root("Amulet of fury (or)", self.PAGES)[1])

    def test_not_a_variant(self):
        self.assertIsNone(resolve_variant_root("Noxious scythe", self.PAGES))
        self.assertIsNone(resolve_variant_root("Skull lantern (tier 50)", self.PAGES))

    def test_missing_base_is_reported(self):
        self.assertEqual(("dye", None, "Zaryte bow"), resolve_variant_root("Zaryte bow (blood)", self.PAGES))


if __name__ == "__main__":
    unittest.main()
