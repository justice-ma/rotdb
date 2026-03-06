package com.rotdb.persistence.repository;

import com.rotdb.domain.model.enums.Slots;
import com.rotdb.persistence.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Long> {
    List<EquipmentEntity> findByTitleContainingIgnoreCaseAndSlot(String title, Slots slot);
}
