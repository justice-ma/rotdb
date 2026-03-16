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
  onBeginEdit,
  disabled = false,
}) {
  const placeholder = `${slot.charAt(0) + slot.slice(1).toLowerCase()}…`;

  return (
    <div className="slot-block">
      <div className="slot-input-wrap">
        <input
          className={`slot-input ${disabled ? "slot-input-disabled" : ""}`}
          value={disabled ? "" : query}
          onChange={(e) => {
            if (disabled) return;
            if (selected) onBeginEdit(slot);
            setQueryForSlot(slot, e.target.value);
          }}
          placeholder={
            disabled
              ? "Offhand unavailable with a two-handed mainhand"
              : placeholder
          }
          autoComplete="off"
          readOnly={disabled}
        />
        {!disabled && loading ? (
          <span className="slot-spinner" aria-hidden="true" />
        ) : null}
      </div>

      {!disabled && error ? <div className="slot-error">{error}</div> : null}

      {!disabled && !loading && results.length > 0 ? (
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
  targetCurrentHp,
  setTargetCurrentHp,
  targetMaxHp,
  setTargetMaxHp,
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
  targetSize,
  setTargetSize,
}) {
  const [editingSlot, setEditingSlot] = useState(null);
  const DEFAULT_BOOK_UPTIME = 66;

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

  const isTwoHandedMainhand =
    selectedEquipmentBySlot?.MAINHAND?.slot === "TWOHANDED";

  const selectedPocket = selectedEquipmentBySlot?.POCKET;

  const pocketSupportsBookUptime = useMemo(() => {
    const pocketName = selectedPocket?.name?.toLowerCase?.() ?? "";

    return (
      pocketName.includes("scripture of ful") ||
      pocketName.includes("scripture of amascut")
    );
  }, [selectedPocket]);

  function setQueryForSlot(slot, value) {
    setQueries((prev) => ({ ...prev, [slot]: value }));
  }

  async function searchEquipmentForSlot(slot, query, signal) {
    if (slot === "MAINHAND") {
      const [mainhandData, twoHandedData] = await Promise.all([
        fetchEquipmentBySlot("MAINHAND", query, signal),
        fetchEquipmentBySlot("TWOHANDED", query, signal),
      ]);

      const combined = [
        ...(Array.isArray(mainhandData) ? mainhandData : []),
        ...(Array.isArray(twoHandedData) ? twoHandedData : []),
      ];

      const seen = new Set();

      return combined.filter((item) => {
        if (!item?.id || seen.has(item.id)) return false;
        seen.add(item.id);
        return true;
      });
    }

    const data = await fetchEquipmentBySlot(slot, query, signal);
    return Array.isArray(data) ? data : [];
  }

  function onPick(slot, item) {
    setEditingSlot((prev) => (prev === slot ? null : prev));

    setSelectedEquipmentBySlot((prev) => {
      const next = { ...prev, [slot]: item };

      if (slot === "MAINHAND" && item?.slot === "TWOHANDED") {
        delete next.OFFHAND;
      }

      return next;
    });

    setQueries((prev) => {
      const next = { ...prev, [slot]: item.name };

      if (slot === "MAINHAND" && item?.slot === "TWOHANDED") {
        next.OFFHAND = "";
      }

      return next;
    });

    setResultsBySlot((prev) => {
      const next = { ...prev, [slot]: [] };

      if (slot === "MAINHAND" && item?.slot === "TWOHANDED") {
        next.OFFHAND = [];
      }

      return next;
    });

    setErrorBySlot((prev) => {
      const next = { ...prev, [slot]: "" };

      if (slot === "MAINHAND" && item?.slot === "TWOHANDED") {
        next.OFFHAND = "";
      }

      return next;
    });

    setLoadingBySlot((prev) => {
      if (!(slot === "MAINHAND" && item?.slot === "TWOHANDED")) return prev;
      return { ...prev, OFFHAND: false };
    });

    if (typeof setEquipmentIds === "function") {
      setEquipmentIds((prev) => {
        const next = { ...(prev ?? {}), [slot]: item.id };

        if (slot === "MAINHAND" && item?.slot === "TWOHANDED") {
          delete next.OFFHAND;
        }

        return next;
      });
    }

    if (slot === "MAINHAND" && typeof setMainhand === "function") {
      setMainhand(item);
    }

    if (slot === "POCKET") {
      const pocketName = item?.name?.toLowerCase?.() ?? "";
      const supportsBookUptime =
        pocketName.includes("scripture of ful") ||
        pocketName.includes("scripture of amascut");

      if (supportsBookUptime) {
        const buff = (allBuffs ?? []).find((b) => b.id === "BOOKUPTIME");
        const min = buff?.min ?? 0;
        const max = buff?.max ?? 100;
        const defaultValue = Math.max(min, Math.min(max, DEFAULT_BOOK_UPTIME));

        setBuffs((prev) => {
          const enabled = prev?.enabledBuffs ?? [];
          const stacks = prev?.buffStacks ?? {};

          if (stacks.BOOKUPTIME !== undefined && stacks.BOOKUPTIME !== null) {
            return {
              ...prev,
              enabledBuffs: enabled.includes("BOOKUPTIME")
                ? enabled
                : [...enabled, "BOOKUPTIME"],
            };
          }

          return {
            ...prev,
            enabledBuffs: enabled.includes("BOOKUPTIME")
              ? enabled
              : [...enabled, "BOOKUPTIME"],
            buffStacks: {
              ...stacks,
              BOOKUPTIME: defaultValue,
            },
          };
        });
      }
    }
  }

  function onBeginEdit(slot) {
    setEditingSlot(slot);

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
    setEditingSlot((prev) => (prev === slot ? null : prev));

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

  const [bookUptimeTouched, setBookUptimeTouched] = useState(false);

  useEffect(() => {
    setBookUptimeTouched(false);
  }, [selectedPocket]);

  function updateBookUptime(value) {
    const buff = (allBuffs ?? []).find((b) => b.id === "BOOKUPTIME");
    const min = buff?.min ?? 0;
    const max = buff?.max ?? 100;

    setBuffs((prev) => {
      const enabled = prev?.enabledBuffs ?? [];
      const stacks = { ...(prev?.buffStacks ?? {}) };

      if (value === "") {
        return {
          ...prev,
          enabledBuffs: enabled.includes("BOOKUPTIME")
            ? enabled
            : [...enabled, "BOOKUPTIME"],
          buffStacks: {
            ...stacks,
            BOOKUPTIME: "",
          },
        };
      }

      const numericValue = Number(value);
      const clamped = Math.max(min, Math.min(max, numericValue));

      return {
        ...prev,
        enabledBuffs: enabled.includes("BOOKUPTIME")
          ? enabled
          : [...enabled, "BOOKUPTIME"],
        buffStacks: {
          ...stacks,
          BOOKUPTIME: Number.isNaN(clamped) ? "" : clamped,
        },
      };
    });
  }

  useEffect(() => {
    if (!isTwoHandedMainhand) return;
    if (!selectedEquipmentBySlot?.OFFHAND) return;

    onClear("OFFHAND");
  }, [isTwoHandedMainhand, selectedEquipmentBySlot]);

  useEffect(() => {
    setQueries((prev) => {
      const next = { ...prev };

      for (const slot of SLOTS) {
        const selected = selectedEquipmentBySlot?.[slot];

        if (selected?.name) {
          next[slot] = selected.name;
        } else if (editingSlot !== slot) {
          next[slot] = "";
        }
      }

      if (isTwoHandedMainhand) {
        next.OFFHAND = "";
      }

      return next;
    });
  }, [selectedEquipmentBySlot, isTwoHandedMainhand, editingSlot]);

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
    if (pocketSupportsBookUptime) return;

    setBuffs((prev) => {
      const enabled = prev?.enabledBuffs ?? [];
      const stacks = prev?.buffStacks ?? {};

      if (!enabled.includes("BOOKUPTIME") && stacks.BOOKUPTIME === undefined) {
        return prev;
      }

      const nextEnabled = enabled.filter((id) => id !== "BOOKUPTIME");
      const nextStacks = { ...stacks };
      delete nextStacks.BOOKUPTIME;

      return {
        ...prev,
        enabledBuffs: nextEnabled,
        buffStacks: nextStacks,
      };
    });
  }, [pocketSupportsBookUptime, setBuffs]);

  useEffect(() => {
    const controllers = {};
    const timers = [];

    for (const slot of SLOTS) {
      if (slot === "OFFHAND" && isTwoHandedMainhand) {
        setResultsBySlot((prev) => ({ ...prev, [slot]: [] }));
        setLoadingBySlot((prev) => ({ ...prev, [slot]: false }));
        setErrorBySlot((prev) => ({ ...prev, [slot]: "" }));
        continue;
      }

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
          const data = await searchEquipmentForSlot(slot, q, controller.signal);

          setResultsBySlot((prev) => ({
            ...prev,
            [slot]: data,
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
  }, [queries, selectedEquipmentBySlot, isTwoHandedMainhand]);

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
                onBeginEdit={onBeginEdit}
                disabled={slot === "OFFHAND" && isTwoHandedMainhand}
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

            {pocketSupportsBookUptime ? (
              <div className="slot-block">
                <div className="book-uptime-field">
                  <input
                    id="book-uptime"
                    className="slot-input"
                    type="number"
                    min="0"
                    max="100"
                    step="1"
                    value={
                      !bookUptimeTouched && buffs?.buffStacks?.BOOKUPTIME === 66
                        ? ""
                        : (buffs?.buffStacks?.BOOKUPTIME ?? "")
                    }
                    onChange={(e) => {
                      setBookUptimeTouched(true);
                      updateBookUptime(e.target.value);
                    }}
                    placeholder="Book uptime (Defaults to 66%)"
                  />
                </div>
              </div>
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
          <TargetPanel
            target={target}
            setTarget={setTarget}
            targetCurrentHp={targetCurrentHp}
            setTargetCurrentHp={setTargetCurrentHp}
            targetMaxHp={targetMaxHp}
            setTargetMaxHp={setTargetMaxHp}
            targetSize={targetSize}
            setTargetSize={setTargetSize}
          />
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
