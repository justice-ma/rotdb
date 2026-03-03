package com.rotdb.domain;

import com.rotdb.model.context.TargetContext;
import com.rotdb.model.enums.Prayer;
import com.rotdb.model.equipment.FamiliarContext;
import com.rotdb.model.equipment.PerkContext;
import com.rotdb.model.player.BuffContext;
import com.rotdb.model.player.RelicsContext;

import java.util.EnumSet;
import java.util.Objects;

public final class DamageRequestNormalizer {
    public DamageRequest normalize(DamageRequest r) {
        Objects.requireNonNull(r, "request");

        if (r.getBuffs() == null) r.setBuffs(new BuffContext());
        if (r.getRelics() == null) r.setRelics(new RelicsContext());
        if (r.getPerks() == null) r.setPerks(new PerkContext());
        if (r.getTarget() == null) {
            TargetContext target = new TargetContext();
            target.setArmour(1);
            target.setDefence(1);
            target.setAffinity(55);
            r.setTarget(target);
        }

        return r;
    }
}
