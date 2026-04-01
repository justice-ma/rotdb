package com.rotdb.presets.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rotdb.auth.application.AuthService;
import com.rotdb.auth.domain.User;
import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.presets.api.PresetRequest;
import com.rotdb.presets.api.PresetResult;
import com.rotdb.presets.domain.UserPreset;
import com.rotdb.presets.persistence.UserPresetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PresetService {
    private final UserPresetRepository userPresetRepository;
    private final ObjectMapper objectMapper;
    private final AuthService authService;


    public PresetService(UserPresetRepository userPresetRepository, ObjectMapper objectMapper, AuthService authService) {
        this.userPresetRepository = userPresetRepository;
        this.objectMapper = objectMapper;
        this.authService = authService;
    }

    public PresetResult createPreset(PresetRequest request) {
        User user = authService.getCurrentUser();
        String jsonPayload = serializePayload(request.payload());
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
        Long userId = authService.getCurrentUser().getId();
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

    public PresetResult getPresetById(Long presetId) {
        Long userId = authService.getCurrentUser().getId();
        UserPreset preset = userPresetRepository.findByIdAndUserId(presetId, userId);

        if (preset == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preset not found for this user");
        }

        try
        {
            return new PresetResult(
                    preset.getId(),
                    preset.getPresetName(),
                    objectMapper.readValue(preset.getPayload(), DamageCalcRequestDto.class),
                    preset.getCreatedAt(),
                    preset.getUpdatedAt()
                    );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize preset payload for preset id " + preset.getId(), e);
        }
    }

    public PresetResult updatePreset(Long presetId, PresetRequest request) {
        User user = authService.getCurrentUser();
        UserPreset preset = userPresetRepository.findByIdAndUserId(presetId, user.getId());

        if (preset == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preset not found for this user");
        }

        preset.setUpdatedAt(LocalDateTime.now());
        preset.setPresetName(request.presetName());
        preset.setPayload(serializePayload(request.payload()));

        userPresetRepository.save(preset);
        return new PresetResult(preset.getId(), preset.getPresetName(), request.payload(), preset.getCreatedAt(), preset.getUpdatedAt());
    }

    public void deletePreset(Long presetId) {
        User user = authService.getCurrentUser();
        UserPreset preset = userPresetRepository.findByIdAndUserId(presetId, user.getId());

        if (preset == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Preset not found for this user");
        }

        userPresetRepository.deleteById(presetId);
    }

    private String serializePayload(DamageCalcRequestDto payload) {
        String jsonPayload;
        try {
            jsonPayload = objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize preset payload", e);
        }
        return jsonPayload;
    }
}
