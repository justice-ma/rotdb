package com.rotdb.simulation.domain.model.context;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;

import java.util.ArrayList;

public class TickSnapshot {
    private ArrayList<AbilityId> releasedAbility;
    private AbilityId stalledAbility;
    private AdrenalineContext adrenaline;
    private ArrayList<AbilityHitsContext> hits;
    private EquipmentModel equipment;
    private AbilityCooldownContext abilityCooldowns;
    private BuffRotationContext buffs;
    private boolean processed;

    public AbilityId getStalledAbility() {
        return stalledAbility;
    }

    public void setStalledAbility(AbilityId stalledAbility) {
        this.stalledAbility = stalledAbility;
    }

    public AdrenalineContext getAdrenaline() {
        return adrenaline;
    }

    public void setAdrenaline(AdrenalineContext adrenaline) {
        this.adrenaline = adrenaline;
    }

    public EquipmentModel getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentModel equipment) {
        this.equipment = equipment;
    }

    public AbilityCooldownContext getAbilityCooldowns() {
        return abilityCooldowns;
    }

    public void setAbilityCooldowns(AbilityCooldownContext abilityCooldowns) {
        this.abilityCooldowns = abilityCooldowns;
    }

    public BuffRotationContext getBuffs() {
        return buffs;
    }

    public void setBuffs(BuffRotationContext buffs) {
        this.buffs = buffs;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public ArrayList<AbilityId> getReleasedAbility() {
        return releasedAbility;
    }

    public void setReleasedAbility(ArrayList<AbilityId> releasedAbility) {
        this.releasedAbility = releasedAbility;
    }

    public ArrayList<AbilityHitsContext> getHits() {
        return hits;
    }

    public void setHits(ArrayList<AbilityHitsContext> hits) {
        this.hits = hits;
    }
}
