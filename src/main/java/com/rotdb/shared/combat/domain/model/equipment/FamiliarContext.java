package com.rotdb.shared.combat.domain.model.equipment;

import com.rotdb.shared.combat.domain.model.enums.Familiars;

public class FamiliarContext {
    private Familiars name;

    public Familiars getName() {
        return name;
    }

    public void setName(Familiars name) {
        this.name = name;
    }
}
