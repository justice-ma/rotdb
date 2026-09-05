package com.rotdb.calculation;

import com.rotdb.shared.combat.domain.model.enums.Potions;
import com.rotdb.shared.combat.domain.model.enums.Stats;
import com.rotdb.shared.combat.domain.model.player.PotionContext;

import java.util.List;

public class PlayerBuffFactory {
    public static List<PotionContext> addElderOverload(List<PotionContext> potionContexts) {
        potionContexts.add(new PotionContext(Potions.ELDER, Stats.ALL));
        return potionContexts;
    }
}
