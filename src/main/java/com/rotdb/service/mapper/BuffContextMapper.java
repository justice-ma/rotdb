package com.rotdb.service.mapper;

import com.rotdb.api.dto.DamageCalcRequest;
import com.rotdb.model.enums.BuffId;
import com.rotdb.model.player.BuffContext;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BuffContextMapper {
    public BuffContext from(DamageCalcRequest.BuffIds buffs) {
        BuffContext buff = new BuffContext();
        buff.setBuffSet(buffs.enabledBuffs());
        buff.setBuffStacks(buffs.buffStacks());

        return BuffRequestNormalizer.normalize(buff);
    }
}
