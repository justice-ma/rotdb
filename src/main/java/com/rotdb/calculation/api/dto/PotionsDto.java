package com.rotdb.calculation.api.dto;

public record PotionsDto(
        String id,
        int flatBonus,
        double multiplicativeBonus,
        String name
) {}
