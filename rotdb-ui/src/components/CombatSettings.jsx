import { useEffect, useMemo, useState } from "react";
import { fetchEquipmentBySlot, fetchSpells } from "../api/api";
import "../style/combatSettings.css";
import BuffPanel from "./BuffPanel";
import StatPanel from "./StatPanel";
import PrayerPanel from "./PrayerPanel";
import PerksPanel from "./PerksPanel";
import TargetPanel from "./TargetPanel";
import FamiliarPanel from "./FamiliarPanel";
import PresetSelector from "./PresetSelector";
import PotionsPanel from "./PotionPanel";

const SLOTS = [
  "MAINHAND",
  "OFFHAND",
  "HEAD",
  "BODY",
  "LEGS",
  "BOOTS",
  "GLOVES",
  "NECK",
  "RING",
  "CAPE",
  "POCKET",
  "QUIVER",
  "AMMO",
];

function SlotSearch({
  slot,
  query,
  setQueryForSlot,
  results,
  loading,
  error,
  onPick,
  selected,
  onClear,
  onBeginEdit,
}) {
  const placeholder = `${slot.charAt(0) + slot.slice(1).toLowerCase()}…`;

  return (
    <div className="slot-block">
      <div className="slot-input-wrap">
        <input
          className="slot-input"
          value={query}
          onChange={(e) => {
            if (selected) onBeginEdit(slot);
            setQueryForSlot(slot, e.target.value);
          }}
          placeholder={placeholder}
          autoComplete="off"
        />
        {loading ? <span className="slot-spinner" aria-hidden="true" /> : null}
      </div>

      {error ? <div className="slot-error">{error}</div> : null}

      {!loading && results.length > 0 ? (
        <ul className="slot-results">
          {results.slice(0, 15).map((item) => (
            <li
              key={item.id}
              className="slot-result"
              onClick={() => onPick(slot, item)}
              role="button"
              tabIndex={0}
              onKeyDown={(e) => {
                if (e.key === "Enter" || e.key === " ") onPick(slot, item);
              }}
            >
              {item.name}
            </li>
          ))}
        </ul>
      ) : null}
    </div>
  );
}

function SpellSearch({
  query,
  setQuery,
  results,
  loading,
  error,
  selected,
  onPick,
  onClear,
}) {
  return (
    <div className="slot-block">
      <div className="slot-input-wrap">
        <input
          className="slot-input"
          value={query}
          onChange={(e) => {
            if (selected) onClear();
            setQuery(e.target.value);
          }}
          placeholder="Spell…"
          autoComplete="off"
        />
        {loading ? <span className="slot-spinner" aria-hidden="true" /> : null}
      </div>

      {error ? <div className="slot-error">{error}</div> : null}

      {!loading && results.length > 0 ? (
        <ul className="slot-results">
          {results.slice(0, 15).map((item) => (
            <li
              key={item.id}
              className="slot-result"
              onClick={() => onPick(item)}
              role="button"
              tabIndex={0}
              onKeyDown={(e) => {
                if (e.key === "Enter" || e.key === " ") onPick(item);
              }}
            >
              {item.name}
            </li>
          ))}
        </ul>
      ) : null}
    </div>
  );
}

export default function CombatSettings({
  mainhand,
  setMainhand,
  setEquipmentIds,
  selectedEquipmentBySlot,
  setSelectedEquipmentBySlot,
  spell,
  setSpell,
  style,
  buffs,
  setBuffs,
  allBuffs,
  skills,
  setSkills,
  selectedPrayers,
  setSelectedPrayers,
  selectedPerks,
  setSelectedPerks,
  target,
  setTarget,
  familiar,
  setFamiliar,
  presets,
  selectedPresetId,
  setSelectedPresetId,
  onLoadPreset,
  onSavePreset,
  onDeletePreset,
  itemLevel20,
  setItemLevel20,
  selectedPotions,
  setSelectedPotions,
}) {
  const visibleSlots = useMemo(() => {
    if (style === "RANGED") return SLOTS;
    return SLOTS.filter((s) => s !== "AMMO");
  }, [style]);

  const [queries, setQueries] = useState(
    Object.fromEntries(SLOTS.map((s) => [s, ""])),
  );

  const [resultsBySlot, setResultsBySlot] = useState({});
  const [loadingBySlot, setLoadingBySlot] = useState({});
  const [errorBySlot, setErrorBySlot] = useState({});

  const [spellQuery, setSpellQuery] = useState("");
  const [spellResults, setSpellResults] = useState([]);
  const [spellLoading, setSpellLoading] = useState(false);
  const [spellError, setSpellError] = useState("");

  function setQueryForSlot(slot, value) {
    setQueries((prev) => ({ ...prev, [slot]: value }));
  }

  function onPick(slot, item) {
    setSelectedEquipmentBySlot((prev) => ({ ...prev, [slot]: item }));
    setQueries((prev) => ({ ...prev, [slot]: item.name }));
    setResultsBySlot((prev) => ({ ...prev, [slot]: [] }));
    setErrorBySlot((prev) => ({ ...prev, [slot]: "" }));

    if (typeof setEquipmentIds === "function") {
      setEquipmentIds((prev) => ({ ...(prev ?? {}), [slot]: item.id }));
    }

    if (slot === "MAINHAND" && typeof setMainhand === "function") {
      setMainhand(item);
    }
  }

  function onBeginEdit(slot) {
    setSelectedEquipmentBySlot((prev) => {
      const next = { ...prev };
      delete next[slot];
      return next;
    });

    setResultsBySlot((prev) => ({ ...prev, [slot]: [] }));
    setErrorBySlot((prev) => ({ ...prev, [slot]: "" }));
    setLoadingBySlot((prev) => ({ ...prev, [slot]: false }));

    if (typeof setEquipmentIds === "function") {
      setEquipmentIds((prev) => {
        const next = { ...(prev ?? {}) };
        delete next[slot];
        return next;
      });
    }

    if (slot === "MAINHAND" && typeof setMainhand === "function") {
      setMainhand(null);
    }
  }

  function onClear(slot) {
    setSelectedEquipmentBySlot((prev) => {
      const next = { ...prev };
      delete next[slot];
      return next;
    });

    setQueries((prev) => ({ ...prev, [slot]: "" }));
    setResultsBySlot((prev) => ({ ...prev, [slot]: [] }));
    setErrorBySlot((prev) => ({ ...prev, [slot]: "" }));
    setLoadingBySlot((prev) => ({ ...prev, [slot]: false }));

    if (typeof setEquipmentIds === "function") {
      setEquipmentIds((prev) => {
        const next = { ...(prev ?? {}) };
        delete next[slot];
        return next;
      });
    }

    if (slot === "MAINHAND" && typeof setMainhand === "function") {
      setMainhand(null);
    }
  }

  function onPickSpell(item) {
    setSpell(item);
    setSpellQuery(item.name);
    setSpellResults([]);
    setSpellError("");
  }

  function onClearSpell() {
    setSpell(null);
    setSpellQuery("");
    setSpellResults([]);
    setSpellError("");
    setSpellLoading(false);
  }

  useEffect(() => {
    setQueries((prev) => {
      const next = { ...prev };

      for (const slot of SLOTS) {
        const selected = selectedEquipmentBySlot?.[slot];
        next[slot] = selected?.name ?? "";
      }

      return next;
    });
  }, [selectedEquipmentBySlot]);

  useEffect(() => {
    if (!mainhand) return;

    setSelectedEquipmentBySlot((prev) => ({
      ...prev,
      MAINHAND: mainhand,
    }));

    setQueries((prev) => ({ ...prev, MAINHAND: mainhand.name ?? "" }));

    setEquipmentIds?.((prev) => ({ ...(prev ?? {}), MAINHAND: mainhand.id }));
  }, [mainhand, setEquipmentIds, setSelectedEquipmentBySlot]);

  useEffect(() => {
    if (!spell) {
      setSpellQuery("");
      return;
    }

    setSpellQuery(spell.name ?? "");
  }, [spell]);

  useEffect(() => {
    const controllers = {};
    const timers = [];

    for (const slot of SLOTS) {
      const q = (queries[slot] ?? "").trim();
      const selected = selectedEquipmentBySlot?.[slot];

      if (selected && q === selected.name) {
        setLoadingBySlot((prev) => ({ ...prev, [slot]: false }));
        continue;
      }

      if (q.length < 2) {
        setResultsBySlot((prev) => ({ ...prev, [slot]: [] }));
        setLoadingBySlot((prev) => ({ ...prev, [slot]: false }));
        setErrorBySlot((prev) => ({ ...prev, [slot]: "" }));
        continue;
      }

      const controller = new AbortController();
      controllers[slot] = controller;

      setLoadingBySlot((prev) => ({ ...prev, [slot]: true }));
      setErrorBySlot((prev) => ({ ...prev, [slot]: "" }));

      const t = setTimeout(async () => {
        try {
          const data = await fetchEquipmentBySlot(slot, q, controller.signal);

          setResultsBySlot((prev) => ({
            ...prev,
            [slot]: Array.isArray(data) ? data : [],
          }));
          setLoadingBySlot((prev) => ({ ...prev, [slot]: false }));
        } catch (e) {
          if (e?.name === "AbortError") return;
          setResultsBySlot((prev) => ({ ...prev, [slot]: [] }));
          setLoadingBySlot((prev) => ({ ...prev, [slot]: false }));
          setErrorBySlot((prev) => ({ ...prev, [slot]: "Search failed" }));
        }
      }, 250);

      timers.push(t);
    }

    return () => {
      timers.forEach(clearTimeout);
      Object.values(controllers).forEach((c) => c.abort());
    };
  }, [queries, selectedEquipmentBySlot]);

  useEffect(() => {
    if (style !== "MAGIC") {
      setSpellResults([]);
      setSpellLoading(false);
      setSpellError("");
      return;
    }

    const q = spellQuery.trim();

    if (spell && q === spell.name) return;

    if (q.length < 2) {
      setSpellResults([]);
      setSpellLoading(false);
      setSpellError("");
      return;
    }

    const controller = new AbortController();

    setSpellLoading(true);
    setSpellError("");

    const t = setTimeout(async () => {
      try {
        const data = await fetchSpells(q, controller.signal);
        setSpellResults(Array.isArray(data) ? data : []);
        setSpellLoading(false);
      } catch (e) {
        if (e?.name === "AbortError") return;
        setSpellResults([]);
        setSpellLoading(false);
        setSpellError("Search failed");
      }
    }, 250);

    return () => {
      clearTimeout(t);
      controller.abort();
    };
  }, [spellQuery, spell, style]);

  return (
    <div className="combat-settings">
      <details className="cs-section" open>
        <summary className="cs-summary">Presets</summary>
        <div className="cs-body">
          <PresetSelector
            presets={presets}
            selectedPresetId={selectedPresetId}
            setSelectedPresetId={setSelectedPresetId}
            onLoadPreset={onLoadPreset}
            onSavePreset={onSavePreset}
            onDeletePreset={onDeletePreset}
          />
        </div>
      </details>

      <details className="cs-section" open>
        <summary className="cs-summary">
          <span>Equipment</span>
        </summary>

        <div className="cs-body">
          <div className="slots-grid">
            {visibleSlots.map((slot) => (
              <SlotSearch
                key={slot}
                slot={slot}
                query={queries[slot]}
                setQueryForSlot={setQueryForSlot}
                results={resultsBySlot[slot] ?? []}
                loading={!!loadingBySlot[slot]}
                error={errorBySlot[slot]}
                onPick={onPick}
                selected={selectedEquipmentBySlot?.[slot]}
                onClear={onClear}
                onBeginEdit={onBeginEdit}
              />
            ))}

            {style === "MAGIC" ? (
              <SpellSearch
                query={spellQuery}
                setQuery={setSpellQuery}
                results={spellResults}
                loading={spellLoading}
                error={spellError}
                selected={spell}
                onPick={onPickSpell}
                onClear={onClearSpell}
              />
            ) : null}
          </div>
        </div>
      </details>

      <details className="cs-section">
        <summary className="cs-summary">Stats</summary>
        <div className="cs-body">
          <StatPanel skills={skills} setSkills={setSkills} />
        </div>
      </details>

      <details className="cs-section">
        <summary className="cs-summary">Prayer</summary>
        <div className="cs-body">
          <PrayerPanel
            style={style}
            selectedPrayers={selectedPrayers}
            setSelectedPrayers={setSelectedPrayers}
            buffs={buffs}
            setBuffs={setBuffs}
            allBuffs={allBuffs}
          />
        </div>
      </details>

      <details className="cs-section">
        <summary className="cs-summary">Potions</summary>
        <div className="cs-body">
          <PotionsPanel
            selectedPotions={selectedPotions}
            setSelectedPotions={setSelectedPotions}
          />
        </div>
      </details>

      <details className="cs-section">
        <summary className="cs-summary">Buffs</summary>
        <div className="cs-body">
          <BuffPanel
            style={style}
            buffs={buffs}
            setBuffs={setBuffs}
            allBuffs={allBuffs}
          />
        </div>
      </details>

      <details className="cs-section">
        <summary className="cs-summary">Perks</summary>
        <div className="cs-body">
          <PerksPanel
            selectedPerks={selectedPerks}
            setSelectedPerks={setSelectedPerks}
            itemLevel20={itemLevel20}
            setItemLevel20={setItemLevel20}
          />
        </div>
      </details>

      <details className="cs-section">
        <summary className="cs-summary">Target</summary>
        <div className="cs-body">
          <TargetPanel target={target} setTarget={setTarget} />
        </div>
      </details>

      <details className="cs-section">
        <summary className="cs-summary">Familiar</summary>
        <div className="cs-body">
          <FamiliarPanel familiar={familiar} setFamiliar={setFamiliar} />
        </div>
      </details>
    </div>
  );
}
