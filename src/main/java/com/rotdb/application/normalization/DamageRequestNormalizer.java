package com.rotdb.application.normalization;

import com.rotdb.domain.model.DamageRequest;
import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.equipment.PerkContext;
import com.rotdb.domain.model.player.BuffContext;

import java.util.Objects;

public final class DamageRequestNormalizer {
    public DamageRequest normalize(DamageRequest r) {
        Objects.requireNonNull(r, "request");

        if (r.getBuffs() == null) r.setBuffs(new BuffContext());
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
