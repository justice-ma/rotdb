import { useEffect, useRef, useState } from "react";
import { fetchPerks } from "../api/api";
import "../style/perksPanel.css";

export default function PerksPanel({
  selectedPerks,
  setSelectedPerks,
  buffs,
  genocidalRank,
  setGenocidalRank,
  itemLevel20,
  setItemLevel20,
}) {
  const [perks, setPerks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [perkRanks, setPerkRanks] = useState({});
  const inputRefs = useRef({});
  const hasPowerArchive = (buffs?.enabledBuffs ?? []).includes("POWER_ARCHIVE");

  useEffect(() => {
    const controller = new AbortController();

    async function loadPerks() {
      try {
        setLoading(true);
        setError("");

        const data = await fetchPerks(controller.signal);
        setPerks(Array.isArray(data) ? data : []);
        setLoading(false);
      } catch (e) {
        if (e?.name === "AbortError") return;
        setPerks([]);
        setLoading(false);
        setError("Failed to load perks");
      }
    }

    loadPerks();

    return () => controller.abort();
  }, []);

  useEffect(() => {
    setPerkRanks((prev) => {
      const next = { ...prev };

      for (const [perkId, rank] of Object.entries(selectedPerks ?? {})) {
        next[perkId] = rank;
      }

      return next;
    });
  }, [selectedPerks]);

  useEffect(() => {
    if (hasPowerArchive || !perks.length) return;

    setPerkRanks((prev) => clampPerkRanks(prev, perks, false));
    setSelectedPerks((prev) => clampPerkRanks(prev, perks, false));
  }, [hasPowerArchive, perks, setSelectedPerks]);

  function getPerkMax(perk) {
    const max = perk.max ?? 1;
    return hasPowerArchive && perk.id !== "GENOCIDAL" ? max * 2 : max;
  }

  function isFixedRankPerk(perk) {
    return perk.id !== "GENOCIDAL" && (perk.max ?? 1) === 1;
  }

  function clampPerkRanks(ranks, perkList, powerArchiveActive) {
    if (!ranks) return ranks;

    let changed = false;
    const byId = new Map(perkList.map((perk) => [perk.id, perk]));
    const next = { ...ranks };

    for (const [perkId, rank] of Object.entries(next)) {
      if (perkId === "GENOCIDAL" || rank === "") continue;

      const perk = byId.get(perkId);
      if (!perk) continue;

      const max = powerArchiveActive ? (perk.max ?? 1) * 2 : (perk.max ?? 1);
      const clamped = Math.max(1, Math.min(max, Number(rank)));

      if (!Number.isNaN(clamped) && clamped !== rank) {
        next[perkId] = clamped;
        changed = true;
      }
    }

    return changed ? next : ranks;
  }

  function focusInput(perkId) {
    const input = inputRefs.current[perkId];
    if (!input) return;
    input.focus();
    input.select();
  }

  function activatePerk(perk) {
    setSelectedPerks((prev) => {
      if (prev?.[perk.id] != null) return prev;

      return {
        ...(prev ?? {}),
        [perk.id]:
          perk.id === "GENOCIDAL" || isFixedRankPerk(perk)
            ? 1
            : (perkRanks[perk.id] ?? 1),
      };
    });
  }

  function deactivatePerk(perkId) {
    setSelectedPerks((prev) => {
      const next = { ...(prev ?? {}) };
      delete next[perkId];
      return next;
    });

    if (document.activeElement === inputRefs.current[perkId]) {
      inputRefs.current[perkId]?.blur();
    }

    if (perkId === "GENOCIDAL") {
      setGenocidalRank(null);
    }
  }

  function togglePerk(perk) {
    const isSelected = selectedPerks?.[perk.id] != null;

    if (isSelected) {
      deactivatePerk(perk.id);
      return;
    }

    activatePerk(perk);

    requestAnimationFrame(() => {
      focusInput(perk.id);
    });
  }

  function updatePerkRank(perk, value) {
    if (perk.id === "GENOCIDAL") {
      if (value === "") {
        setGenocidalRank(null);
        return;
      }

      const parsed = Number(value);
      if (Number.isNaN(parsed)) return;

      const clamped = Math.max(0, Math.min(4.9, parsed));
      setGenocidalRank(clamped);
      activatePerk(perk);
      return;
    }

    if (isFixedRankPerk(perk)) {
      setPerkRanks((prev) => ({
        ...prev,
        [perk.id]: 1,
      }));
      activatePerk(perk);
      return;
    }

    setPerkRanks((prev) => ({
      ...prev,
      [perk.id]: value,
    }));

    if (value === "") {
      setSelectedPerks((prev) => {
        if (prev?.[perk.id] == null) return prev;
        return {
          ...(prev ?? {}),
          [perk.id]: "",
        };
      });
      return;
    }

    const parsed = Number(value);
    if (Number.isNaN(parsed)) return;

    const max = getPerkMax(perk);
    const clamped = Math.max(1, Math.min(max, parsed));

    setSelectedPerks((prev) => ({
      ...(prev ?? {}),
      [perk.id]: clamped,
    }));
  }

  function normalizePerkRank(perk) {
    if (perk.id === "GENOCIDAL") {
      if (genocidalRank == null || genocidalRank === "") {
        if (selectedPerks?.[perk.id] != null) {
          setGenocidalRank(0.0);
        }
        return;
      }

      const parsed = Number(genocidalRank);
      const clamped = Number.isNaN(parsed) ? 0 : Math.max(0, Math.min(4.9, parsed));
      setGenocidalRank(clamped);
      return;
    }

    if (isFixedRankPerk(perk)) {
      setPerkRanks((prev) => ({
        ...prev,
        [perk.id]: 1,
      }));

      if (selectedPerks?.[perk.id] != null) {
        setSelectedPerks((prev) => ({
          ...(prev ?? {}),
          [perk.id]: 1,
        }));
      }

      return;
    }

    const current = perkRanks[perk.id];

    if (current == null || current === "") {
      setPerkRanks((prev) => ({
        ...prev,
        [perk.id]: 1,
      }));

      if (selectedPerks?.[perk.id] != null) {
        setSelectedPerks((prev) => ({
          ...(prev ?? {}),
          [perk.id]: 1,
        }));
      }

      return;
    }

    const parsed = Number(current);
    const max = getPerkMax(perk);
    const clamped = Number.isNaN(parsed)
      ? 1
      : Math.max(1, Math.min(max, parsed));

    setPerkRanks((prev) => ({
      ...prev,
      [perk.id]: clamped,
    }));

    if (selectedPerks?.[perk.id] != null) {
      setSelectedPerks((prev) => ({
        ...(prev ?? {}),
        [perk.id]: clamped,
      }));
    }
  }

  function handleInputFocus(perk) {
    activatePerk(perk);
  }

  if (loading) {
    return <div className="perks-panel">Loading perks...</div>;
  }

  if (error) {
    return <div className="perks-panel perk-error">{error}</div>;
  }

  return (
    <div className="perks-panel">
      <div className="perk-toolbar">
        <button
          type="button"
          className={`perk-toggle-button ${itemLevel20 ? "active" : ""}`}
          onClick={() => setItemLevel20((prev) => !prev)}
        >
          Item Level 20
        </button>
      </div>

      <div className="perks-grid">
        {perks.map((perk) => {
          const selected = selectedPerks?.[perk.id] != null;
          const fixedRank = isFixedRankPerk(perk);
          const rank =
            perk.id === "GENOCIDAL"
              ? (genocidalRank ?? "")
              : fixedRank
                ? 1
                : (perkRanks[perk.id] ?? "");

          return (
            <div
              key={perk.id}
              className={`perk-row ${selected ? "selected" : ""}`}
              onClick={() => togglePerk(perk)}
            >
              <span className="perk-label">{perk.name}</span>

              {!fixedRank && (
                <input
                  ref={(el) => {
                    inputRefs.current[perk.id] = el;
                  }}
                  className="perk-rank-input"
                  type="number"
                  min={perk.id === "GENOCIDAL" ? 0 : 1}
                  max={perk.id === "GENOCIDAL" ? 4.9 : getPerkMax(perk)}
                  step={perk.id === "GENOCIDAL" ? 0.1 : 1}
                  value={rank}
                  placeholder={perk.id === "GENOCIDAL" ? "0.0" : "1"}
                  onMouseDown={(e) => e.stopPropagation()}
                  onClick={(e) => e.stopPropagation()}
                  onFocus={() => handleInputFocus(perk)}
                  onChange={(e) => updatePerkRank(perk, e.target.value)}
                  onBlur={() => normalizePerkRank(perk)}
                  onKeyDown={(e) => {
                    e.stopPropagation();
                    if (e.key === "Enter") {
                      e.currentTarget.blur();
                    }
                  }}
                />
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
}
