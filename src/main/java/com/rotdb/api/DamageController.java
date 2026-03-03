package com.rotdb.api;

import com.rotdb.ability.AbilityId;
import com.rotdb.api.dto.AbilityCardResult;
import com.rotdb.api.dto.DamageCalcRequest;
import com.rotdb.api.mapper.DamageRequestMapper;
import com.rotdb.api.request.BatchDamageCalcRequest;
import com.rotdb.domain.CalculationEngine;
import com.rotdb.domain.DamageRequest;
import com.rotdb.domain.DamageResult;
import com.rotdb.service.DamageBatchService;
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
    public DamageResult calculate(@RequestBody DamageCalcRequest request) {
        DamageRequest internal = mapper.from(request);
        return engine.calculateAbilityDamage(internal);
    }

    @PostMapping("/calculate/batch")
    public Map<AbilityId, AbilityCardResult> calculateBatch(@RequestBody BatchDamageCalcRequest request) {
        return batchService.calculateBatch(request);
    }
}