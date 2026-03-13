import { useEffect, useState } from "react";
import { fetchPotions } from "../api/api";
import "../style/potionsPanel.css";

export default function PotionsPanel({ selectedPotions, setSelectedPotions }) {
  const [potions, setPotions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const controller = new AbortController();

    async function loadPotions() {
      try {
        setLoading(true);
        setError("");

        const data = await fetchPotions(controller.signal);
        setPotions(Array.isArray(data) ? data : []);
      } catch (e) {
        if (e?.name === "AbortError") return;
        setPotions([]);
        setError("Failed to load potions");
      } finally {
        setLoading(false);
      }
    }

    loadPotions();

    return () => controller.abort();
  }, []);

  const selectedPotion = selectedPotions?.[0] ?? {
    pot: "NONE",
    stat: "ALL",
  };

  function handleChange(e) {
    const pot = e.target.value;

    setSelectedPotions([
      {
        pot,
        stat: "ALL",
      },
    ]);
  }

  if (loading) {
    return <div className="potions-panel">Loading potions...</div>;
  }

  if (error) {
    return <div className="potions-panel potion-error">{error}</div>;
  }

  return (
    <div className="potions-panel">
      <select
        className="potion-select"
        value={selectedPotion.pot}
        onChange={handleChange}
      >
        {potions.map((potion) => (
          <option key={potion.id} value={potion.id}>
            {potion.name}
          </option>
        ))}
      </select>
    </div>
  );
}
