package com.rotdb.calculation.domain.model.player;

import com.rotdb.calculation.domain.model.enums.Spells;

public class SpellContext {
    private Spells spell;

    public Spells getSpell() {
        return spell;
    }

    public void setSpell(Spells spell) {
        this.spell = spell;
    }
}
