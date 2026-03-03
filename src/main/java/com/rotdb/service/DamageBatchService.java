package com.rotdb.service;

import com.rotdb.ability.AbilityId;
import com.rotdb.api.dto.AbilityCardResult;
import com.rotdb.api.dto.DamageCalcRequest;
import com.rotdb.api.mapper.DamageRequestMapper;
import com.rotdb.api.request.BatchDamageCalcRequest;
import com.rotdb.domain.CalculationEngine;
import com.rotdb.domain.DamageRequest;
import com.rotdb.domain.DamageResult;
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

    public Map<AbilityId, AbilityCardResult> calculateBatch(BatchDamageCalcRequest req) {
        Map<AbilityId, AbilityCardResult> out = new LinkedHashMap<>();

        for (AbilityId id : req.abilityIds()) {
            DamageCalcRequest base = req.base();
            DamageCalcRequest perAbility = copyWithAbility(base, id);

            DamageRequest internal = mapper.from(perAbility);

            DamageResult result = engine.calculateAbilityDamage(internal);

            double min = result.getMinCoeff();
            double max = result.getMaxCoeff();
            double avg = result.getTotalAvgDamage();

            out.put(id, new AbilityCardResult(min, max, avg));
        }

        return out;
    }

    private DamageCalcRequest copyWithAbility(DamageCalcRequest base, AbilityId id) {
        return new DamageCalcRequest(
                base.skills(),
                id,
                base.equipment(),
                base.ammoId(),
                base.buffs(),
                base.targetTitle(),
                base.potions(),
                base.spell(),
                base.selectedPrayers(),
                base.berserkersFury(),
                base.familiar(),
                base.perks()
        );
    }
}