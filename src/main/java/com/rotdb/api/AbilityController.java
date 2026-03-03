package com.rotdb.api;

import com.rotdb.api.dto.AbilityDto;
import com.rotdb.model.enums.CombatStyles;
import com.rotdb.service.AbilityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abilities")
public class AbilityController {
    private final AbilityService abilityService;

    public AbilityController(AbilityService ability) {
        this.abilityService = ability;
    }

    @GetMapping
    public List<AbilityDto> filter(@RequestParam("style") CombatStyles style) {
        return abilityService.listByStyle(style);
    }
}
