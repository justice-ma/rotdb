package com.rotdb.calculation.api.dto;

public record AbilityCardResultDto(
        double minCoeff,
        double maxCoeff,
        double min,
        double max,
        double avg,
        double critMin,
        double critMax,
        double critAvg,
        double nonCritMin,
        double nonCritMax,
        double nonCritAvg,
        int hits
) {}
