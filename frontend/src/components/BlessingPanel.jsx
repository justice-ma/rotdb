import "../style/buffPanel.css";
import "../style/blessingPanel.css";

const BLESSING_TIERS = [
  {
    label: "Blessing Tier I",
    selectable: true,
    deriviationGroup: "LEAGUES_T1_TO_T4",
    blessings: [
      { id: "ADRENALINE_JUNKIE", god: "ZAMORAK" },
      { id: "BIG_BONED", god: "GUTHIX" },
      { id: "TERAGARDS_AEGIS", god: "SARADOMIN" },
    ],
  },
  {
    label: "Blessing Tier II",
    selectable: true,
    deriviationGroup: "LEAGUES_T1_TO_T4",
    blessings: [
      { id: "ABYSSAL_CINDERS", god: "ZAMORAK" },
      { id: "BARKSCALES", god: "GUTHIX" },
      { id: "STRIKING_LIGHT", god: "SARADOMIN" },
    ],
  },
  {
    label: "Blessing Tier III",
    selectable: true,
    deriviationGroup: "LEAGUES_T1_TO_T4",
    blessings: [
      { id: "AVERNIC_RAMPAGE", god: "ZAMORAK" },
      { id: "ETERNAL_SUSTENANCE", god: "GUTHIX" },
      { id: "STEADFAST_WILL", god: "SARADOMIN" },
    ],
  },
  {
    label: "God Tier I",
    selectable: false,
    deriviationGroup: "LEAGUES_T1_TO_T4",
    blessings: [
      { id: "DEMONS_MARK", god: "ZAMORAK" },
      { id: "SPLASH_ZONE", god: "GUTHIX" },
      { id: "SACRED_FERVOR", god: "SARADOMIN" },
    ],
  },
  {
    label: "Blessing Tier IV",
    selectable: true,
    deriviationGroup: "LEAGUES_T5_TO_T8",
    blessings: [
      { id: "HAVOC_BORN", god: "ZAMORAK" },
      { id: "TRUE_EQUILIBRIUM", god: "GUTHIX" },
      { id: "HIGHER_POWER", god: "SARADOMIN" },
    ],
  },
  {
    label: "Blessing Tier V",
    selectable: true,
    deriviationGroup: "LEAGUES_T5_TO_T8",
    blessings: [
      { id: "UNHOLY_CRITUAL", god: "ZAMORAK" },
      { id: "TEARING_THORNS", god: "GUTHIX" },
      { id: "LORD_OF_LIGHT", god: "SARADOMIN" },
    ],
  },
  {
    label: "Blessing Tier VI",
    selectable: true,
    deriviationGroup: "LEAGUES_T5_TO_T8",
    blessings: [
      { id: "PERFIDIOUS", god: "ZAMORAK" },
      { id: "ENVENOMED", god: "GUTHIX" },
      { id: "TEMPERED_HEART", god: "SARADOMIN" },
    ],
  },
  {
    label: "God Tier II",
    selectable: false,
    deriviationGroup: "LEAGUES_T5_TO_T8",
    blessings: [
      { id: "CHAOTIC_INSIGHT", god: "ZAMORAK" },
      { id: "POWER_ARCHIVE", god: "GUTHIX" },
      { id: "GENESIS_ESSENCE", god: "SARADOMIN" },
    ],
  },
  {
    label: "Relics",
    selectable: true,
    deriviationGroup: "LEAGUES_RELICS",
    blessings: [
      { id: "ICYENIC_FAITH", god: "RELIC" },
      { id: "NARAGI_EFFECT", god: "RELIC" },
      { id: "INFERNAL_FIRE", god: "RELIC" },
    ],
  },
];

const BLESSING_IDS = BLESSING_TIERS.flatMap((tier) =>
  tier.blessings.map((blessing) => blessing.id),
);
const DERIVED_TIERS = BLESSING_TIERS.filter((tier) => !tier.selectable);

function splitTierLabel(label) {
  const match = label.match(/^(Blessing Tier|God Tier) (.+)$/);
  if (!match) return [label];
  return [match[1], match[2]];
}

function getSelectableTiersForGroup(deriviationGroup) {
  return BLESSING_TIERS.filter(
    (tier) => tier.selectable && tier.deriviationGroup === deriviationGroup,
  );
}

function getDerivedTierForGroup(deriviationGroup) {
  return DERIVED_TIERS.find(
    (tier) => tier.deriviationGroup === deriviationGroup,
  );
}

function deriveGod(selectedGods, requiredSelections) {
  if (selectedGods.length !== requiredSelections) return null;

  const counts = selectedGods.reduce((acc, god) => {
    acc[god] = (acc[god] ?? 0) + 1;
    return acc;
  }, {});

  const majority = Object.entries(counts).find(([, count]) => count >= 2);
  return majority ? majority[0] : "GUTHIX";
}

function deriveTierId(deriviationGroup, selectedGods) {
  const derivedTier = getDerivedTierForGroup(deriviationGroup);
  if (!derivedTier) return null;

  const selectableTiers = getSelectableTiersForGroup(deriviationGroup);
  const god = deriveGod(selectedGods, selectableTiers.length);
  return derivedTier.blessings.find((blessing) => blessing.god === god)?.id;
}

export default function BlessingPanel({
  style,
  buffs,
  setBuffs,
  allBuffs,
  selectedPocket,
}) {
  const buffsById = new Map((allBuffs ?? []).map((buff) => [buff.id, buff]));
  const enabledBuffs = buffs?.enabledBuffs ?? [];
  const icyenicFaithSelected = enabledBuffs.includes("ICYENIC_FAITH");
  const selectedPocketName = (
    selectedPocket?.name ??
    selectedPocket?.title ??
    ""
  ).toLowerCase();
  const tomeEquipped = selectedPocketName.includes("tome of the icyene");

  function selectBlessing(tier, entry) {
    if (!tier.selectable) return;

    setBuffs((prev) => {
      const enabled = prev?.enabledBuffs ?? [];
      const tierIds = tier.blessings.map((blessing) => blessing.id);
      const alreadySelected = enabled.includes(entry.id);
      const derivedTier = getDerivedTierForGroup(tier.deriviationGroup);
      const derivedTierIds =
        derivedTier?.blessings.map((blessing) => blessing.id) ?? [];

      let nextEnabled = enabled.filter(
        (id) => !tierIds.includes(id) && !derivedTierIds.includes(id),
      );

      if (!alreadySelected) {
        nextEnabled = [...nextEnabled, entry.id];
      }

      const selectableTiers = getSelectableTiersForGroup(tier.deriviationGroup);
      const selectedGods = selectableTiers
        .map((selectableTier) => {
          const selectedId = nextEnabled.find((id) =>
            selectableTier.blessings.some((blessing) => blessing.id === id),
          );

          return selectableTier.blessings.find(
            (blessing) => blessing.id === selectedId,
          )?.god;
        })
        .filter(Boolean);

      const derivedTierId = deriveTierId(tier.deriviationGroup, selectedGods);

      if (derivedTierId) {
        nextEnabled = [...nextEnabled, derivedTierId];
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
        Blessing damage is estimated from pre-release information. Exact
        behavior may differ on launch.
      </div>

      {icyenicFaithSelected && !tomeEquipped ? (
        <div className="blessing-warning" role="alert">
          Icyenic Faith requires Tome of the Icyene in the pocket slot.
        </div>
      ) : null}

      {BLESSING_TIERS.map((tier) => (
        <div key={tier.label} className="blessing-tier-row">
          <div className="blessing-tier-label">
            {splitTierLabel(tier.label).map((part) => (
              <span key={part}>{part}</span>
            ))}
          </div>

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
                  data-label={blessing.label}
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
