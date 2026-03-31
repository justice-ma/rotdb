package com.rotdb.presets.api;

import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PresetRequest(
        @NotBlank String presetName,
        @NotNull DamageCalcRequestDto payload
        ) {}
