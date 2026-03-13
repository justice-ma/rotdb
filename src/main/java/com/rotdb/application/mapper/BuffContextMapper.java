package com.rotdb.application.mapper;

import com.rotdb.api.dto.DamageCalcRequestDto;
import com.rotdb.domain.model.player.BuffContext;
import org.springframework.stereotype.Component;

@Component
public class BuffContextMapper {
    public BuffContext from(DamageCalcRequestDto.BuffIds buffs) {
        BuffContext buff = new BuffContext();
        buff.setBuffSet(buffs.enabledBuffs());
        buff.setBuffStacks(buffs.buffStacks());

        return BuffRequestNormalizer.normalize(buff);
    }
}
