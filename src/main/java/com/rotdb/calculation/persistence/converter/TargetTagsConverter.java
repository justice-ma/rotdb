package com.rotdb.calculation.persistence.converter;

import com.rotdb.calculation.domain.model.enums.TargetTags;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Converter
public class TargetTagsConverter implements AttributeConverter<EnumSet<TargetTags>, String> {
    @Override
    public String convertToDatabaseColumn(EnumSet<TargetTags> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public EnumSet<TargetTags> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return EnumSet.noneOf(TargetTags.class);
        }

        dbData = dbData
                .replace("{", "")
                .replace("}", "")
                .replace("\"", "");

        if (dbData.isBlank()) {
            return EnumSet.noneOf(TargetTags.class);
        }

        return Arrays.stream(dbData.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(TargetTags::valueOf)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(TargetTags.class)));
    }
}
