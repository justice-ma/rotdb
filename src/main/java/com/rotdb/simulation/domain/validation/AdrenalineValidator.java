package com.rotdb.simulation.domain.validation;

import com.rotdb.calculation.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.simulation.domain.model.context.RotationContext;

public class AdrenalineValidator implements Validator {
    public boolean validate(RotationContext rc, CalculationContext cc) {
        AbilityContext ability = AbilityProvider.get(rc.getAbilityContext().getAbilityId(), cc);
        rc.getAdrenalineContext().setAdrenaline(rc.getAdrenalineContext().getAdrenaline() + ability.getAdrenaline());
        if (rc.getAdrenalineContext().getAdrenaline() + ability.getAdrenaline() < 0) {
            throw new RuntimeException("Warning: May not have adrenaline required for " + ability.getName());
        }
        if (rc.getAdrenalineContext().getAdrenaline() > 100) {
            rc.getAdrenalineContext().setAdrenaline(100);
            throw new RuntimeException("Warning: Adrenaline capped after " + ability.getName());
        }
        return true;
    }
}
