import { useEffect, useState } from "react";
import { fetchFamiliars } from "../api/api";
import "../style/familiarPanel.css";

export default function FamiliarPanel({ familiar, setFamiliar }) {
  const [familiars, setFamiliars] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const controller = new AbortController();

    async function loadFamiliars() {
      try {
        setLoading(true);
        setError("");

        const data = await fetchFamiliars(controller.signal);
        setFamiliars(Array.isArray(data) ? data : []);
        setLoading(false);
      } catch (e) {
        if (e?.name === "AbortError") return;
        setFamiliars([]);
        setLoading(false);
        setError("Failed to load familiars");
      }
    }

    loadFamiliars();

    return () => controller.abort();
  }, []);

  function handleChange(e) {
    const selectedId = e.target.value;

    if (!selectedId) {
      setFamiliar(null);
      return;
    }

    const selected = familiars.find((f) => String(f.id) === selectedId) ?? null;
    setFamiliar(selected);
  }

  if (loading) {
    return <div className="familiar-panel">Loading familiars...</div>;
  }

  if (error) {
    return <div className="familiar-panel familiar-error">{error}</div>;
  }

  return (
    <div className="familiar-panel">
      <select
        className="familiar-select"
        value={familiar?.id ?? ""}
        onChange={handleChange}
      >
        <option value="">No familiar</option>
        {familiars.map((familiarOption) => (
          <option key={familiarOption.id} value={familiarOption.id}>
            {familiarOption.name}
          </option>
        ))}
      </select>
    </div>
  );
}
