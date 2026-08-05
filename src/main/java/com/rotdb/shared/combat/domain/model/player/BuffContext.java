package com.rotdb.shared.combat.domain.model.player;

import com.rotdb.shared.combat.domain.model.enums.BuffId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BuffContext {
    private List<PotionContext> potionBuffs;
    private Set<BuffId> buffSet;
    private Map<BuffId, Integer> buffStacks;

    public int getBlessingsPerAlignment() {
        List<BuffId> guthixAlignments = new ArrayList<>(List.of(BuffId.TRUE_EQUILIBRIUM, BuffId.TEARING_THORNS, BuffId.ENVENOMED, BuffId.POWER_ARCHIVE));
        List<BuffId> zamorakAlignments = new ArrayList<>(List.of(BuffId.HAVOC_BORN, BuffId.UNHOLY_CRITUAL, BuffId.PERFIDIOUS, BuffId.CHAOTIC_INSIGHT));
        List<BuffId> saradominAlignments = new ArrayList<>(List.of(BuffId.HIGHER_POWER, BuffId.LORD_OF_LIGHT, BuffId.TEMPERED_HEART, BuffId.GENESIS_ESSENCE));

        int count = 0;
        for (BuffId guthixBlessing : guthixAlignments) {
            if (has(guthixBlessing)) {
                count++;
                break;
            }
        }
        for (BuffId zamorakBlessing : zamorakAlignments) {
            if (has(zamorakBlessing)) {
                count++;
                break;
            }
        }
        for (BuffId saradominBlessing : saradominAlignments) {
            if (has(saradominBlessing)) {
                count++;
                break;
            }
        }

        return count;
    }

    public boolean has(BuffId buff) {
        return buffSet.contains(buff) || buffStacks.containsKey(buff);
    }

    public int stacks(BuffId buff) {
        return buffStacks.get(buff) == null ? 0 : buffStacks.get(buff);
    }

    public Set<BuffId> getBuffSet() {
        return buffSet;
    }

    public void setBuffSet(Set<BuffId> buffSet) {
        this.buffSet = buffSet;
    }

    public Map<BuffId, Integer> getBuffStacks() {
        return buffStacks;
    }

    public void setBuffStacks(Map<BuffId, Integer> buffStacks) {
        this.buffStacks = buffStacks;
    }

    public List<PotionContext> getPotionBuffs() {
        return potionBuffs;
    }

    public void setPotionBuffs(List<PotionContext> potionBuffs) {
        this.potionBuffs = potionBuffs;
    }
}
