package com.rotdb.calculation.domain.model.player;

import com.rotdb.calculation.domain.model.enums.BuffId;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BuffContext {
    private List<PotionContext> potionBuffs;
    private Set<BuffId> buffSet;
    private Map<BuffId, Integer> buffStacks;

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
