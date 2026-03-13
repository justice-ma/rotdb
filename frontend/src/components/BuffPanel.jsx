import { useEffect, useState } from "react";
import "../style/buffPanel.css";

const BUFF_CATEGORY = {
  TARGET: "Target",
  SPECIAL_ATTACKS: "Specials",
  ENCHANTMENTS: "Enchants",
  ABILITY_BUFFS: "Ability",
  SLAYER_BUFFS: "Slayer",
  PASSIVE_BUFFS: "Passive",
  STACKS: "Stacks",
  OTHER: "Other",
};

const STYLE = {
  MELEE: "MELEE",
  RANGED: "RANGED",
  MAGIC: "MAGIC",
  NECROMANCY: "NECROMANCY",
};

const PRAYER_MOVED_BUFF_IDS = ["ECLIPSEDSOUL"];

const BUFF_UI_META = {
  ENCHANTMENTOFSAVAGERY: {
    category: BUFF_CATEGORY.ENCHANTMENTS,
    styles: [STYLE.MELEE],
  },
  ENCHANTMENTOFAGONY: {
    category: BUFF_CATEGORY.ENCHANTMENTS,
    styles: [STYLE.MELEE],
  },
  ENCHANTMENTOFHEROISM: {
    category: BUFF_CATEGORY.ENCHANTMENTS,
    styles: [STYLE.MELEE],
  },
  ENCHANTMENTOFDISPELLING: {
    category: BUFF_CATEGORY.ENCHANTMENTS,
    styles: [STYLE.RANGED],
  },
  ENCHANTMENTOFDREAD: {
    category: BUFF_CATEGORY.ENCHANTMENTS,
    styles: [STYLE.RANGED],
  },
  ENCHANTMENTOFSHADOWS: {
    category: BUFF_CATEGORY.ENCHANTMENTS,
    styles: [STYLE.RANGED],
  },
  ENCHANTMENTOFAFFLICTION: {
    category: BUFF_CATEGORY.ENCHANTMENTS,
    styles: [STYLE.MAGIC],
  },
  ENCHANTMENTOFFLAMES: {
    category: BUFF_CATEGORY.ENCHANTMENTS,
    styles: [STYLE.MAGIC],
  },
  ENCHANTMENTOFMETAPHYSICS: {
    category: BUFF_CATEGORY.ENCHANTMENTS,
    styles: [STYLE.MAGIC],
  },
  SMASH: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.MELEE],
  },
  CHAOSROAR: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.MELEE],
  },
  SUNSHINE: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.MAGIC],
  },
  DEATHSWIFTNESS: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.RANGED],
  },
  BERSERK: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.MELEE],
  },
  ZGS: {
    category: BUFF_CATEGORY.SPECIAL_ATTACKS,
    styles: [STYLE.MELEE],
  },
  UNDEADSLAYERSIGIL: {
    category: BUFF_CATEGORY.SLAYER_BUFFS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  DRAGONSLAYERSIGIL: {
    category: BUFF_CATEGORY.SLAYER_BUFFS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  DEMONSLAYERSIGIL: {
    category: BUFF_CATEGORY.SLAYER_BUFFS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  REAPERSCREW: {
    category: BUFF_CATEGORY.PASSIVE_BUFFS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  RUNICCHARGE: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.MAGIC],
  },
  KALG: {
    category: BUFF_CATEGORY.OTHER,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  ECLIPSEDSOUL: {
    category: BUFF_CATEGORY.OTHER,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  SPLITSOUL: {
    category: BUFF_CATEGORY.SPECIAL_ATTACKS,
    styles: [STYLE.RANGED, STYLE.NECROMANCY],
  },
  INSTABILITY: {
    category: BUFF_CATEGORY.SPECIAL_ATTACKS,
    styles: [STYLE.MAGIC],
  },
  BALANCEBYFORCE: {
    category: BUFF_CATEGORY.SPECIAL_ATTACKS,
    styles: [STYLE.RANGED],
  },
  DBA: {
    category: BUFF_CATEGORY.SPECIAL_ATTACKS,
    styles: [STYLE.MELEE],
  },
  DRAGONSCIMITAR: {
    category: BUFF_CATEGORY.SPECIAL_ATTACKS,
    styles: [STYLE.MELEE],
  },
  GALES: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.RANGED],
  },
  FURYBUFF: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.MELEE],
  },
  GREATERFURYBUFF: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.MELEE],
    stackable: false,
  },
  CONCENTRATEDBLASTBUFF: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.MAGIC],
  },
  GREATERCONCENTRATEDBLASTBUFF: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.MAGIC],
  },
  RAPIDFIREBUFF: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.RANGED],
  },
  ASPHYXIATEBUFF: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.MAGIC],
  },
  CHILL: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.MELEE],
  },
  BLOODLUST: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.MELEE],
  },
  CONFLAGRATE: {
    category: BUFF_CATEGORY.SPECIAL_ATTACKS,
    styles: [STYLE.MAGIC],
  },
  STONEOFJAS: {
    category: BUFF_CATEGORY.OTHER,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  RUBYAURORA: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  GRAVITATE: {
    category: BUFF_CATEGORY.SPECIAL_ATTACKS,
    styles: [STYLE.MELEE],
  },
  WENSTACKS: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.RANGED],
  },
  REVENGESTACKS: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  RUTHELESSSTACKS: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  GUARDHOUSE: {
    category: BUFF_CATEGORY.SLAYER_BUFFS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  TITHESTACKS: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.MAGIC],
  },
  PUZZLEBOX: {
    category: BUFF_CATEGORY.PASSIVE_BUFFS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  NOPENOPENOPE: {
    category: BUFF_CATEGORY.OTHER,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  BALANCEOFPOWER: {
    category: BUFF_CATEGORY.OTHER,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  GUARDIANSTRIUMPH: {
    category: BUFF_CATEGORY.OTHER,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  SLAYERLODGE: {
    category: BUFF_CATEGORY.SLAYER_BUFFS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  ESSENCECORRUPTIONSTACKS: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.MAGIC],
  },
  NOFEAR: {
    category: BUFF_CATEGORY.OTHER,
    styles: [STYLE.MELEE],
  },
  PERFECTEQUILIBRIUMSTACKS: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.RANGED],
  },
  PRIMORDIALICESTACKS: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.MELEE],
  },
  TIMESINCELASTATTACK: {
    category: BUFF_CATEGORY.OTHER,
    styles: [STYLE.MELEE],
  },
  REAPERSTACKS: {
    category: BUFF_CATEGORY.STACKS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  SHARDOFGENESIS: {
    category: BUFF_CATEGORY.ENCHANTMENTS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  BLEEDS: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE],
  },
  BLACKSTONEARROWSTACKS: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  LORDOFBONESSTACKS: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  COMBUSTED: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MAGIC],
  },
  FLAMEBOUNDRIVAL: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE],
  },
  HAUNTED: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  VULNED: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  CURSED: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  SMOKECLOUDED: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  OBLITERATED: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  BANDOSBOOK: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  CLAWSOFGUTHIX: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  CLOBBER: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  SUNDER: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  BACKSTAB: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  CROESUSSPORED: {
    category: BUFF_CATEGORY.TARGET,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  BERSERKERSFURY: {
    category: BUFF_CATEGORY.PASSIVE_BUFFS,
    styles: [STYLE.MELEE, STYLE.MAGIC, STYLE.RANGED, STYLE.NECROMANCY],
  },
  LIVINGDEATH: {
    category: BUFF_CATEGORY.ABILITY_BUFFS,
    styles: [STYLE.NECROMANCY],
  },
};

const CATEGORY_ORDER = [
  BUFF_CATEGORY.ABILITY_BUFFS,
  BUFF_CATEGORY.SPECIAL_ATTACKS,
  BUFF_CATEGORY.PASSIVE_BUFFS,
  BUFF_CATEGORY.STACKS,
  BUFF_CATEGORY.SLAYER_BUFFS,
  BUFF_CATEGORY.ENCHANTMENTS,
  BUFF_CATEGORY.TARGET,
  BUFF_CATEGORY.OTHER,
];

export default function BuffPanel({ style, buffs, setBuffs, allBuffs }) {
  const [activeCategory, setActiveCategory] = useState(
    BUFF_CATEGORY.ABILITY_BUFFS,
  );
  const [search, setSearch] = useState("");

  const enabledBuffs = buffs?.enabledBuffs ?? [];
  const buffStacks = buffs?.buffStacks ?? {};

  function toggleBuff(buffId) {
    setBuffs((prev) => {
      const enabled = prev?.enabledBuffs ?? [];
      const stacks = prev?.buffStacks ?? {};
      const isEnabled = enabled.includes(buffId);

      if (isEnabled) {
        const nextEnabled = enabled.filter((id) => id !== buffId);
        const nextStacks = { ...stacks };
        delete nextStacks[buffId];

        return {
          ...prev,
          enabledBuffs: nextEnabled,
          buffStacks: nextStacks,
        };
      }

      const buff = (allBuffs ?? []).find((b) => b.id === buffId);

      return {
        ...prev,
        enabledBuffs: [...enabled, buffId],
        buffStacks: buff?.stackable
          ? { ...stacks, [buffId]: buff.min }
          : stacks,
      };
    });
  }

  function updateBuffStacks(buffId, value) {
    const buff = (allBuffs ?? []).find((b) => b.id === buffId);
    if (!buff) return;

    setBuffs((prev) => {
      const enabled = prev?.enabledBuffs ?? [];
      const stacks = { ...(prev?.buffStacks ?? {}) };

      if (value === "") {
        delete stacks[buffId];

        return {
          ...prev,
          enabledBuffs: enabled.includes(buffId)
            ? enabled
            : [...enabled, buffId],
          buffStacks: stacks,
        };
      }

      const numericValue = Number(value);
      const clamped = Math.max(buff.min, Math.min(buff.max, numericValue));

      return {
        ...prev,
        enabledBuffs: enabled.includes(buffId) ? enabled : [...enabled, buffId],
        buffStacks: {
          ...stacks,
          [buffId]: clamped,
        },
      };
    });
  }

  const mergedBuffs = (allBuffs ?? [])
    .filter((buff) => !PRAYER_MOVED_BUFF_IDS.includes(buff.id))
    .map((buff) => ({
      ...buff,
      ...(BUFF_UI_META[buff.id] ?? {
        category: BUFF_CATEGORY.OTHER,
        styles: [STYLE.MELEE, STYLE.RANGED, STYLE.MAGIC, STYLE.NECROMANCY],
      }),
    }))
    .filter((buff) => (buff.styles ?? []).includes(style))
    .filter((buff) => buff.label.toLowerCase().includes(search.toLowerCase()));

  const groupedBuffs = mergedBuffs.reduce((groups, buff) => {
    const category = buff.category ?? BUFF_CATEGORY.OTHER;
    if (!groups[category]) groups[category] = [];
    groups[category].push(buff);
    return groups;
  }, {});

  const visibleCategories = CATEGORY_ORDER.filter(
    (category) => groupedBuffs[category]?.length,
  );

  const activeBuffs = groupedBuffs[activeCategory] ?? [];

  useEffect(() => {
    if (!visibleCategories.length) return;
    if (!visibleCategories.includes(activeCategory)) {
      setActiveCategory(visibleCategories[0]);
    }
  }, [activeCategory, visibleCategories]);

  return (
    <div className="buff-panel">
      <input
        className="buff-search"
        placeholder="Search buffs..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      <div className="buff-tabs">
        {visibleCategories.map((category) => (
          <button
            key={category}
            type="button"
            className={
              activeCategory === category ? "buff-tab active" : "buff-tab"
            }
            onClick={() => setActiveCategory(category)}
          >
            {category}
          </button>
        ))}
      </div>

      <div className="buff-section">
        <div className="buff-grid">
          {activeBuffs.map((buff) => {
            const isSelected = enabledBuffs.includes(buff.id);

            return (
              <div key={buff.id} className="buff-item">
                <button
                  type="button"
                  onClick={() => toggleBuff(buff.id)}
                  className={isSelected ? "buff selected" : "buff"}
                >
                  <span className="buff-label">{buff.label}</span>

                  {buff.stackable ? (
                    <input
                      type="number"
                      min={buff.min}
                      max={buff.max}
                      value={buffStacks[buff.id] ?? ""}
                      placeholder={buff.min}
                      onClick={(e) => e.stopPropagation()}
                      onFocus={(e) => e.stopPropagation()}
                      onChange={(e) =>
                        updateBuffStacks(buff.id, e.target.value)
                      }
                      onKeyDown={(e) => {
                        e.stopPropagation();
                        if (e.key === "Enter") {
                          e.currentTarget.blur();
                        }
                      }}
                      className="buff-stack-input"
                    />
                  ) : null}
                </button>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}
