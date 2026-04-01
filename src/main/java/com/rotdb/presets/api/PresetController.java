package com.rotdb.presets.api;

import com.rotdb.presets.application.PresetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presets")
public class PresetController {
    private final PresetService presetService;

    public PresetController(PresetService presetService) {
        this.presetService = presetService;
    }

    @PostMapping
    public PresetResult createPreset(@Valid @RequestBody PresetRequest request) {
        return presetService.createPreset(request);
    }

    @PutMapping("/{presetId}")
    public PresetResult updatePreset(
            @PathVariable Long presetId,
            @Valid @RequestBody PresetRequest request) {
        return presetService.updatePreset(presetId, request);
    }

    @GetMapping
    public List<PresetResult> getPresets() {
        return presetService.getPresetsForCurrentUser();
    }

    @GetMapping("/{presetId}")
    public PresetResult getPresetById(@PathVariable Long presetId) {
        return presetService.getPresetById(presetId);
    }

    @DeleteMapping("/{presetId}")
    public ResponseEntity<Void> deletePreset(@PathVariable Long presetId) {
        presetService.deletePreset(presetId);
        return ResponseEntity.noContent().build();
    }
}
