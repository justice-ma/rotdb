package com.rotdb.domain.modifiers.baseDamage;

import com.rotdb.domain.model.context.CalculationContext;
import com.rotdb.domain.model.enums.BuffId;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.Effect;
import com.rotdb.domain.model.enums.Perks;
import com.rotdb.domain.modifiers.Modifier;
import com.rotdb.domain.resolvers.baseAbilityDamage.MagicBaseDamageResolver;
import com.rotdb.domain.resolvers.baseAbilityDamage.MeleeBaseDamageResolver;
import com.rotdb.domain.resolvers.baseAbilityDamage.NecromancyBaseDamageResolver;
import com.rotdb.domain.resolvers.baseAbilityDamage.RangedBaseDamageResolver;

public final class BaseAbilityDamageModifier implements Modifier{
    @Override
    public void apply(CalculationContext context) {
        var equipment = context.getEquipment();
        if (equipment.getMainhand() == null) {
            throw new IllegalStateException("Mainhand is required for ability damage");
        }

        var skills = context.getSkills();
        var perks  = context.getPerks();
        var buffs  = context.getBuffs();

        CombatStyles style = equipment.getMainhand().getClazz();
        boolean dw = (equipment.getOffhand().getId() != null);

        int s = switch (style) {
            case MELEE -> skills.getBoostedStrength();
            case RANGED -> skills.getBoostedRanged();
            case MAGIC -> skills.getBoostedMagic();
            case NECROMANCY -> skills.getBoostedNecromancy();
            case ALL -> throw new IllegalStateException("Combat style ALL is not valid for base damage");
        };

        double bonus = switch (style) {
            case MELEE -> equipment.getTotalStrength();
            case RANGED -> equipment.getTotalRanged();
            case MAGIC -> equipment.getTotalMagic();
            case NECROMANCY -> equipment.getTotalNecromancy();
            case ALL -> throw new IllegalStateException("Combat style ALL is not valid for base damage");
        };

        if (buffs.has(BuffId.REAPERSCREW)) bonus += 12;

        int er = perks.has(Perks.ERUPTIVE) ? perks.rank(Perks.ERUPTIVE) : 0;
        int eq = perks.has(Perks.EQUILIBRIUM) ? perks.rank(Perks.EQUILIBRIUM) : 0;
        int mhTier = equipment.getMainhand().getDamageTier();
        int ohTier = dw ? equipment.getOffhand().getDamageTier() : 0;
        int ammoTier = style == CombatStyles.MAGIC ? context.getSpellContext().getSpell().getDamageTier() :
                context.getEquipment().getAmmo().getDamageTier();

        if (equipment.getMainhand().getEffect().contains(Effect.SHARDABLE) && buffs.getBuffSet().contains(BuffId.SHARDOFGENESIS)) {
            mhTier += 5;
            ammoTier += 5;
        }
        if (equipment.getOffhand().getEffect().contains(Effect.SHARDABLE) && buffs.getBuffSet().contains(BuffId.SHARDOFGENESIS)) {
            ohTier += 5;
        }

        int base = resolveBase(style, dw, s, bonus, mhTier, ohTier, er, ammoTier, eq);

        context.getDamage().setBaseDamage(base);
        context.getEquipment().setCombatStyle(style);
    }

    private int resolveBase(CombatStyles style, boolean dw,
                            int s, double bonus, int mhTier, int ohTier,
                            int er, int ammoTier, int eq) {
        return switch (style) {
            case MELEE -> dw
                    ? MeleeBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, eq)
                    : MeleeBaseDamageResolver.twoHand(s, bonus, mhTier, er, eq);
            case MAGIC -> dw
                    ? MagicBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, ammoTier, eq)
                    : MagicBaseDamageResolver.twoHand(s, bonus, mhTier, er, ammoTier, eq);
            case RANGED -> dw
                    ? RangedBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, ammoTier, eq)
                    : RangedBaseDamageResolver.twoHand(s, bonus, mhTier, er, ammoTier, eq);
            case NECROMANCY -> {
                if (!dw) throw new IllegalStateException("Necromancy requires offhand in current rules");
                yield NecromancyBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, eq);
            }
            case ALL -> throw new IllegalStateException("Combat style ALL is not valid for base damage");
        };
    }
}
