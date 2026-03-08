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
      <div className="combat-settings-panel">
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
              <h3 className="start-title">Start here</h3>
              <ol className="start-steps">
                <li>
                  Select a <b>Mainhand</b> weapon on the left.
                </li>
                <li>Pick buffs you want enabled.</li>
                <li>Select an ability to see detailed results.</li>
              </ol>
              <div className="start-hint">
                Tip: type 2+ characters to search.
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
        {needsMainhand ? (
          <div className="start-card subtle">
            <h3 className="start-title">No weapon selected</h3>
            <p className="start-text">
              Pick a mainhand to enable calculations.
            </p>
          </div>
        ) : (
          <AbilityStatistics
            calculationResults={detailedResults}
            selectedAbility={selectedAbility}
          />
        )}
      </div>
    </div>
  );
}
