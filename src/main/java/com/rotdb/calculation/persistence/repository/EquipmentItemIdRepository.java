package com.rotdb.calculation.persistence.repository;

import com.rotdb.calculation.persistence.entity.EquipmentItemIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentItemIdRepository extends JpaRepository<EquipmentItemIdEntity, Integer> {
}
