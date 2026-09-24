package com.rotdb.calculation.domain.modifiers.baseDamage;

import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.modifiers.Modifier;
import com.rotdb.calculation.domain.resolvers.baseAbilityDamage.*;
import com.rotdb.shared.combat.domain.model.enums.*;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

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
        double ammoTier = style == CombatStyles.MAGIC ? context.getSpellContext().getSpell().getDamageTier() :
                context.getEquipment().getAmmo().getDamageTier();

        /*
        NOTE: Used for 2h-ranged and all magic ability damage calculations.
        Will result in 1/3 of the 2h-ranged weapon's damage tier if ammunition is missing or tier 0.
        No dual-wield crossbows/thrown weapons currently exist and are shardable, so ammoTier is used in place for
        those.
        If dual-wield crossbows are equipped without ammo, the result is still calculated with ammo tier of 0 as intended.
        Will increment spell tier by 5 if mainhand weapon is sharded.
         */
        double resolvedAmmoTier = effectiveAmmoTier(style, equipment.getMainhand().getStyle(),
                equipment.getMainhand().getType(), mhTier, ohTier, ammoTier, equipment, buffs);

        ShardOfGenesisWeaponTiers shardResolver = ShardOfGenesisResolver.resolveShardOfGenesisWeaponTiers(equipment, buffs, mhTier, ohTier);
        mhTier = shardResolver.mhTier();
        ohTier = shardResolver.ohTier();

        int base = resolveBase(style, dw, twoHanded, s, bonus, mhTier, ohTier, er, ammoTier, resolvedAmmoTier, eq);

        context.getDamage().setBaseDamage(base);
        context.getEquipment().setCombatStyle(style);
    }

    private int resolveBase(CombatStyles style, boolean dw, boolean twoHanded,
                            int s, double bonus, int mhTier, int ohTier,
                            int er, double ammoTier, double resolvedAmmoTier, int eq) {
        return switch (style) {
            case MELEE -> dw
                    ? MeleeBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, eq)
                    : twoHanded ? MeleeBaseDamageResolver.twoHand(s, bonus, mhTier, er, eq)
                    : MeleeBaseDamageResolver.mainhandOnly(s, bonus, mhTier, er, eq);

            case MAGIC -> dw
                    ? MagicBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, resolvedAmmoTier, eq)
                    : twoHanded ? MagicBaseDamageResolver.twoHand(s, bonus, mhTier, er, resolvedAmmoTier, eq)
                    : MagicBaseDamageResolver.mainhandOnly(s, bonus, mhTier, er, resolvedAmmoTier, eq);

            case RANGED -> dw
                    ? RangedBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, ammoTier, eq)
                    : twoHanded ? RangedBaseDamageResolver.twoHand(s, bonus, mhTier, er, resolvedAmmoTier, eq)
                    : RangedBaseDamageResolver.mainhandOnly(s, bonus, mhTier, er, ammoTier, eq);

            case NECROMANCY ->
                    dw ? NecromancyBaseDamageResolver.dualWield(s, bonus, mhTier, ohTier, er, eq) :
                    NecromancyBaseDamageResolver.mainhandOnly(s, bonus, mhTier, er, eq);

            case ALL -> throw new IllegalStateException("Combat style ALL is not valid for base damage");
        };
    }

    private double effectiveAmmoTier(CombatStyles style, WeaponStyle weaponStyle, EquipmentType weaponType,
                                  int mhTier, int ohTier, double ammoTier, EquipmentModel equipment,
                                     BuffContext buffs) {
        if (style != CombatStyles.RANGED || ammoTier > 0) {
            return ShardOfGenesisResolver.resolveShardOfGenesisAmmoTier(equipment, buffs, ammoTier);
        }

        if (weaponStyle == WeaponStyle.THROWN || weaponType == EquipmentType.CHARGEBOW) {
            return Math.max(mhTier, ohTier);
        }

        // Adjusting ammo tier to 1/3 of the mainhand's tier. This is strictly for payloads with no ammunition and a
        // 2h-ranged weapon - the only remaining payload that will consume this value.
        double adjustedAmmoTier = mhTier / 3.0;

        adjustedAmmoTier = ShardOfGenesisResolver.resolveShardOfGenesisAmmoTier(equipment, buffs, adjustedAmmoTier);

        return adjustedAmmoTier;
    }
}
