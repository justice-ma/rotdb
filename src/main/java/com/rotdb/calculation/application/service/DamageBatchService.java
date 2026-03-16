package com.rotdb.calculation.application.service;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.api.dto.AbilityCardResultDto;
import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.api.mapper.DamageRequestMapper;
import com.rotdb.calculation.api.request.BatchDamageCalcRequest;
import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.calculation.domain.model.DamageResult;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DamageBatchService {

    private final CalculationEngine engine;
    private final DamageRequestMapper mapper;

    public DamageBatchService(CalculationEngine engine, DamageRequestMapper mapper) {
        this.engine = engine;
        this.mapper = mapper;
    }

    public Map<AbilityId, AbilityCardResultDto> calculateBatch(BatchDamageCalcRequest req) {
        Map<AbilityId, AbilityCardResultDto> out = new LinkedHashMap<>();

        for (AbilityId id : req.abilityIds()) {
            DamageCalcRequestDto base = req.base();
            DamageCalcRequestDto perAbility = copyWithAbility(base, id);

            DamageRequest internal = mapper.from(perAbility);

            DamageResult result = engine.calculateAbilityDamage(internal);

            double min = result.getMinCoeff();
            double max = result.getMaxCoeff();
            double avg = result.getTotalAvgDamage();

            out.put(id, new AbilityCardResultDto(min, max, avg));
        }

        return out;
    }

    private DamageCalcRequestDto copyWithAbility(DamageCalcRequestDto base, AbilityId id) {
        return new DamageCalcRequestDto(
                base.skills(),
                id,
                base.equipment(),
                base.ammoId(),
                base.buffs(),
                base.targetTitle(),
                base.targetCurrentHp(),
                base.targetMaxHp(),
                base.targetSize(),
                base.potions(),
                base.spell(),
                base.selectedPrayers(),
                base.berserkersFury(),
                base.selectedFamiliar(),
                base.perks()
        );
    }
}