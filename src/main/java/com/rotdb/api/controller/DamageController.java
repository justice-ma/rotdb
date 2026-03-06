package com.rotdb.api.controller;

import com.rotdb.ability.AbilityId;
import com.rotdb.api.dto.AbilityCardResultDto;
import com.rotdb.api.dto.DamageCalcRequestDto;
import com.rotdb.api.mapper.DamageRequestMapper;
import com.rotdb.api.request.BatchDamageCalcRequest;
import com.rotdb.domain.engine.CalculationEngine;
import com.rotdb.domain.model.DamageRequest;
import com.rotdb.domain.model.DamageResult;
import com.rotdb.application.service.DamageBatchService;
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