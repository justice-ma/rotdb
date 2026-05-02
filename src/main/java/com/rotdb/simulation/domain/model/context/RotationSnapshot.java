package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RotationSnapshot {
    Map<Integer, TickSnapshot> rotationSnapshot = new HashMap<>();
    private AbilityContext abilityContext;
    private AdrenalineContext adrenalineContext = new AdrenalineContext();
    private AbilityCooldownContext abilityCooldownContext = new AbilityCooldownContext();

    public boolean isProcessed(Integer tick) {
        return rotationSnapshot.get(tick).isProcessed();
    }

    public void process(Integer tick) {
        rotationSnapshot.get(tick).setProcessed(true);
    }

    public ArrayList<AbilityId> getReleasedAbility(Integer tick) {
        return rotationSnapshot.get(tick).getReleasedAbility();
    }

    public void setReleasedAbility(Integer tick, AbilityId ability) {
        rotationSnapshot.get(tick).getReleasedAbility().add(ability);
    }

    public AbilityId getStalledAbility(Integer tick) {
        return rotationSnapshot.get(tick).getStalledAbility();
    }

    public void setStalledAbility(Integer tick, AbilityId ability) {
        rotationSnapshot.get(tick).setStalledAbility(ability);
    }

    public double getAdrenaline(Integer tick) {
        return rotationSnapshot.get(tick).getAdrenaline().getAdrenaline();
    }

    public void setAdrenaline(Integer tick, int adrenaline) {
        rotationSnapshot.get(tick).getAdrenaline().setAdrenaline(adrenaline);
    }

    public double addAdrenaline(Integer tick, int adrenaline) {
        double adren = rotationSnapshot.get(tick).getAdrenaline().getAdrenaline();
        rotationSnapshot.get(tick).getAdrenaline().setAdrenaline(adren + adrenaline);
        return adren + adrenaline;
    }

    public void setAdrenalineMessage(Integer tick, String message) {
        rotationSnapshot.get(tick).getAdrenaline().setMessage(message);
    }

    public EquipmentModel getEquipment(Integer tick) {
        return rotationSnapshot.get(tick).getEquipment();
    }

    public AbilityContext getAbilityContext() {
        return abilityContext;
    }

    public void setAbilityContext(AbilityContext abilityContext) {
        this.abilityContext = abilityContext;
    }

    public AdrenalineContext getAdrenalineContext() {
        return adrenalineContext;
    }

    public void setAdrenalineContext(AdrenalineContext adrenalineContext) {
        this.adrenalineContext = adrenalineContext;
    }

    public AdrenalineContext getAdrenaline() {
        return adrenalineContext;
    }

    public AbilityCooldownContext getAbilityCooldownContext() {
        if (abilityCooldownContext.getCooldownMap() == null) {
            abilityCooldownContext.setCooldownMap(new HashMap<>());
        }
        return abilityCooldownContext;
    }

    public void setAbilityCooldownContext(AbilityCooldownContext abilityCooldownContext) {
        this.abilityCooldownContext = abilityCooldownContext;
    }
}
