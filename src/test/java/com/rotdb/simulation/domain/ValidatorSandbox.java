package com.rotdb.simulation.domain;

import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.application.mapper.BuffContextMapper;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;
import com.rotdb.simulation.domain.model.context.RotationContext;
import com.rotdb.simulation.domain.validation.AdrenalineValidator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ValidatorSandbox {
    public static void main(String[] args) {
        RotationContext rc = new RotationContext();
        EquipmentModel eq = new EquipmentModel();
        AdrenalineContext adc = new AdrenalineContext();
        BuffContextMapper buffMapper = new BuffContextMapper();
        Set<BuffId> buffIdSet = new HashSet<>();
        buffIdSet.add(BuffId.HEIGHTENEDSENSES);
        buffIdSet.add(BuffId.VESTMENTSBLEED);
        DamageCalcRequestDto.BuffIds buffs = new DamageCalcRequestDto.BuffIds(buffIdSet, new HashMap<>());
        BuffContext buff = buffMapper.from(buffs);

        AbilityId ABILITY = AbilityId.OVERPOWER;
        AbilityContext ac = AbilityProvider.get(ABILITY, eq);

        adc.setAdrenaline(71);

        rc.setAbilityContext(ac);
        rc.setAdrenalineContext(adc);

        AdrenalineValidator validator = new AdrenalineValidator();
        validator.validate(rc, eq, buff);
        System.out.println("After Adrenaline Processing: " + adc.getAdrenaline() + " Upper: " + adc.getMaximumBound());

        System.out.println(rc.getAdrenalineContext().getMessage());
    }
}
