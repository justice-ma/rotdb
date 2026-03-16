package com.rotdb.calculation.api.controller;

import com.rotdb.calculation.api.dto.SpellDto;
import com.rotdb.calculation.domain.model.enums.Spells;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/spells")
public class SpellController {
    @GetMapping
    public List<SpellDto> getSpells(@RequestParam(required = false) String q) {
        String query = q == null ? "" : q.trim().toLowerCase();

        return Arrays.stream(Spells.values())
                .filter(spell -> query.isBlank() ||
                        spell.getName().toLowerCase().contains(query))
                .map(spell -> new SpellDto(
                        spell.name(),
                        spell.getDamageTier(),
                        spell.getName()
                )).toList();
    }
}
