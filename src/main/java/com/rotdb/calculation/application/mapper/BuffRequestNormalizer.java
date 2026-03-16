package com.rotdb.calculation.application.mapper;

import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.player.BuffContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BuffRequestNormalizer {
    public static BuffContext normalize(BuffContext buffs) {
        BuffContext result = new BuffContext();
        Map<BuffId, Integer> cleanedStacks = new HashMap<>();
        Set<BuffId> cleanedBuffs = new HashSet<>();

        if (buffs == null || buffs.getBuffSet() == null && buffs.getBuffStacks() == null) {
            result.setBuffSet(cleanedBuffs);
            result.setBuffStacks(cleanedStacks);
            return result;
        }

        if (buffs.getBuffStacks() != null) {
            for (Map.Entry<BuffId, Integer> entry : buffs.getBuffStacks().entrySet()) {
                if (entry.getKey() == null) continue;
                var v = entry.getValue() == null ? 0 : entry.getValue();
                if (!entry.getKey().isStackable() && v > 0) {
                    cleanedBuffs.add(entry.getKey());
                } else if (entry.getKey().isStackable()) {
                    if(entry.getKey().getMaximumStacks() < v) {
                        cleanedStacks.put(entry.getKey(), entry.getKey().getMaximumStacks());
                    } else if (entry.getKey().getMinimumStacks() > v) {
                        cleanedStacks.put(entry.getKey(), entry.getKey().getMinimumStacks());
                    } else {
                        cleanedStacks.put(entry.getKey(), v);
                    }
                }
            }
        }

        if (buffs.getBuffSet() != null) {
            for (BuffId buff : buffs.getBuffSet()) {
                if (buff == null) continue;
                if (buff.isStackable() && !cleanedStacks.containsKey(buff)) {
                    cleanedStacks.put(buff, buff.getMinimumStacks());
                } else {
                    cleanedBuffs.add(buff);
                }
            }
        }

        result.setBuffStacks(cleanedStacks);
        result.setBuffSet(cleanedBuffs);
        return result;
    }
}