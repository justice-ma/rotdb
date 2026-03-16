package com.rotdb.calculation.api.dto;

public record PerkDto(
    String id,
    int min,
    int max,
    boolean affectedByLevel20,
    String name
) {
}
