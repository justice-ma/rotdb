import { useEffect, useMemo, useState } from "react";

import Abilities from "../components/Abilities";
import AbilityStatistics from "../components/AbilityStatistics";
import CombatSettings from "../components/CombatSettings";

import {
  fetchAbilities,
  fetchBatchCalculation,
  fetchDetailedAbilityCalculation,
  fetchBuffs,
} from "../api/api";

import "../style/abilityPage.css";

const STORAGE_KEY = "rs3-presets";

const DEFAULT_SKILLS = {
  necromancy: 120,
  constitution: 99,
  strength: 120,
  ranged: 120,
  magic: 120,
  attack: 120,
  defence: 99,
  summoning: 99,
};

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
      const stored = JSON.parse(localStorage.getItem(STORAGE_KEY) || "[]");
      setPresets(Array.isArray(stored) ? stored : []);
    } catch (e) {
      console.error("Failed to load presets", e);
      setPresets([]);
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
      },
      buffs,
      selectedPrayers,
      potions: selectedPotions,
      perks: {
        selectedPerks,
        itemLevel20,
      },
      targetTitle: target?.name ?? "Training dummy",
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

  function applyPresetPayload(payload, uiState = {}) {
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
    });

    setBuffs(payload.buffs ?? {});
    setSelectedPrayers(payload.selectedPrayers ?? []);
    setSelectedPerks(payload.perks?.selectedPerks ?? {});
    setSelectedPotions(payload?.potions ?? [{ pot: "NONE", stat: "ALL" }]);

    setMainhand(uiState.mainhand ?? null);
    setSpell(uiState.spell ?? null);
    setFamiliar(uiState.familiar ?? null);
    setTarget(uiState.target ?? null);
    setSelectedEquipmentBySlot(uiState.selectedEquipmentBySlot ?? {});

    setSelectedAbility(null);
    setDetailedResults({});
    setResults({});
  }

  function handleLoadPreset() {
    const preset = presets.find((p) => p.id === selectedPresetId);
    if (!preset) return;

    applyPresetPayload(preset.payload, preset.uiState);
    setSettingsOpen(false);
  }

  function handleDeletePreset() {
    if (!selectedPresetId) return;

    const updated = presets.filter((p) => p.id !== selectedPresetId);
    persistPresets(updated);
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
