package com.rotdb.calculation.api.dto;

public record BuffDto (
   String id,
   String label,
   boolean stackable,
   int min,
   int max
) {}
