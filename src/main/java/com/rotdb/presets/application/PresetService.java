package com.rotdb.presets.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rotdb.auth.domain.User;
import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.presets.api.PresetRequest;
import com.rotdb.presets.api.PresetResult;
import com.rotdb.presets.domain.UserPreset;
import com.rotdb.presets.persistence.UserPresetRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PresetService {
    private final UserPresetRepository userPresetRepository;
    private final ObjectMapper objectMapper;


    public PresetService(UserPresetRepository userPresetRepository, ObjectMapper objectMapper) {
        this.userPresetRepository = userPresetRepository;
        this.objectMapper = objectMapper;
    }

    public PresetResult createPreset(PresetRequest request) {
        User user = getCurrentUser();
        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(request.payload());
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize preset payload", e);
        }

        UserPreset preset = new UserPreset();
        preset.setUser(user);
        preset.setPresetName(request.presetName());
        preset.setPayload(jsonPayload);
        preset.setCreatedAt(LocalDateTime.now());
        preset.setUpdatedAt(LocalDateTime.now());

        UserPreset savedPreset = userPresetRepository.save(preset);

        return new PresetResult(savedPreset.getId(), savedPreset.getPresetName(), request.payload(), savedPreset.getCreatedAt(), savedPreset.getUpdatedAt());
    }

    public List<PresetResult> getPresetsForCurrentUser() {
        Long userId = getCurrentUser().getId();
        List<UserPreset> presets = userPresetRepository.findAllByUserId(userId);
        List<PresetResult> result = new ArrayList<>();

        for (UserPreset preset : presets) {
            try {
                PresetResult presetResult = new PresetResult(
                        preset.getId(),
                        preset.getPresetName(),
                        objectMapper.readValue(preset.getPayload(), DamageCalcRequestDto.class),
                        preset.getCreatedAt(),
                        preset.getUpdatedAt()
                );
                result.add(presetResult);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to deserialize preset payload for preset id " + preset.getId(), e);
            }
        }
        return result;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        return (User) authentication.getPrincipal();
    }
}
