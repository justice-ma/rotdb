import { useEffect, useMemo, useState } from "react";

import Abilities from "../components/Abilities";
import AbilityStatistics from "../components/AbilityStatistics";
import CombatSettings from "../components/CombatSettings";

import {
  fetchAbilities,
  fetchBatchCalculation,
  fetchDetailedAbilityCalculation,
} from "../api/api";

import "../style/abilityPage.css";

export default function AbilityCalculation() {
  const [abilities, setAbilities] = useState([]);
  const [results, setResults] = useState({});
  const [detailedResults, setDetailedResults] = useState({});
  const [error, setError] = useState("");

  // weapon selected in CombatSettings
  const [mainhand, setMainhand] = useState(null);

  // combat style used to fetch abilities (MELEE/RANGED/MAGIC/etc.)
  const [style, setStyle] = useState("RANGED");

  // current selected ability
  const [selectedAbility, setSelectedAbility] = useState(null);

  // equipmentIds in slot shape: { MAINHAND: 3119, OFFHAND: 123, ... }
  const [equipmentIds, setEquipmentIds] = useState({});

  // buffs state for payload
  const [buffs, setBuffs] = useState({
    enabledBuffs: ["SHARDOFGENESIS", "REAPERSCREW"],
    buffStacks: {},
  });

  // keep style in sync with picked mainhand.clazz
  useEffect(() => {
    if (!mainhand?.clazz) return;

    setStyle((prev) => (prev === mainhand.clazz ? prev : mainhand.clazz));

    // reset selection + detailed panel when swapping weapon styles
    setSelectedAbility(null);
    setDetailedResults({});
  }, [mainhand]);

  // Build request base payload from state (single source of truth)
  const base = useMemo(() => {
    const mainhandId = equipmentIds.MAINHAND ?? null;

    // If no mainhand, return null so we can gate requests
    if (!mainhandId) return null;

    return {
      skills: {
        strength: 120,
        magic: 120,
        ranged: 120,
        necromancy: 120,
        attack: 120,
      },
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
      selectedPrayers: [],
      potions: [{ pot: "ELDER", stat: "ALL" }],
      perks: {
        selectedPerks: { PRECISE: 6, ERUPTIVE: 2, ULTIMATUMS: 4 },
      },
      targetTitle: "Arch-Glacor",
      spell: "INCITEFEAR",
      relic: null,
    };
  }, [equipmentIds, buffs]);

  const needsMainhand = !equipmentIds.MAINHAND;

  // Load abilities ONLY when we have a mainhand
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

  // Batch calculate ability card values
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

  // Detailed calculations for selected ability
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
          buffs={buffs}
          setBuffs={setBuffs}
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
