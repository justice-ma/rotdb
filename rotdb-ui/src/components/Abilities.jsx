import { useState } from "react";
import "../style/abilityCards.css";

export default function Abilities({
  abilities,
  results,
  error,
  selectedAbility,
  onSelectedAbility,
  weaponStyle,
}) {
  const [specialFilter, setSpecialFilter] = useState("COMMON");

  const grouped = abilities.reduce((acc, a) => {
    const key = a.tier ?? "Other";
    (acc[key] ??= []).push(a);
    return acc;
  }, {});

  const typeOrder = ["BASIC", "THRESHOLD", "ULTIMATE", "SPECIAL", "OTHER"];
  const orderedTypes = Object.keys(grouped).sort((x, y) => {
    const ax = typeOrder.indexOf(x);
    const ay = typeOrder.indexOf(y);
    return (ax === -1 ? 999 : ax) - (ay === -1 ? 999 : ay);
  });

  const isUsableWithWeapon = (ability) => {
    if (!weaponStyle) return true;

    const abilityStyle =
      ability.style ?? ability.combatStyle ?? ability.weaponStyle ?? "ANY";

    return abilityStyle === "ANY" || abilityStyle === weaponStyle;
  };

  const handleSelect = (ability) => {
    if (!isUsableWithWeapon(ability)) return;
    onSelectedAbility(ability);
  };

  const getAbilitiesForType = (type) => {
    const items = grouped[type] ?? [];

    if (type !== "SPECIAL") {
      return items;
    }

    if (specialFilter === "ALL") {
      return items;
    }

    return items.filter((a) => a.common);
  };

  return (
    <div className="abilities-root">
      {error && <div className="abilities-error">{error}</div>}

      {orderedTypes.map((type) => {
        const sectionAbilities = getAbilitiesForType(type);

        return (
          <section key={type} className="ability-section">
            <div className="ability-section-header">
              <h3 className="ability-section-title">{type}</h3>

              {type === "SPECIAL" ? (
                <div className="special-filter">
                  <button
                    type="button"
                    className={specialFilter === "COMMON" ? "active" : ""}
                    onClick={() => setSpecialFilter("COMMON")}
                  >
                    Commonly Used
                  </button>

                  <button
                    type="button"
                    className={specialFilter === "ALL" ? "active" : ""}
                    onClick={() => setSpecialFilter("ALL")}
                  >
                    All
                  </button>
                </div>
              ) : null}
            </div>

            <div className="ability-card-container">
              {sectionAbilities.map((a) => {
                const result = results?.[a.ability];
                const isSelected = selectedAbility?.ability === a.ability;
                const usable = isUsableWithWeapon(a);
                const isSpecial = a.iconPath?.includes("special");

                return (
                  <div
                    key={a.ability}
                    className={[
                      "ability-card",
                      isSelected ? "selected" : "",
                      usable ? "ability-usable" : "ability-disabled",
                    ].join(" ")}
                    onClick={() => handleSelect(a)}
                    role="button"
                    tabIndex={0}
                    aria-disabled={!usable}
                    onKeyDown={(e) => {
                      if (e.key === "Enter" || e.key === " ") {
                        handleSelect(a);
                      }
                    }}
                  >
                    <div className="ability-card-row">
                      <img
                        className={`ability-icon ${isSpecial ? "special-icon" : ""}`}
                        src={a.iconPath}
                        alt={a.name}
                      />

                      <div className="ability-card-content">
                        <div className="ability-card-primary">
                          <p className="ability-name" title={a.name}>
                            {a.name}
                          </p>
                          <p className="ability-damage">{result?.avg ?? "-"}</p>
                        </div>

                        {result && (
                          <div className="ability-card-secondary">
                            <p className="subtle-text">
                              {result.min}% - {result.max}%
                            </p>
                            <p className="subtle-text">Avg</p>
                          </div>
                        )}

                        {!usable && weaponStyle ? (
                          <div className="ability-tag">
                            Not {weaponStyle.toLowerCase()}
                          </div>
                        ) : null}
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>
          </section>
        );
      })}
    </div>
  );
}
