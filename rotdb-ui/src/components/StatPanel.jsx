import { useRef } from "react";
import "../style/statPanel.css";

const SKILL_FIELDS = [
  { key: "attack", label: "Attack" },
  { key: "strength", label: "Strength" },
  { key: "magic", label: "Magic" },
  { key: "ranged", label: "Ranged" },
  { key: "necromancy", label: "Necromancy" },
];

export default function StatsPanel({ skills, setSkills }) {
  const inputRefs = useRef({});

  function handleSkillChange(skillKey, value) {
    const parsed = value === "" ? "" : Number(value);

    setSkills((prev) => ({
      ...prev,
      [skillKey]: parsed,
    }));
  }

  function focusInput(skillKey) {
    inputRefs.current[skillKey]?.focus();
    inputRefs.current[skillKey]?.select();
  }

  return (
    <div className="stats-grid">
      {SKILL_FIELDS.map((skill) => (
        <div
          key={skill.key}
          className="stat-row"
          onClick={() => focusInput(skill.key)}
        >
          <label htmlFor={skill.key} className="stat-label">
            {skill.label}
          </label>

          <input
            id={skill.key}
            ref={(el) => {
              inputRefs.current[skill.key] = el;
            }}
            type="number"
            min="1"
            max="120"
            value={skills[skill.key] ?? ""}
            onClick={(e) => e.stopPropagation()}
            onChange={(e) => handleSkillChange(skill.key, e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                e.currentTarget.blur();
              }
            }}
            className="stat-input"
          />
        </div>
      ))}
    </div>
  );
}
