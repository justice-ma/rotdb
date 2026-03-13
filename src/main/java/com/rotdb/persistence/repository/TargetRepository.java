package com.rotdb.persistence.repository;

import com.rotdb.persistence.entity.TargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface TargetRepository extends JpaRepository<TargetEntity, Long> {
    Optional<TargetEntity> findByTitle(String title);
    List<TargetEntity> findByTitleContainingIgnoreCase(String title);
    Optional<TargetEntity> findByTitleIgnoreCase(String title);
}
