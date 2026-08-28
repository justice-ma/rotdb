package com.rotdb.calculation.api.controller;

import com.rotdb.analytics.application.HeartbeatService;
import com.rotdb.calculation.api.dto.AbilityCardResultDto;
import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.api.dto.EffectiveStatsRequestDto;
import com.rotdb.calculation.api.mapper.DamageRequestMapper;
import com.rotdb.calculation.api.mapper.EffectiveStatsMapper;
import com.rotdb.calculation.api.request.BatchDamageCalcRequest;
import com.rotdb.calculation.application.service.DamageBatchService;
import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.engine.CalculationMode;
import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.calculation.domain.model.*;
import com.rotdb.shared.ability.AbilityId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/damage")
public class DamageController {
    private final CalculationEngine engine;
    private final DamageRequestMapper mapper;
    private final DamageBatchService batchService;
    private final HeartbeatService heartbeatService;
    private final EffectiveStatsMapper effectiveStatsMapper;

    public DamageController(CalculationEngine engine, DamageRequestMapper mapper, DamageBatchService batchService, HeartbeatService heartbeatService, EffectiveStatsMapper effectiveStatsMapper) {
        this.engine = engine;
        this.mapper = mapper;
        this.batchService = batchService;
        this.heartbeatService = heartbeatService;
        this.effectiveStatsMapper = effectiveStatsMapper;
    }

    @PostMapping("/calculate")
    public DamageResult calculate(@RequestBody DamageCalcRequestDto request) {
        DamageRequest internal = mapper.from(request);
        heartbeatService.recordCalculationActivity(request.clientId(), request.sessionId());
        return engine.calculateAbilityDamage(internal);
    }

    @PostMapping("/derived-stats")
    public DerivedStatsResult calculateDerivedStats(@RequestBody DamageCalcRequestDto request) {
        DamageRequest internal = mapper.from(request);
        return engine.calculateDerivedStats(internal);
    }

    @PostMapping("/calculate/batch")
    public Map<AbilityId, AbilityCardResultDto> calculateBatch(@RequestBody BatchDamageCalcRequest request) {
        heartbeatService.recordCalculationActivity(request.base().clientId(), request.base().sessionId());
        return batchService.calculateBatch(request);
    }

    @PostMapping("/effective-stats")
    public EffectiveStatsResult calculateEffectiveStats(@RequestBody EffectiveStatsRequestDto request) {
        EffectiveStatsRequest internal = effectiveStatsMapper.from(request);
        return engine.calculateEffectiveStats(internal);
    }
}
