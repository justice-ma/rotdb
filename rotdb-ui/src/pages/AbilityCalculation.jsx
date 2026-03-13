import { useEffect, useMemo, useState } from "react";

import Abilities from "../components/Abilities";
import AbilityStatistics from "../components/AbilityStatistics";
import CombatSettings from "../components/CombatSettings";

import {
  fetchAbilities,
  fetchBatchCalculation,
  fetchDetailedAbilityCalculation,
  fetchBuffs,
  fetchEquipmentByIds,
  fetchSpells,
  fetchFamiliars,
  fetchTargetByTitle,
} from "../api/api";

import "../style/abilityPage.css";

const STORAGE_KEY = "rs3-presets";
const DELETED_DEFAULT_PRESET_IDS_KEY = "rs3-deleted-default-preset-ids";

const DEFAULT_SKILLS = {
  necromancy: 120,
  constitution: 99,
  strength: 120,
  ranged: 120,
  magic: 120,
  attack: 120,
  defence: 99,
  summoning: 99,
  maxHp: 10100,
  currentHp: 10100,
};

const DEFAULT_PRESETS = [
  {
    id: "default-ranged-bis",
    version: 1,
    name: "Default Ranged",
    created: "2026-03-13",
    edited: "2026-03-13",
    payload: {
      style: "RANGED",
      skills: {
        necromancy: 120,
        constitution: 99,
        strength: 120,
        ranged: 120,
        magic: 120,
        attack: 120,
        defence: 99,
        summoning: 99,
        maxHp: 10100,
        currentHp: 10100,
      },
      equipment: {
        mainhandId: 1785,
        offhandId: null,
        headId: 3263,
        bodyId: 3264,
        legsId: 3262,
        bootsId: 3261,
        glovesId: 3265,
        neckId: 3399,
        ringId: 7949,
        capeId: 4552,
        pocketId: 7429,
        ammoId: 3737,
        quiverId: 6266,
      },
      buffs: {
        enabledBuffs: [
          "DEATHSWIFTNESS",
          "SPLITSOUL",
          "BALANCEBYFORCE",
          "REAPERSCREW",
          "ENCHANTMENTOFDISPELLING",
          "ENCHANTMENTOFSHADOWS",
          "SHARDOFGENESIS",
          "ENCHANTMENTOFDREAD",
          "VULNED",
          "SMOKECLOUDED",
          "KALG",
        ],
        buffStacks: {},
      },
      selectedPrayers: ["DESOLATION"],
      potions: [
        {
          pot: "ELDER",
          stat: "ALL",
        },
      ],
      perks: {
        selectedPerks: {
          PRECISE: 6,
          ERUPTIVE: 2,
          ULTIMATUMS: 4,
          BITING: 4,
        },
        itemLevel20: true,
      },
      targetTitle: null,
      targetCurrentHp: null,
      targetMaxHp: null,
      spell: null,
      relic: null,
      selectedFamiliar: "KALGERIONDEMON",
    },
  },
  {
    id: "default-melee-bis",
    version: 1,
    name: "Default Melee",
    created: "2026-03-13",
    edited: "2026-03-13",
    payload: {
      style: "MELEE",
      skills: {
        necromancy: 120,
        constitution: 99,
        strength: 120,
        ranged: 120,
        magic: 120,
        attack: 120,
        defence: 99,
        summoning: 99,
        maxHp: 10100,
        currentHp: 10100,
      },
      equipment: {
        mainhandId: 3119,
        offhandId: null,
        headId: 8769,
        bodyId: 8771,
        legsId: 8770,
        bootsId: 8768,
        glovesId: 3376,
        neckId: 291,
        ringId: 6682,
        capeId: 4552,
        pocketId: 7429,
        ammoId: null,
        quiverId: 5737,
      },
      buffs: {
        enabledBuffs: [
          "ECLIPSEDSOUL",
          "BERSERK",
          "DBA",
          "REAPERSCREW",
          "ENCHANTMENTOFSAVAGERY",
          "ENCHANTMENTOFHEROISM",
          "SHARDOFGENESIS",
          "ENCHANTMENTOFAGONY",
          "FLAMEBOUNDRIVAL",
          "VULNED",
          "SMOKECLOUDED",
          "KALG",
        ],
        buffStacks: {},
      },
      selectedPrayers: ["DIVINERAGE", "PIETY"],
      potions: [
        {
          pot: "ELDER",
          stat: "ALL",
        },
      ],
      perks: {
        selectedPerks: {},
        itemLevel20: false,
      },
      targetTitle: null,
      targetCurrentHp: null,
      targetMaxHp: null,
      spell: null,
      relic: null,
      selectedFamiliar: "KALGERIONDEMON",
    },
  },
  {
    id: "default-magic-bis",
    version: 1,
    name: "Default Magic",
    created: "2026-03-13",
    edited: "2026-03-13",
    payload: {
      style: "MAGIC",
      skills: {
        necromancy: 120,
        constitution: 99,
        strength: 120,
        ranged: 120,
        magic: 120,
        attack: 120,
        defence: 99,
        summoning: 99,
        maxHp: 10100,
        currentHp: 10100,
      },
      equipment: {
        mainhandId: 3682,
        offhandId: null,
        headId: 5266,
        bodyId: 6884,
        legsId: 6858,
        bootsId: 1758,
        glovesId: 3938,
        neckId: 319,
        ringId: 2040,
        capeId: 4552,
        pocketId: 3394,
        ammoId: null,
        quiverId: null,
      },
      buffs: {
        enabledBuffs: [
          "ECLIPSEDSOUL",
          "SUNSHINE",
          "INSTABILITY",
          "REAPERSCREW",
          "TITHESTACKS",
          "ENCHANTMENTOFAFFLICTION",
          "ENCHANTMENTOFMETAPHYSICS",
          "SHARDOFGENESIS",
          "ENCHANTMENTOFFLAMES",
          "HAUNTED",
          "VULNED",
          "SMOKECLOUDED",
          "KALG",
        ],
        buffStacks: {
          TITHESTACKS: 12,
        },
      },
      selectedPrayers: ["MYSTICMIGHT", "OVERCHARGE"],
      potions: [
        {
          pot: "ELDER",
          stat: "ALL",
        },
      ],
      perks: {
        selectedPerks: {},
        itemLevel20: false,
      },
      targetTitle: null,
      targetCurrentHp: null,
      targetMaxHp: null,
      spell: "INCITEFEAR",
      relic: null,
      selectedFamiliar: "KALGERIONDEMON",
    },
  },
  {
    id: "default-necromancy-bis",
    version: 1,
    name: "Default Necromancy",
    created: "2026-03-13",
    edited: "2026-03-13",
    payload: {
      style: "NECROMANCY",
      skills: {
        necromancy: 120,
        constitution: 99,
        strength: 120,
        ranged: 120,
        magic: 120,
        attack: 120,
        defence: 99,
        summoning: 99,
        maxHp: 10100,
        currentHp: 10100,
      },
      equipment: {
        mainhandId: 6098,
        offhandId: 7818,
        headId: 8809,
        bodyId: 6885,
        legsId: 6859,
        bootsId: 3648,
        glovesId: 2654,
        neckId: 7302,
        ringId: 9251,
        capeId: 4552,
        pocketId: 7429,
        ammoId: null,
        quiverId: 8470,
      },
      buffs: {
        enabledBuffs: [
          "SPLITSOUL",
          "REAPERSCREW",
          "UNDEADSLAYERSIGIL",
          "SHARDOFGENESIS",
          "HAUNTED",
          "VULNED",
        ],
        buffStacks: {},
      },
      selectedPrayers: ["RUINATION"],
      potions: [
        {
          pot: "ELDER",
          stat: "ALL",
        },
      ],
      perks: {
        selectedPerks: {
          PRECISE: 6,
          ERUPTIVE: 4,
          EQUILIBRIUM: 4,
          ULTIMATUMS: 4,
          UNDEADSLAYER: 1,
        },
        itemLevel20: true,
      },
      targetTitle: "Rasial, the First Necromancer",
      targetCurrentHp: 800000,
      targetMaxHp: 800000,
      spell: null,
      relic: null,
      selectedFamiliar: "RIPPERDEMON",
    },
  },
];

export default function AbilityCalculation() {
  const [settingsOpen, setSettingsOpen] = useState(false);

  const [abilities, setAbilities] = useState([]);
  const [results, setResults] = useState({});
  const [detailedResults, setDetailedResults] = useState({});
  const [error, setError] = useState("");

  const [mainhand, setMainhand] = useState(null);
  const [style, setStyle] = useState("RANGED");
  const [selectedAbility, setSelectedAbility] = useState(null);

  const [equipmentIds, setEquipmentIds] = useState({});
  const [selectedEquipmentBySlot, setSelectedEquipmentBySlot] = useState({});

  const [spell, setSpell] = useState(null);

  const [buffs, setBuffs] = useState({});
  const [allBuffs, setAllBuffs] = useState([]);

  const [skills, setSkills] = useState(DEFAULT_SKILLS);

  const [selectedPrayers, setSelectedPrayers] = useState([]);
  const [selectedPerks, setSelectedPerks] = useState({});

  const [target, setTarget] = useState(null);
  const [targetCurrentHp, setTargetCurrentHp] = useState("");
  const [targetMaxHp, setTargetMaxHp] = useState("");

  const [familiar, setFamiliar] = useState(null);

  const [presets, setPresets] = useState([]);
  const [selectedPresetId, setSelectedPresetId] = useState("");

  const [itemLevel20, setItemLevel20] = useState(false);
  const [selectedPotions, setSelectedPotions] = useState([
    { pot: "NONE", stat: "ALL" },
  ]);

  useEffect(() => {
    (async () => {
      try {
        const data = await fetchBuffs();
        setAllBuffs(data);
      } catch (e) {
        console.error(e);
      }
    })();
  }, []);

  useEffect(() => {
    try {
      const rawPresets = localStorage.getItem(STORAGE_KEY);
      const rawDeletedDefaultIds = localStorage.getItem(
        DELETED_DEFAULT_PRESET_IDS_KEY,
      );

      const storedPresets = rawPresets ? JSON.parse(rawPresets) : [];
      const safeStoredPresets = Array.isArray(storedPresets)
        ? storedPresets
        : [];

      const deletedDefaultIds = rawDeletedDefaultIds
        ? JSON.parse(rawDeletedDefaultIds)
        : [];
      const safeDeletedDefaultIds = Array.isArray(deletedDefaultIds)
        ? deletedDefaultIds
        : [];

      const deletedSet = new Set(safeDeletedDefaultIds);
      const existingIds = new Set(safeStoredPresets.map((p) => p.id));

      const defaultsToSeed = DEFAULT_PRESETS.filter(
        (preset) => !deletedSet.has(preset.id) && !existingIds.has(preset.id),
      );

      const merged = [...safeStoredPresets, ...defaultsToSeed];

      localStorage.setItem(STORAGE_KEY, JSON.stringify(merged));
      localStorage.setItem(
        DELETED_DEFAULT_PRESET_IDS_KEY,
        JSON.stringify(safeDeletedDefaultIds),
      );

      setPresets(merged);
    } catch (e) {
      console.error("Failed to load presets", e);
      localStorage.setItem(STORAGE_KEY, JSON.stringify(DEFAULT_PRESETS));
      localStorage.setItem(DELETED_DEFAULT_PRESET_IDS_KEY, JSON.stringify([]));
      setPresets(DEFAULT_PRESETS);
    }
  }, []);

  useEffect(() => {
    if (!mainhand?.clazz) return;

    setStyle((prev) => (prev === mainhand.clazz ? prev : mainhand.clazz));
    setSelectedAbility(null);
    setDetailedResults({});
  }, [mainhand]);

  const base = useMemo(() => {
    const mainhandId = equipmentIds.MAINHAND ?? null;

    if (!mainhandId) return null;

    return {
      style,
      skills,
      equipment: {
        mainhandId,
        offhandId: equipmentIds.OFFHAND ?? null,
        headId: equipmentIds.HEAD ?? null,
        bodyId: equipmentIds.BODY ?? null,
        legsId: equipmentIds.LEGS ?? null,
        bootsId: equipmentIds.BOOTS ?? null,
        glovesId: equipmentIds.GLOVES ?? null,
        neckId: equipmentIds.NECK ?? null,
        ringId: equipmentIds.RING ?? null,
        capeId: equipmentIds.CAPE ?? null,
        pocketId: equipmentIds.POCKET ?? null,
        ammoId: equipmentIds.AMMO ?? null,
        quiverId: equipmentIds.QUIVER ?? null,
      },
      buffs,
      selectedPrayers,
      potions: selectedPotions,
      perks: {
        selectedPerks,
        itemLevel20,
      },
      targetTitle: target?.name ?? "Training dummy",
      targetCurrentHp: targetCurrentHp === "" ? null : Number(targetCurrentHp),
      targetMaxHp: targetMaxHp === "" ? null : Number(targetMaxHp),
      spell: spell?.id ?? null,
      relic: null,
      selectedFamiliar: familiar?.id ?? null,
    };
  }, [
    style,
    equipmentIds,
    buffs,
    skills,
    spell,
    selectedPrayers,
    selectedPerks,
    target,
    targetCurrentHp,
    targetMaxHp,
    familiar,
    selectedPotions,
    itemLevel20,
  ]);

  const needsMainhand = !equipmentIds.MAINHAND;

  function persistPresets(updatedPresets) {
    setPresets(updatedPresets);
    localStorage.setItem(STORAGE_KEY, JSON.stringify(updatedPresets));
  }

  function handleSavePreset(name) {
    if (!base) return;

    console.log("Preset payload:");
    console.log(JSON.stringify(base, null, 2));

    const trimmedName = name.trim();
    if (!trimmedName) return;

    const now = new Date().toISOString();

    const newPreset = {
      id: crypto.randomUUID(),
      version: 1,
      name: trimmedName,
      created: now,
      edited: now,
      payload: base,
      uiState: {
        mainhand,
        spell,
        target,
        familiar,
        selectedEquipmentBySlot,
        selectedPotions,
      },
    };

    const updated = [...presets, newPreset];
    persistPresets(updated);
    setSelectedPresetId(newPreset.id);
  }

  function applyPresetPayload(payload) {
    if (!payload) return;

    setStyle(payload.style ?? "RANGED");
    setSkills(payload.skills ?? DEFAULT_SKILLS);

    setEquipmentIds({
      MAINHAND: payload.equipment?.mainhandId ?? null,
      OFFHAND: payload.equipment?.offhandId ?? null,
      HEAD: payload.equipment?.headId ?? null,
      BODY: payload.equipment?.bodyId ?? null,
      LEGS: payload.equipment?.legsId ?? null,
      BOOTS: payload.equipment?.bootsId ?? null,
      GLOVES: payload.equipment?.glovesId ?? null,
      NECK: payload.equipment?.neckId ?? null,
      RING: payload.equipment?.ringId ?? null,
      CAPE: payload.equipment?.capeId ?? null,
      POCKET: payload.equipment?.pocketId ?? null,
      AMMO: payload.equipment?.ammoId ?? null,
      QUIVER: payload.equipment?.quiverId ?? null,
    });

    setSelectedEquipmentBySlot({});
    setMainhand(null);
    setSpell(null);
    setFamiliar(null);
    setTarget(null);

    setBuffs(payload.buffs ?? {});
    setSelectedPrayers(payload.selectedPrayers ?? []);
    setSelectedPerks(payload.perks?.selectedPerks ?? {});
    setItemLevel20(payload.perks?.itemLevel20 ?? false);
    setSelectedPotions(payload.potions ?? [{ pot: "NONE", stat: "ALL" }]);

    setTargetCurrentHp(
      payload.targetCurrentHp == null ? "" : String(payload.targetCurrentHp),
    );
    setTargetMaxHp(
      payload.targetMaxHp == null ? "" : String(payload.targetMaxHp),
    );

    setSelectedAbility(null);
    setDetailedResults({});
    setResults({});
  }

  async function hydratePresetUiFromPayload(payload) {
    const equipment = payload?.equipment ?? {};

    const slotToId = {
      MAINHAND: equipment.mainhandId,
      OFFHAND: equipment.offhandId,
      HEAD: equipment.headId,
      BODY: equipment.bodyId,
      LEGS: equipment.legsId,
      BOOTS: equipment.bootsId,
      GLOVES: equipment.glovesId,
      NECK: equipment.neckId,
      RING: equipment.ringId,
      CAPE: equipment.capeId,
      POCKET: equipment.pocketId,
      AMMO: equipment.ammoId,
      QUIVER: equipment.quiverId,
    };

    const equipmentIds = Object.values(slotToId).filter(Boolean);

    const [equipmentItems, allSpells, allFamiliars] = await Promise.all([
      equipmentIds.length
        ? fetchEquipmentByIds(equipmentIds)
        : Promise.resolve([]),
      payload.spell ? fetchSpells("") : Promise.resolve([]),
      payload.selectedFamiliar ? fetchFamiliars() : Promise.resolve([]),
    ]);

    const byId = new Map(
      equipmentItems.map((item) => [
        item.id,
        {
          ...item,
          name: item.name ?? item.title ?? "",
        },
      ]),
    );

    const hydratedBySlot = {};

    for (const [slot, id] of Object.entries(slotToId)) {
      if (!id) continue;
      const item = byId.get(id);
      if (item) hydratedBySlot[slot] = item;
    }

    const hydratedSpell =
      allSpells.find((spell) => spell.id === payload.spell) ?? null;

    const hydratedFamiliar =
      allFamiliars.find(
        (familiar) => familiar.id === payload.selectedFamiliar,
      ) ?? null;

    const targetItem = payload.targetTitle
      ? await fetchTargetByTitle(payload.targetTitle)
      : null;

    setTarget(targetItem);

    setSelectedEquipmentBySlot(hydratedBySlot);
    setMainhand(hydratedBySlot.MAINHAND ?? null);
    setSpell(hydratedSpell);
    setFamiliar(hydratedFamiliar);
  }

  async function handleLoadPreset() {
    const preset = presets.find((p) => p.id === selectedPresetId);
    if (!preset) return;

    applyPresetPayload(preset.payload);
    await hydratePresetUiFromPayload(preset.payload);

    setSettingsOpen(false);
  }

  function handleDeletePreset() {
    if (!selectedPresetId) return;

    const presetToDelete = presets.find((p) => p.id === selectedPresetId);
    const isDefaultPreset = DEFAULT_PRESETS.some(
      (p) => p.id === selectedPresetId,
    );

    const updated = presets.filter((p) => p.id !== selectedPresetId);
    persistPresets(updated);

    if (isDefaultPreset && presetToDelete) {
      try {
        const rawDeleted = localStorage.getItem(DELETED_DEFAULT_PRESET_IDS_KEY);
        const deletedIds = rawDeleted ? JSON.parse(rawDeleted) : [];
        const safeDeletedIds = Array.isArray(deletedIds) ? deletedIds : [];

        if (!safeDeletedIds.includes(selectedPresetId)) {
          localStorage.setItem(
            DELETED_DEFAULT_PRESET_IDS_KEY,
            JSON.stringify([...safeDeletedIds, selectedPresetId]),
          );
        }
      } catch (e) {
        console.error("Failed to store deleted default preset id", e);
      }
    }

    setSelectedPresetId("");
  }

  useEffect(() => {
    if (!base) {
      setAbilities([]);
      setResults({});
      setSelectedAbility(null);
      setDetailedResults({});
      return;
    }

    (async () => {
      try {
        const abilityData = await fetchAbilities(style);
        setAbilities(abilityData);
        setError("");
      } catch (e) {
        setError(e?.message ?? "Failed to load abilities");
      }
    })();
  }, [style, base]);

  useEffect(() => {
    if (!base) return;
    if (!abilities.length) return;

    (async () => {
      try {
        const batchPayload = {
          base,
          abilityIds: abilities.map((a) => a.ability),
        };
        const batchResults = await fetchBatchCalculation(batchPayload);
        setResults(batchResults);
        setError("");
      } catch (e) {
        setError(e?.message ?? "Batch calculation failed");
      }
    })();
  }, [base, abilities]);

  useEffect(() => {
    if (!base) return;
    if (!selectedAbility) return;

    (async () => {
      try {
        const detailedPayload = {
          ...base,
          abilityId: selectedAbility.ability,
        };
        const res = await fetchDetailedAbilityCalculation(detailedPayload);
        setDetailedResults(res);
        setError("");
      } catch (e) {
        setError(e?.message ?? "Detailed calculation failed");
      }
    })();
  }, [base, selectedAbility]);

  return (
    <div className="ability-page">
      <button
        type="button"
        className="mobile-settings-button"
        onClick={() => setSettingsOpen(true)}
      >
        ☰ Settings
      </button>

      {settingsOpen ? (
        <div
          className="mobile-settings-overlay"
          onClick={() => setSettingsOpen(false)}
        />
      ) : null}

      <div className={`combat-settings-panel ${settingsOpen ? "open" : ""}`}>
        <div className="mobile-settings-topbar">
          <h3>Settings</h3>
          <button
            type="button"
            className="mobile-close-button"
            onClick={() => setSettingsOpen(false)}
          >
            ✕
          </button>
        </div>

        <CombatSettings
          mainhand={mainhand}
          setMainhand={setMainhand}
          setEquipmentIds={setEquipmentIds}
          selectedEquipmentBySlot={selectedEquipmentBySlot}
          setSelectedEquipmentBySlot={setSelectedEquipmentBySlot}
          spell={spell}
          setSpell={setSpell}
          style={style}
          buffs={buffs}
          setBuffs={setBuffs}
          allBuffs={allBuffs}
          skills={skills}
          setSkills={setSkills}
          selectedPrayers={selectedPrayers}
          setSelectedPrayers={setSelectedPrayers}
          selectedPerks={selectedPerks}
          setSelectedPerks={setSelectedPerks}
          target={target}
          setTarget={setTarget}
          targetCurrentHp={targetCurrentHp}
          setTargetCurrentHp={setTargetCurrentHp}
          targetMaxHp={targetMaxHp}
          setTargetMaxHp={setTargetMaxHp}
          familiar={familiar}
          setFamiliar={setFamiliar}
          presets={presets}
          selectedPresetId={selectedPresetId}
          setSelectedPresetId={setSelectedPresetId}
          onLoadPreset={handleLoadPreset}
          onSavePreset={handleSavePreset}
          onDeletePreset={handleDeletePreset}
          itemLevel20={itemLevel20}
          setItemLevel20={setItemLevel20}
          selectedPotions={selectedPotions}
          setSelectedPotions={setSelectedPotions}
        />
      </div>

      <div className="ability-browser">
        {needsMainhand ? (
          <div className="start-container">
            <div className="start-card">
              <h3 className="start-title">
                Welcome to the Ability Damage Calculator
              </h3>

              <p className="start-text">
                This tool lets you build a combat setup, compare ability damage,
                and inspect detailed calculation results for individual
                abilities.
              </p>

              <h4 className="start-subtitle">Getting started</h4>
              <ol className="start-steps">
                <li>
                  Select a <b>Mainhand</b> weapon in the <b>Equipment</b>{" "}
                  section on the left.
                </li>
                <li>
                  The selected weapon determines your combat style and unlocks
                  the matching ability list.
                </li>
                <li>
                  Add the rest of your setup, such as armour, offhand, prayers,
                  potions, perks, buffs, target, and familiar.
                </li>
                <li>
                  Browse the ability list in the middle panel to compare damage
                  values.
                </li>
                <li>
                  Click any ability to view a more detailed breakdown in the
                  right panel.
                </li>
              </ol>

              <h4 className="start-subtitle">What each section does</h4>
              <ul className="start-list">
                <li>
                  <b>Presets</b> — Save and load full builds for quick testing.
                </li>
                <li>
                  <b>Equipment</b> — Select your weapon, armour, jewellery,
                  ammo, and spell where applicable.
                </li>
                <li>
                  <b>Stats</b> — Set skill levels used in the calculation.
                </li>
                <li>
                  <b>Prayer</b> — Apply active prayers for the selected combat
                  style.
                </li>
                <li>
                  <b>Potions</b> — Choose an active potion boost.
                </li>
                <li>
                  <b>Buffs</b> — Toggle supported buffs and conditional bonuses.
                </li>
                <li>
                  <b>Perks</b> — Apply invention perk ranks and related options.
                </li>
                <li>
                  <b>Target</b> — Choose the target being attacked.
                </li>
                <li>
                  <b>Familiar</b> — Select an active familiar if relevant.
                </li>
              </ul>

              <h4 className="start-subtitle">Important notes</h4>
              <ul className="start-list">
                <li>
                  A <b>Mainhand</b> weapon is required before ability
                  calculations can begin.
                </li>
                <li>
                  Some systems and values may still be in progress, so treat
                  this as a build and testing tool rather than a final
                  authoritative source.
                </li>
                <li>Saved presets are stored locally in your browser.</li>
              </ul>

              <div className="start-hint">
                Tip: most searchable fields begin returning results after typing
                at least 2 characters.
              </div>
            </div>
          </div>
        ) : (
          <Abilities
            abilities={abilities}
            results={results}
            error={error}
            selectedAbility={selectedAbility}
            onSelectedAbility={setSelectedAbility}
            weaponStyle={mainhand?.clazz}
          />
        )}
      </div>

      <div className="detailed-ability-panel">
        {!needsMainhand && (
          <AbilityStatistics
            calculationResults={detailedResults}
            selectedAbility={selectedAbility}
          />
        )}
      </div>
    </div>
  );
}
