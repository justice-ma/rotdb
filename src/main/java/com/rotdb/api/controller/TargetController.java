package com.rotdb.api.controller;

import com.rotdb.persistence.entity.TargetEntity;
import com.rotdb.persistence.repository.TargetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/target")
public class TargetController {
    private final TargetRepository repo;

    public TargetController(TargetRepository repo) {
        this.repo = repo;
    }
    @GetMapping("/search")
    public List<TargetEntity> search(@RequestParam("q") String query) {
        return repo.findByTitleContainingIgnoreCase(query);
    }
}
