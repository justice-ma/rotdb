package com.rotdb.resolvers.hitChance.accuracy;

import com.rotdb.model.context.CalculationContext;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.model.enums.Effect;

import static com.rotdb.model.enums.CombatStyles.*;

public class PlayerAccuracyResolver {
    public static int resolve(CalculationContext context) {
        CombatStyles style = context.getEquipment().getCombatStyle();
        int prayerBonus = context.getPrayer().getPrayerAccuracyBonus();
        int att =
                style == MELEE ? context.getSkills().getBoostedAttack() + prayerBonus :
                style == RANGED ? context.getSkills().getBoostedRanged() + prayerBonus :
                style == MAGIC ? context.getSkills().getBoostedMagic() + prayerBonus :
                context.getSkills().getBoostedNecromancy() + prayerBonus;
        int weaponTier = context.getEquipment().getMainhand().getAccuracyTier() == 0 ?
                context.getEquipment().getMainhand().getTier() == 0 ? 0 :
                        context.getEquipment().getMainhand().getTier() :
                context.getEquipment().getMainhand().getAccuracyTier();
        if (context.getEquipment().getMainhand().getEffect().contains(Effect.SHARDABLE) &&
                context.getBuffs().getBuffSet().contains(BuffId.SHARDOFGENESIS)) {
            weaponTier += 5;
        }
        int skillBonus = (int) (1.0 / 1250 * Math.pow(att, 3) + 4 * att + 40);
        int weaponBonus = (int) (2.5 * (1.0 / 1250 * Math.pow(weaponTier, 3) + 4 * weaponTier + 40));
        double accuracyModifier =
                1.0 +
                BuffAccuracyResolver.resolve(context) +
                DefenderAccuracyResolver.resolve(context) +
                FamiliarAccuracyResolver.resolve(context) +
                ScrimshawAccuracyResolver.resolve(context) +
                SlayerTaskAccuracyResolver.resolve(context) +
                UndeadAccuracyResolver.resolve(context) +
                VoidAccuracyResolver.resolve(context);

        return (int) ((skillBonus + weaponBonus) * accuracyModifier);
    }
}
