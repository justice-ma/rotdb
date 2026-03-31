package com.rotdb.presets.persistence;

import com.rotdb.presets.domain.UserPreset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPresetRepository extends JpaRepository<UserPreset, Long> {
    List<UserPreset> findAllByUserId(Long userId);
    UserPreset findByIdAndUserId(Long id, Long userId);
}
