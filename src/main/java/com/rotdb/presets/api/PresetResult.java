package com.rotdb.presets.api;

import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.domain.model.DamageRequest;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PresetResult(
        @NotBlank Long id,
        @NotBlank String presetName,
        @NotBlank DamageCalcRequestDto payload,
        @NotBlank LocalDateTime createdAt,
        @NotBlank LocalDateTime updatedAt
) {}
