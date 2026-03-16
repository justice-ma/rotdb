package com.rotdb.calculation.api.controller;

import com.rotdb.calculation.ability.AbilityId;
import com.rotdb.calculation.api.dto.AbilityCardResultDto;
import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.api.mapper.DamageRequestMapper;
import com.rotdb.calculation.api.request.BatchDamageCalcRequest;
import com.rotdb.calculation.domain.engine.CalculationEngine;
import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.calculation.application.service.DamageBatchService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/damage")
public class DamageController {
    private final CalculationEngine engine;
    private final DamageRequestMapper mapper;
    private final DamageBatchService batchService;

    public DamageController(CalculationEngine engine, DamageRequestMapper mapper, DamageBatchService batchService) {
        this.engine = engine;
        this.mapper = mapper;
        this.batchService = batchService;
    }

    @PostMapping("/calculate")
    public DamageResult calculate(@RequestBody DamageCalcRequestDto request) {
        DamageRequest internal = mapper.from(request);
        return engine.calculateAbilityDamage(internal);
    }

    @PostMapping("/calculate/batch")
    public Map<AbilityId, AbilityCardResultDto> calculateBatch(@RequestBody BatchDamageCalcRequest request) {
        return batchService.calculateBatch(request);
    }
}