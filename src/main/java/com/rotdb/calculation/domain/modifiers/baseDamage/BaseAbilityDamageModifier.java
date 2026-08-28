package com.rotdb.calculation.domain.modifiers.baseDamage;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.baseAbilityDamage.MagicBaseDamageResolver;
import com.rotdb.calculation.domain.resolvers.baseAbilityDamage.MeleeBaseDamageResolver;
import com.rotdb.calculation.domain.resolvers.baseAbilityDamage.NecromancyBaseDamageResolver;
import com.rotdb.calculation.domain.resolvers.baseAbilityDamage.RangedBaseDamageResolver;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.calculation.domain.resolvers.baseAbilityDamage.*;
import com.rotdb.shared.combat.domain.model.enums.*;

public final class BaseAbilityDamageModifier implements Modifier {
    @Override
    public void apply(AggregatedCalculationContext aggregatedCalculationContext) {
        CalculationContext context = aggregatedCalculationContext.getSnapshotContext();

        var equipment = context.getEquipment();
        if (equipment.getMainhand() == null) {
            throw new IllegalStateException("Mainhand is required for ability damage");
        }

        var skills = context.getSkills();
        var perks = context.getPerks();
        var buffs = context.getBuffs();

        CombatStyles style = equipment.getMainhand().getClazz();
        boolean dw = (equipment.getOffhand().getId() != null && equipment.getOffhand().getType() != EquipmentType.SHIELD);
        boolean twoHanded = equipment.getMainhand().getSlot() == Slots.TWOHANDED;

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
        ammoTier = effectiveAmmoTier(style, equipment.getMainhand().getStyle(), equipment.getMainhand().getType(),
                mhTier, ohTier, ammoTier);

        if (equipment.getMainhand().getEffect().contains(Effect.SHARDABLE) && buffs.getBuffSet().contains(BuffId.SHARDOFGENESIS)) {
            mhTier += 5;
            ammoTier += 5;
        }
        if (equipment.getOffhand().getEffect().contains(Effect.SHARDABLE) && buffs.getBuffSet().contains(BuffId.SHARDOFGENESIS)) {
            ohTier += 5;
        }

        int base = resolveBase(style, dw, twoHanded, s, bonus, mhTier, ohTier, er, ammoTier, eq);

        context.getDamage().setBaseDamage(base);
        context.getEquipment().setCombatStyle(style);
    }

    private int resolveBase(CombatStyles style, boolean dw, boolean twoHanded,
                            int s, double bonus, int mhTier, int ohTier,
                            int er, int ammoTier, int eq) {
        return switch (style) {
            case MELEE -> dw
                    ? MeleeBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, eq)
                    : twoHanded ? MeleeBaseDamageResolver.twoHand(s, bonus, mhTier, er, eq)
                    : MeleeBaseDamageResolver.mainhandOnly(s, bonus, mhTier, er, eq);

            case MAGIC -> dw
                    ? MagicBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, ammoTier, eq)
                    : twoHanded ? MagicBaseDamageResolver.twoHand(s, bonus, mhTier, er, ammoTier, eq)
                    : MagicBaseDamageResolver.mainhandOnly(s, bonus, mhTier, er, ammoTier, eq);

            case RANGED -> dw
                    ? RangedBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, ammoTier, eq)
                    : twoHanded ? RangedBaseDamageResolver.twoHand(s, bonus, mhTier, er, ammoTier, eq)
                    : RangedBaseDamageResolver.mainhandOnly(s, bonus, mhTier, er, ammoTier, eq);

            case NECROMANCY ->
                    dw ? NecromancyBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, eq) :
                    NecromancyBaseDamageResolver.mainhandOnly(s, bonus, mhTier, er, eq);

            case ALL -> throw new IllegalStateException("Combat style ALL is not valid for base damage");
        };
    }

    private int effectiveAmmoTier(CombatStyles style, WeaponStyle weaponStyle, EquipmentType weaponType,
                                  int mhTier, int ohTier, int ammoTier) {
        if (style != CombatStyles.RANGED || ammoTier > 0) {
            return ammoTier;
        }

        if (weaponStyle == WeaponStyle.THROWN || weaponType == EquipmentType.CHARGEBOW) {
            return Math.max(mhTier, ohTier);
        }

        return ammoTier;
    }
}
