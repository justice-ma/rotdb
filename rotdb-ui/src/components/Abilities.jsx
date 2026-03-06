import "../style/abilityCards.css";

export default function Abilities({
  abilities,
  results,
  error,
  selectedAbility,
  onSelectedAbility,
  weaponStyle,
}) {
  const grouped = abilities.reduce((acc, a) => {
    const key = a.tier ?? "Other";
    (acc[key] ??= []).push(a);
    return acc;
  }, {});

  const typeOrder = ["Basic", "Threshold", "Ultimate", "Special", "Other"];
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

  return (
    <div className="abilities-root">
      {error && <div className="abilities-error">{error}</div>}

      {orderedTypes.map((type) => (
        <section key={type} className="ability-section">
          <h3 className="ability-section-title">{type}</h3>

          <div className="ability-card-container">
            {grouped[type].map((a) => {
              const result = results?.[a.ability];
              const isSelected = selectedAbility?.ability === a.ability;
              const usable = isUsableWithWeapon(a);

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
                    if (e.key === "Enter" || e.key === " ") handleSelect(a);
                  }}
                >
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
              );
            })}
          </div>
        </section>
      ))}
    </div>
  );
}
