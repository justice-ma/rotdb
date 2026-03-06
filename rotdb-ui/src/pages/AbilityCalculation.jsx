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

export default function AbilityCalculation() {
  const [abilities, setAbilities] = useState([]);
  const [results, setResults] = useState({});
  const [detailedResults, setDetailedResults] = useState({});
  const [error, setError] = useState("");
  const [mainhand, setMainhand] = useState(null);
  const [style, setStyle] = useState("RANGED");
  const [selectedAbility, setSelectedAbility] = useState(null);
  const [equipmentIds, setEquipmentIds] = useState({});
  const [buffs, setBuffs] = useState({});
  const [allBuffs, setAllBuffs] = useState([]);

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
    if (!mainhand?.clazz) return;

    setStyle((prev) => (prev === mainhand.clazz ? prev : mainhand.clazz));

    setSelectedAbility(null);
    setDetailedResults({});
  }, [mainhand]);

  const base = useMemo(() => {
    const mainhandId = equipmentIds.MAINHAND ?? null;

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
      targetTitle: "Training dummy",
      spell: "INCITEFEAR",
      relic: null,
    };
  }, [equipmentIds, buffs]);

  const needsMainhand = !equipmentIds.MAINHAND;

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
          style={style}
          buffs={buffs}
          setBuffs={setBuffs}
          allBuffs={allBuffs}
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
