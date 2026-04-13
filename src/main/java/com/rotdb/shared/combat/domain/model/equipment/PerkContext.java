package com.rotdb.calculation.domain.model.equipment;

import com.rotdb.calculation.domain.model.enums.Perks;

import java.util.Map;

public class PerkContext {
    private Map<Perks, Integer> perk;
    private boolean equipmentLevel20;
    private Double genocidalRank;

    public boolean has(Perks selectedPerk) {
        return perk.containsKey(selectedPerk);
    }

    public int rank(Perks selectedPerk) {
        if (perk.get(selectedPerk) == null) {
            return 0;
        }
        return perk.get(selectedPerk);
    }

    public Map<Perks, Integer> getPerk() {
        return perk;
    }

    public void setPerk(Map<Perks, Integer> perk) {
        this.perk = perk;
    }

    public boolean isEquipmentLevel20() {
        return equipmentLevel20;
    }

    public void setEquipmentLevel20(boolean equipmentLevel20) {
        this.equipmentLevel20 = equipmentLevel20;
    }

    public Double getGenocidalRank() {
        return genocidalRank;
    }

    public void setGenocidalRank(Double genocidalRank) {
        this.genocidalRank = genocidalRank;
    }
}
