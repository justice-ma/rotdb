package com.rotdb.presets.api;

import com.rotdb.presets.application.PresetService;
import jakarta.validation.Valid;
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

    @GetMapping
    public List<PresetResult> getPresets() {
        return presetService.getPresetsForCurrentUser();
    }
}
