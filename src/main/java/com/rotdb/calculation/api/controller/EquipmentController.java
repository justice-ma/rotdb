package com.rotdb.calculation.api.controller;

import com.rotdb.calculation.domain.model.enums.Slots;
import com.rotdb.calculation.persistence.entity.EquipmentEntity;
import com.rotdb.calculation.persistence.repository.EquipmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    private final EquipmentRepository repo;

    public EquipmentController(EquipmentRepository repo) {
        this.repo = repo;
    }
    @GetMapping("/search")
    public List<EquipmentEntity> search(
            @RequestParam("q") String query,
            @RequestParam("slot")Slots slot) {
        return repo.findByTitleContainingIgnoreCaseAndSlot(query, slot);
    }

    @PostMapping("/by-ids")
    public List<EquipmentEntity> getByIds(@RequestBody List<Long> ids) {
        return repo.findAllById(ids);
    }
}
