package com.rotdb.simulation.domain;

import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.application.mapper.BuffContextMapper;
import com.rotdb.calculation.application.mapper.PerkContextMapper;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.enums.Perks;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;
import com.rotdb.simulation.domain.model.context.RotationSnapshot;
import com.rotdb.simulation.domain.validation.AdrenalineValidator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ValidatorSandbox {
    public static void main(String[] args) {
        RotationSnapshot rc = new RotationSnapshot();
        EquipmentModel eq = new EquipmentModel();
        AdrenalineContext adc = new AdrenalineContext();
        BuffContextMapper buffMapper = new BuffContextMapper();

        Set<BuffId> buffIdSet = new HashSet<>();
        buffIdSet.add(BuffId.FURYOFTHESMALL);
        buffIdSet.add(BuffId.CONSERVATIONOFENERGY);
        buffIdSet.add(BuffId.METEORSTRIKE);
        buffIdSet.add(BuffId.RINGOFVIGOUR);
        buffIdSet.add(BuffId.IMBUESHADOWS);

        DamageCalcRequestDto.BuffIds buffs = new DamageCalcRequestDto.BuffIds(buffIdSet, new HashMap<>());
        BuffContext buff = buffMapper.from(buffs);

        PerkContextMapper perkMapper = new PerkContextMapper();
        Map<Perks, Integer> perksMap = new HashMap<>();

        DamageCalcRequestDto.PerkSelection perkSelection = new DamageCalcRequestDto.PerkSelection(perksMap, true, 0.0);
        PerkContext perks = perkMapper.from(perkSelection);

        AbilityId ABILITY = AbilityId.GREATERRICOCHET;
        AbilityContext ac = AbilityProvider.get(ABILITY, eq);

        adc.setAdrenaline(20);

        rc.setAbilityContext(ac);
        rc.setAdrenalineContext(adc);

        AdrenalineValidator validator = new AdrenalineValidator();
        validator.validate(rc, perks, eq, buff);
        System.out.println("After Adrenaline Processing: " + adc.getAdrenaline() + " Upper: " + adc.getMaximumBound());

        System.out.println(rc.getAdrenalineContext().getMessage());
    }
}
