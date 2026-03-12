import { useEffect, useMemo, useState } from "react";
import { fetchPrayers } from "../api/api";
import "../style/prayerPanel.css";

const PRAYER_MOVED_BUFF_META = {
  ECLIPSEDSOUL: {
    book: "NORMAL",
  },
};

export default function PrayerPanel({
  style,
  selectedPrayers,
  setSelectedPrayers,
  buffs,
  setBuffs,
  allBuffs,
}) {
  const [query, setQuery] = useState("");
  const [allPrayers, setAllPrayers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [activeBook, setActiveBook] = useState("NORMAL");

  useEffect(() => {
    const controller = new AbortController();

    setLoading(true);
    setError("");

    const t = setTimeout(async () => {
      try {
        const data = await fetchPrayers("", controller.signal);

        const filtered = Array.isArray(data)
          ? data.filter((prayer) => prayer.styles?.includes(style))
          : [];

        setAllPrayers(filtered);
        setLoading(false);
      } catch (e) {
        if (e?.name === "AbortError") return;
        setAllPrayers([]);
        setLoading(false);
        setError("Search failed");
      }
    }, 250);

    return () => {
      clearTimeout(t);
      controller.abort();
    };
  }, [style]);

  const splitSoulEnabled = (buffs?.enabledBuffs ?? []).includes("SPLITSOUL");

  const results = useMemo(() => {
    const normalizedQuery = query.trim().toLowerCase();

    return allPrayers.filter((prayer) => {
      const matchesBook = prayer.book === activeBook;
      const matchesQuery =
        normalizedQuery === "" ||
        prayer.name?.toLowerCase().includes(normalizedQuery);

      return matchesBook && matchesQuery;
    });
  }, [allPrayers, activeBook, query]);

  const prayerMovedBuffs = useMemo(() => {
    const normalizedQuery = query.trim().toLowerCase();

    return (allBuffs ?? [])
      .filter((buff) => PRAYER_MOVED_BUFF_META[buff.id])
      .map((buff) => ({
        ...buff,
        ...PRAYER_MOVED_BUFF_META[buff.id],
      }))
      .filter((buff) => buff.book === activeBook)
      .filter((buff) => {
        if (!normalizedQuery) return true;
        return buff.label?.toLowerCase().includes(normalizedQuery);
      });
  }, [allBuffs, activeBook, query]);

  function groupsOverlap(a = [], b = []) {
    return a.some((group) => b.includes(group));
  }

  function canStackTogether(a, b) {
    if (!a || !b) return false;

    if (a.id === "DIVINE_RAGE" && b.stackableWithDivineRage) return true;
    if (b.id === "DIVINE_RAGE" && a.stackableWithDivineRage) return true;

    return false;
  }

  function togglePrayer(clickedPrayer) {
    if (splitSoulEnabled && clickedPrayer.book === "NORMAL") {
      return;
    }

    const isCurrentlySelected = selectedPrayers.includes(clickedPrayer.id);

    setSelectedPrayers((prev) => {
      const isSelected = prev.includes(clickedPrayer.id);

      if (isSelected) {
        return prev.filter((id) => id !== clickedPrayer.id);
      }

      const selectedPrayerObjects = allPrayers.filter((prayer) =>
        prev.includes(prayer.id),
      );

      let nextSelected = selectedPrayerObjects.filter(
        (prayer) => prayer.book === clickedPrayer.book,
      );

      nextSelected = nextSelected.filter((prayer) => {
        if (canStackTogether(prayer, clickedPrayer)) {
          return true;
        }

        return !groupsOverlap(
          prayer.exclusivityGroups ?? [],
          clickedPrayer.exclusivityGroups ?? [],
        );
      });

      return [...nextSelected.map((prayer) => prayer.id), clickedPrayer.id];
    });

    if (!isCurrentlySelected && clickedPrayer.book === "CURSES") {
      setBuffs((prev) => {
        const enabled = prev?.enabledBuffs ?? [];
        if (!enabled.includes("ECLIPSEDSOUL")) return prev;

        return {
          ...prev,
          enabledBuffs: enabled.filter((id) => id !== "ECLIPSEDSOUL"),
        };
      });
    }
  }

  useEffect(() => {
    if (!splitSoulEnabled) return;

    setSelectedPrayers([]);

    setBuffs((prev) => {
      const enabled = prev?.enabledBuffs ?? [];
      if (!enabled.includes("ECLIPSEDSOUL")) return prev;

      return {
        ...prev,
        enabledBuffs: enabled.filter((id) => id !== "ECLIPSEDSOUL"),
      };
    });
  }, [splitSoulEnabled, setSelectedPrayers, setBuffs]);

  function togglePrayerMovedBuff(buffId) {
    setBuffs((prev) => {
      const enabled = prev?.enabledBuffs ?? [];
      const stacks = prev?.buffStacks ?? {};
      const isEnabled = enabled.includes(buffId);

      if (isEnabled) {
        const nextEnabled = enabled.filter((id) => id !== buffId);
        const nextStacks = { ...stacks };
        delete nextStacks[buffId];

        return {
          ...prev,
          enabledBuffs: nextEnabled,
          buffStacks: nextStacks,
        };
      }

      return {
        ...prev,
        enabledBuffs: [...enabled, buffId],
        buffStacks: stacks,
      };
    });

    if (
      buffId === "ECLIPSEDSOUL" &&
      !(buffs?.enabledBuffs ?? []).includes(buffId)
    ) {
      setSelectedPrayers((prev) => {
        const curseIds = allPrayers
          .filter((prayer) => prayer.book === "CURSES")
          .map((prayer) => prayer.id);

        return prev.filter((id) => !curseIds.includes(id));
      });
    }
  }

  return (
    <div className="prayer-panel">
      <div className="prayer-tabs">
        <button
          type="button"
          className={`prayer-tab ${activeBook === "NORMAL" ? "active" : ""}`}
          onClick={() => setActiveBook("NORMAL")}
        >
          Normal
        </button>

        <button
          type="button"
          className={`prayer-tab ${activeBook === "CURSES" ? "active" : ""}`}
          onClick={() => setActiveBook("CURSES")}
        >
          Curses
        </button>
      </div>

      <div className="slot-input-wrap">
        <input
          className="slot-input"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search prayers…"
          autoComplete="off"
        />
        {loading ? <span className="slot-spinner" aria-hidden="true" /> : null}
      </div>

      {error ? <div className="slot-error">{error}</div> : null}

      {splitSoulEnabled && activeBook === "NORMAL" ? (
        <div className="slot-error">
          Normal prayers cannot be selected while Split Soul is active.
        </div>
      ) : null}

      <div className="prayer-results">
        {prayerMovedBuffs.map((buff) => {
          const enabled = (buffs?.enabledBuffs ?? []).includes(buff.id);
          const disabled = splitSoulEnabled && buff.book === "NORMAL";

          return (
            <div key={buff.id} className="prayer-row">
              <button
                type="button"
                className={`prayer ${enabled ? "selected" : ""}`}
                aria-pressed={enabled}
                disabled={disabled}
                onClick={() => togglePrayerMovedBuff(buff.id)}
              >
                <span>{buff.label}</span>
              </button>
            </div>
          );
        })}

        {results.map((prayer) => {
          const selected = selectedPrayers.includes(prayer.id);
          const disabled = splitSoulEnabled && prayer.book === "NORMAL";

          return (
            <div key={prayer.id} className="prayer-row">
              <button
                type="button"
                className={`prayer ${selected ? "selected" : ""}`}
                aria-pressed={selected}
                disabled={disabled}
                onClick={() => togglePrayer(prayer)}
              >
                <span>{prayer.name}</span>
              </button>
            </div>
          );
        })}
      </div>
    </div>
  );
}
