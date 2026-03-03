package com.rotdb.api;

import com.rotdb.persistence.entity.EquipmentEntity;
import com.rotdb.persistence.repository.EquipmentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipment")
public class EquipmentTestController {
    private final EquipmentRepository repo;

    public EquipmentTestController(EquipmentRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{id}")
    public EquipmentEntity getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }
}
