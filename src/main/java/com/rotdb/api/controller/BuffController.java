package com.rotdb.api.controller;

import com.rotdb.api.dto.BuffDto;
import com.rotdb.domain.model.enums.BuffId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/buffs")
public class BuffController {
    @GetMapping
    public List<BuffDto> getBuffs() {
        return Arrays.stream(BuffId.values())
                .map(buff -> new BuffDto(
                        buff.name(),
                        buff.getLabel(),
                        buff.isStackable(),
                        buff.getMinimumStacks(),
                        buff.getMaximumStacks()
        )).toList();
    }
}
