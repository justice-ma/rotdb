package com.rotdb.calculation.domain.model;

import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.ForcedCritResolver;
import com.rotdb.shared.combat.domain.model.enums.ForceCritSource;

public class ForcedCritResult {
    private boolean forcedCrit;
    private ForceCritSource source;

    public ForcedCritResult(boolean forcedCrit, ForceCritSource source) {
        this.forcedCrit = forcedCrit;
        this.source = source;
    }

    public boolean isForcedCrit() {
        return forcedCrit;
    }

    public void setForcedCrit(boolean forcedCrit) {
        this.forcedCrit = forcedCrit;
    }

    public ForceCritSource getSource() {
        return source;
    }

    public void setSource(ForceCritSource source) {
        this.source = source;
    }
}
