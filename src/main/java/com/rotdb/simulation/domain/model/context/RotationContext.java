package com.rotdb.simulation.domain.model.context;

public class RotationContext {
    private AbilityCooldownContext abilityCooldownContext;
    private AdrenalineContext adrenalineContext;
    private BuffCooldownContext buffCooldownContext;
    private AbilityContext abilityContext;

    public AbilityCooldownContext getAbilityCooldownContext() {
        return abilityCooldownContext;
    }

    public void setAbilityCooldownContext(AbilityCooldownContext abilityCooldownContext) {
        this.abilityCooldownContext = abilityCooldownContext;
    }

    public AdrenalineContext getAdrenalineContext() {
        return adrenalineContext;
    }

    public void setAdrenalineContext(AdrenalineContext adrenalineContext) {
        this.adrenalineContext = adrenalineContext;
    }

    public BuffCooldownContext getBuffCooldownContext() {
        return buffCooldownContext;
    }

    public void setBuffCooldownContext(BuffCooldownContext buffCooldownContext) {
        this.buffCooldownContext = buffCooldownContext;
    }

    public AbilityContext getAbilityContext() {
        return abilityContext;
    }

    public void setAbilityContext(AbilityContext abilityContext) {
        this.abilityContext = abilityContext;
    }
}
