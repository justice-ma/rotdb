package com.rotdb.api.dto;

public record PotionsDto(
        String id,
        int flatBonus,
        double multiplicativeBonus,
        String name
) {}
