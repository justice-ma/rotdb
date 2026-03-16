package com.rotdb.calculation.application.mapper;

import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.domain.model.enums.BuffId;
import com.rotdb.calculation.domain.model.player.BuffContext;
import org.springframework.stereotype.Component;

@Component
public class BuffContextMapper {
    public BuffContext from(DamageCalcRequestDto.BuffIds buffs) {
        BuffContext buff = new BuffContext();
        buff.setBuffSet(buffs.enabledBuffs());
        buff.setBuffStacks(buffs.buffStacks());

        if (!buff.getBuffStacks().containsKey(BuffId.BOOKUPTIME)) {
            buff.getBuffSet().add(BuffId.BOOKUPTIME);
            buff.getBuffStacks().put(BuffId.BOOKUPTIME, 66);
        }

        return BuffRequestNormalizer.normalize(buff);
    }
}
