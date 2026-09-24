package com.rotdb.calculation.domain.resolvers.baseAbilityDamage;

import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentSlot;
import com.rotdb.shared.combat.domain.model.player.BuffContext;

public class ShardOfGenesisResolver {
    private static final int shardOfGenesisTierDelta = 5;

    public static ShardOfGenesisWeaponTiers resolveShardOfGenesisWeaponTiers(EquipmentModel equipment,
                                                                             BuffContext buffs, int mhTier, int ohTier) {
        if (isShardable(equipment.getMainhand()) && isSharded(buffs)) mhTier += shardOfGenesisTierDelta;
        if (isShardable(equipment.getOffhand()) && isSharded(buffs)) ohTier += shardOfGenesisTierDelta;
        return new ShardOfGenesisWeaponTiers(mhTier, ohTier);
    }

    public static double resolveShardOfGenesisAmmoTier(EquipmentModel equipment, BuffContext buffs, double ammoTier) {
        if (isShardable(equipment.getMainhand()) && isSharded(buffs)) ammoTier += shardOfGenesisTierDelta;
        return ammoTier;
    }

    private static boolean isSharded(BuffContext buffs) {
        return buffs.has(BuffId.SHARDOFGENESIS);
    }

    private static boolean isShardable(EquipmentSlot equipmentSlot) {
        return equipmentSlot.getEffect().contains(Effect.SHARDABLE);
    }
}
