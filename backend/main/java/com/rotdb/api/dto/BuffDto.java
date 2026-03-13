package com.rotdb.api.dto;

import com.rotdb.domain.model.enums.BuffId;

public record BuffDto (
   String id,
   String label,
   boolean stackable,
   int min,
   int max
) {}
