package com.rotdb.simulation.domain.validation;

import com.rotdb.calculation.domain.model.context.AbilityContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.simulation.domain.model.context.RotationContext;

import java.util.Map;

public class AbilityCooldownValidator implements Validator {
    public boolean validate(RotationContext rc, CalculationContext cc) {
        AbilityContext ability = AbilityProvider.get(rc.getAbilityContext().getAbilityId(), cc);
        int cooldown = rc.getAbilityCooldownContext().getCooldownMap().getOrDefault(ability.getId(), 0);

        if (cooldown == 0) {
            rc.getAbilityCooldownContext().getCooldownMap().put(ability.getId(), ability.getCooldownTicks());
        }

        for (Map.Entry<AbilityId, Integer> cds : rc.getAbilityCooldownContext().getCooldownMap().entrySet()) {
            cds.setValue(cds.getValue() - 1);
        }

        if (cooldown > 0) {
            rc.getAbilityCooldownContext().getCooldownMap().put(ability.getId(), ability.getCooldownTicks());
            throw new RuntimeException("Warning: " + ability.getName() + " may still be on cooldown");
        }

        return true;
    }
}
