package com.rotdb.api.controller;

import com.rotdb.persistence.entity.TargetEntity;
import com.rotdb.persistence.repository.TargetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/by-title")
    public TargetEntity getTargetByTitle(@RequestParam String title) {
        return repo.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
