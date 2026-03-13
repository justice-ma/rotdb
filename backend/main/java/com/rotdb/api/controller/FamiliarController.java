package com.rotdb.api.controller;

import com.rotdb.api.dto.FamiliarDto;
import com.rotdb.domain.model.enums.Familiars;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/familiars")
public class FamiliarController {
    @GetMapping
    public List<FamiliarDto> getFamiliars() {
        return Arrays.stream(Familiars.values())
                .map(familiar -> new FamiliarDto(
                        familiar.name(),
                        familiar.getName()
                )).toList();
    }
}
