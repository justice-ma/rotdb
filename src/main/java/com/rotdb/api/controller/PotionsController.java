package com.rotdb.api.controller;

import com.rotdb.api.dto.PotionsDto;
import com.rotdb.domain.model.enums.Potions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/potions")
public class PotionsController {
    @GetMapping
    public List<PotionsDto> getPotions() {
        return Arrays.stream(Potions.values()).map(potion -> new PotionsDto(
                potion.name(),
                potion.getFlatBonus(),
                potion.getMultiplicativeBonus(),
                potion.getName()
        )).toList();
    }
}
