package com.rotdb.calculation.application.service;

import com.rotdb.calculation.domain.model.HitResult;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.calculation.api.dto.AbilityCardResultDto;
import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.api.mapper.DamageRequestMapper;
import com.rotdb.calculation.api.request.BatchDamageCalcRequest;
import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.shared.combat.domain.model.enums.EquipmentIdSource;
import com.rotdb.shared.combat.domain.model.enums.HitType;
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

    public Map<AbilityId, AbilityCardResultDto> calculateBatch(BatchDamageCalcRequest req, EquipmentIdSource equipmentIdSource) {
        Map<AbilityId, AbilityCardResultDto> out = new LinkedHashMap<>();

        for (AbilityId id : req.abilityIds()) {
            DamageCalcRequestDto base = req.base();
            DamageCalcRequestDto perAbility = copyWithAbility(base, id);
            DamageRequest internal = mapper.from(perAbility, equipmentIdSource);

            DamageResult result = null;
            if (base.baseAbilityDamage() == null || base.baseAbilityDamage() == 0) {
                result = engine.calculateAbilityDamageFromStart(internal);
            } else {
                result = engine.calculateAbilityDamageFromBaseAbilityDamage(internal);
            }

            double minCoeff = result.getMinCoeff();
            double maxCoeff = result.getMaxCoeff();
            double min = result.getTotalMinDamage();
            double max = result.getTotalMaxDamage();
            double avg = result.getTotalAvgDamage();
            double critMin = result.getTotalMinCrit();
            double critMax = result.getTotalMaxCrit();
            double critAvg = result.getTotalAvgCrit();
            double nonCritMin = result.getTotalMinNonCrit();
            double nonCritMax = result.getTotalMaxNonCrit();
            double nonCritAvg = result.getTotalAvgNonCrit();
            int hits = 0;

            for (HitResult hit : result.getHit()) {
                if (hit.getHitType() == HitType.BASE || hit.getHitType() == HitType.POISON) {
                    hits++;
                }
            }

            out.put(id, new AbilityCardResultDto(minCoeff, maxCoeff, min, max, avg, critMin, critMax, critAvg, nonCritMin, nonCritMax, nonCritAvg, hits));
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
                base.perks(),
                base.hitCapMode(),
                base.clientId(),
                base.sessionId(),
                base.baseAbilityDamage()
        );
    }
}
