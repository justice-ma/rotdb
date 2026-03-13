import { useRef } from "react";
import "../style/statPanel.css";

const SKILL_FIELDS = [
  { key: "attack", label: "Attack" },
  { key: "strength", label: "Strength" },
  { key: "magic", label: "Magic" },
  { key: "ranged", label: "Ranged" },
  { key: "necromancy", label: "Necromancy" },
  { key: "currentHp", label: "Current HP" },
  { key: "maxHp", label: "Maximum HP" },
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
      {SKILL_FIELDS.map((skill) => {
        if (skill.key === "currentHp") {
          return (
            <div key="hp-row" className="stat-row hp-row">
              <label className="stat-label">Health Points</label>

              <div className="hp-input-group">
                <input
                  id="currentHp"
                  ref={(el) => (inputRefs.current.currentHp = el)}
                  type="number"
                  min="1"
                  max="20000"
                  value={skills.currentHp ?? ""}
                  onClick={(e) => e.stopPropagation()}
                  onChange={(e) =>
                    handleSkillChange("currentHp", e.target.value)
                  }
                  className="stat-input hp-input"
                />

                <span className="hp-divider">/</span>

                <input
                  id="maxHp"
                  ref={(el) => (inputRefs.current.maxHp = el)}
                  type="number"
                  min="1"
                  max="20000"
                  value={skills.maxHp ?? ""}
                  onClick={(e) => e.stopPropagation()}
                  onChange={(e) => handleSkillChange("maxHp", e.target.value)}
                  className="stat-input hp-input"
                />
              </div>
            </div>
          );
        }

        if (skill.key === "maxHp") return null;

        return (
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
              className="stat-input"
            />
          </div>
        );
      })}
    </div>
  );
}
