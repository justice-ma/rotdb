import { useEffect, useMemo, useState } from "react";
import { fetchEquipmentBySlot } from "../api/api";
import "../style/combatSettings.css";

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
}) {
  const placeholder = `${slot.charAt(0) + slot.slice(1).toLowerCase()}…`;

  return (
    <div className="slot-block">
      <div className="slot-input-wrap">
        <input
          className="slot-input"
          value={query}
          onChange={(e) => {
            if (selected) onClear(slot);
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
          {results.slice(0, 8).map((item) => (
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

export default function CombatSettings({
  mainhand,
  setMainhand,
  setEquipmentIds,
  buffs,
  setBuffs,
}) {
  const [queries, setQueries] = useState(
    Object.fromEntries(SLOTS.map((s) => [s, ""])),
  );

  const [resultsBySlot, setResultsBySlot] = useState({});
  const [selectedBySlot, setSelectedBySlot] = useState({});
  const [loadingBySlot, setLoadingBySlot] = useState({});
  const [errorBySlot, setErrorBySlot] = useState({});

  function setQueryForSlot(slot, value) {
    setQueries((prev) => ({ ...prev, [slot]: value }));
  }

  function onPick(slot, item) {
    setSelectedBySlot((prev) => ({ ...prev, [slot]: item }));
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

  function onClear(slot) {
    setSelectedBySlot((prev) => {
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

  useEffect(() => {
    if (!mainhand) return;
    setSelectedBySlot((prev) => ({ ...prev, MAINHAND: mainhand }));
    setQueries((prev) => ({ ...prev, MAINHAND: mainhand.name ?? "" }));

    setEquipmentIds?.((prev) => ({ ...(prev ?? {}), MAINHAND: mainhand.id }));
  }, [mainhand, setEquipmentIds]);

  useEffect(() => {
    const controllers = {};
    const timers = [];

    for (const slot of SLOTS) {
      const q = (queries[slot] ?? "").trim();

      const selected = selectedBySlot[slot];
      if (selected && q === selected.name) continue;

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
  }, [queries, selectedBySlot]);

  const equipmentIdsPayload = useMemo(
    () =>
      Object.fromEntries(
        SLOTS.map((slot) => [slot, selectedBySlot[slot]?.id ?? null]),
      ),
    [selectedBySlot],
  );

  return (
    <div className="combat-settings">
      <details className="cs-section" open>
        <summary className="cs-summary">
          <span>Equipment</span>
        </summary>

        <div className="cs-body">
          <div className="slots-grid">
            {SLOTS.map((slot) => (
              <SlotSearch
                key={slot}
                slot={slot}
                query={queries[slot]}
                setQueryForSlot={setQueryForSlot}
                results={resultsBySlot[slot] ?? []}
                loading={!!loadingBySlot[slot]}
                error={errorBySlot[slot]}
                onPick={onPick}
                selected={selectedBySlot[slot]}
                onClear={onClear}
              />
            ))}
          </div>
        </div>
      </details>
    </div>
  );
}
