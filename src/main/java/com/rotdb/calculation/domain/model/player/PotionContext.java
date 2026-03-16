package com.rotdb.calculation.domain.model.player;

import com.rotdb.calculation.domain.model.enums.Potions;
import com.rotdb.calculation.domain.model.enums.Stats;

public class PotionContext {
    private Potions potion;
    private Stats stat;

    public PotionContext(Potions potion, Stats stat) {
        this.potion = potion;
        this.stat = stat;
    }

    public Potions getPotion() {
        return potion;
    }

    public void setPotion(Potions potion) {
        this.potion = potion;
    }

    public Stats getStat() {
        return stat;
    }

    public void setStat(Stats stat) {
        this.stat = stat;
    }
}
