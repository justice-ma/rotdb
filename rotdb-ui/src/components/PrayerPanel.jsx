import { useEffect, useMemo, useState } from "react";
import { fetchPrayers } from "../api/api";
import "../style/prayerPanel.css";

export default function PrayerPanel({
  style,
  selectedPrayers,
  setSelectedPrayers,
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

      <div className="prayer-results">
        {results.map((prayer) => {
          const selected = selectedPrayers.includes(prayer.id);

          return (
            <div key={prayer.id} className="prayer-row">
              <button
                type="button"
                className={`prayer ${selected ? "selected" : ""}`}
                aria-pressed={selected}
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
