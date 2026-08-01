import "../style/buffPanel.css";
import "../style/blessingPanel.css";

const BLESSING_TIERS = [
  {
    label: "Tier 1",
    selectable: true,
    blessings: [
      { id: "ADRENALINE_JUNKIE", god: "ZAMORAK" },
      { id: "BIG_BONED", god: "GUTHIX" },
      { id: "TERAGARDS_AEGIS", god: "SARADOMIN" },
    ],
  },
  {
    label: "Tier 2",
    selectable: true,
    blessings: [
      { id: "ABYSSAL_CINDERS", god: "ZAMORAK" },
      { id: "BARKSCALES", god: "GUTHIX" },
      { id: "STRIKING_LIGHT", god: "SARADOMIN" },
    ],
  },
  {
    label: "Tier 3",
    selectable: true,
    blessings: [
      { id: "AVERNIC_RAMPAGE", god: "ZAMORAK" },
      { id: "ETERNAL_SUSTENANCE", god: "GUTHIX" },
      { id: "STEADFAST_WILL", god: "SARADOMIN" },
    ],
  },
  {
    label: "Tier 4",
    selectable: false,
    blessings: [
      { id: "DEMONS_MARK", god: "ZAMORAK" },
      { id: "SPLASH_ZONE", god: "GUTHIX" },
      { id: "SACRED_FERVOR", god: "SARADOMIN" },
    ],
  },
];

const SELECTABLE_TIERS = BLESSING_TIERS.filter((tier) => tier.selectable);
const TIER_FOUR = BLESSING_TIERS.find((tier) => !tier.selectable);
const BLESSING_IDS = BLESSING_TIERS.flatMap((tier) =>
  tier.blessings.map((blessing) => blessing.id),
);
const TIER_FOUR_IDS = TIER_FOUR.blessings.map((blessing) => blessing.id);

function deriveTier4God(selectedGods) {
  if (selectedGods.length !== SELECTABLE_TIERS.length) return null;

  const counts = selectedGods.reduce((acc, god) => {
    acc[god] = (acc[god] ?? 0) + 1;
    return acc;
  }, {});

  const majority = Object.entries(counts).find(([, count]) => count >= 2);
  return majority ? majority[0] : "GUTHIX";
}

function deriveTier4Id(selectedGods) {
  const god = deriveTier4God(selectedGods);
  return TIER_FOUR.blessings.find((blessing) => blessing.god === god)?.id;
}

export default function BlessingPanel({ style, buffs, setBuffs, allBuffs }) {
  const buffsById = new Map((allBuffs ?? []).map((buff) => [buff.id, buff]));

  function selectBlessing(tier, entry) {
    if (!tier.selectable) return;

    setBuffs((prev) => {
      const enabled = prev?.enabledBuffs ?? [];
      const tierIds = tier.blessings.map((blessing) => blessing.id);
      const alreadySelected = enabled.includes(entry.id);

      let nextEnabled = enabled.filter(
        (id) => !tierIds.includes(id) && !TIER_FOUR_IDS.includes(id),
      );

      if (!alreadySelected) {
        nextEnabled = [...nextEnabled, entry.id];
      }

      const selectedGods = SELECTABLE_TIERS.map((selectableTier) => {
        const selectedId = nextEnabled.find((id) =>
          selectableTier.blessings.some((blessing) => blessing.id === id),
        );

        return selectableTier.blessings.find(
          (blessing) => blessing.id === selectedId,
        )?.god;
      }).filter(Boolean);

      const tierFourId = deriveTier4Id(selectedGods);

      if (tierFourId) {
        nextEnabled = [...nextEnabled, tierFourId];
      }

      return {
        ...prev,
        enabledBuffs: nextEnabled,
        buffStacks: prev?.buffStacks ?? {},
      };
    });
  }

  return (
    <div className="blessing-tier-list">
      <div className="blessing-notice" role="note">
        Blessing damage is estimated from pre-release information. Exact behavior
        may differ on launch.
      </div>

      {BLESSING_TIERS.map((tier) => (
        <div key={tier.label} className="blessing-tier-row">
          <div className="blessing-tier-label">{tier.label}</div>

          <div className="blessing-tier-grid">
            {tier.blessings.map((entry) => {
              const blessing = buffsById.get(entry.id);
              if (!blessing) return null;

              const selected = (buffs?.enabledBuffs ?? []).includes(
                blessing.id,
              );

              return (
                <button
                  key={entry.id}
                  type="button"
                  className={selected ? "blessing selected" : "blessing"}
                  onClick={() => selectBlessing(tier, entry)}
                  disabled={!tier.selectable}
                  title={blessing.label}
                  aria-label={blessing.label}
                >
                  {blessing.iconPath && (
                    <img src={blessing.iconPath} alt="" draggable={false} />
                  )}
                </button>
              );
            })}
          </div>
        </div>
      ))}
    </div>
  );
}
