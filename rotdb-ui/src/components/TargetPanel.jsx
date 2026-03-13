import { useEffect, useState } from "react";
import { fetchTargets } from "../api/api";
import "../style/targetPanel.css";

export default function TargetPanel({
  target,
  setTarget,
  targetCurrentHp,
  setTargetCurrentHp,
  targetMaxHp,
  setTargetMaxHp,
}) {
  const [query, setQuery] = useState("");
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const defaultMaxHp = target?.lifepoints1 ?? 100000;

  function onPick(item) {
    setTarget(item);
    setQuery(item.name);
    setResults([]);
    setError("");

    const hp = item?.lifepoints1 ?? "";
    setTargetCurrentHp(hp === "" ? "" : String(hp));
    setTargetMaxHp(hp === "" ? "" : String(hp));
  }

  function onClear() {
    setTarget(null);
    setQuery("");
    setResults([]);
    setError("");
    setLoading(false);
    setTargetCurrentHp("");
    setTargetMaxHp("");
  }

  useEffect(() => {
    const q = query.trim();

    if (target && q === target.name) return;

    if (q.length < 2) {
      setResults([]);
      setLoading(false);
      setError("");
      return;
    }

    const controller = new AbortController();

    setLoading(true);
    setError("");

    const t = setTimeout(async () => {
      try {
        const data = await fetchTargets(q, controller.signal);
        setResults(Array.isArray(data) ? data : []);
        setLoading(false);
      } catch (e) {
        if (e?.name === "AbortError") return;
        setResults([]);
        setLoading(false);
        setError("Search failed");
      }
    }, 250);

    return () => {
      clearTimeout(t);
      controller.abort();
    };
  }, [query, target]);

  useEffect(() => {
    if (!target) {
      setQuery("");
      return;
    }

    setQuery(target.name ?? "");
  }, [target]);

  return (
    <div className="target-panel">
      <div className="slot-block">
        <div className="slot-input-wrap">
          <input
            className="slot-input"
            value={query}
            onChange={(e) => {
              if (target) onClear();
              setQuery(e.target.value);
            }}
            placeholder="Target…"
            autoComplete="off"
          />
          {loading ? (
            <span className="slot-spinner" aria-hidden="true" />
          ) : null}
        </div>

        {error ? <div className="slot-error">{error}</div> : null}

        {!loading && results.length > 0 ? (
          <ul className="slot-results">
            {results.slice(0, 15).map((item) => (
              <li
                key={item.id ?? item.name}
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

      <div className="target-hint">
        <p>
          Current target: <b>{target?.name ?? "Training Dummy"}</b>
        </p>

        <div className="target-hp-row">
          <label htmlFor="target-current-hp">HP</label>

          <input
            id="target-current-hp"
            type="number"
            min="1"
            value={targetCurrentHp}
            onChange={(e) => setTargetCurrentHp(e.target.value)}
            placeholder={String(defaultMaxHp)}
            className="target-hp-input"
          />

          <span className="target-hp-divider">/</span>

          <input
            id="target-max-hp"
            type="number"
            min="1"
            value={targetMaxHp}
            onChange={(e) => setTargetMaxHp(e.target.value)}
            placeholder={String(defaultMaxHp)}
            className="target-hp-input"
          />
        </div>

        {!target ? (
          <>
            <p>Default target behavior:</p>
            <ul>
              <li>100% accuracy</li>
              <li>No defensive modifiers</li>
              <li>All buffs and debuffs are applicable and can be toggled</li>
            </ul>
          </>
        ) : (
          <p className="target-subtext">
            Selected target will use its own stats and modifiers.
          </p>
        )}
      </div>
    </div>
  );
}
