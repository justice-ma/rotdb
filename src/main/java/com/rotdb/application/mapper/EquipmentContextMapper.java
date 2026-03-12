package com.rotdb.application.mapper;

import com.rotdb.api.dto.DamageCalcRequestDto;
import com.rotdb.domain.model.equipment.EquipmentModel;
import com.rotdb.persistence.mapper.EquipmentMapper;
import com.rotdb.persistence.repository.EquipmentRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EquipmentContextMapper {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;
    
    public EquipmentContextMapper(EquipmentRepository equipmentRepository, EquipmentMapper equipmentMapper) {
        this.equipmentMapper = equipmentMapper;
        this.equipmentRepository = equipmentRepository;
    }
    
    public EquipmentModel from(DamageCalcRequestDto.EquipmentIds equipmentIds) {
        var ids = new ArrayList<Long>();

        if (equipmentIds.mainhandId() != null) ids.add(equipmentIds.mainhandId());
        if (equipmentIds.offhandId() != null) ids.add(equipmentIds.offhandId());
        if (equipmentIds.headId() != null) ids.add(equipmentIds.headId());
        if (equipmentIds.bodyId() != null) ids.add(equipmentIds.bodyId());
        if (equipmentIds.glovesId() != null) ids.add(equipmentIds.glovesId());
        if (equipmentIds.legsId() != null) ids.add(equipmentIds.legsId());
        if (equipmentIds.bootsId() != null) ids.add(equipmentIds.bootsId());
        if (equipmentIds.pocketId() != null) ids.add(equipmentIds.pocketId());
        if (equipmentIds.ammoId() != null) ids.add(equipmentIds.ammoId());
        if (equipmentIds.ringId() != null) ids.add(equipmentIds.ringId());
        if (equipmentIds.neckId() != null) ids.add(equipmentIds.neckId());
        if (equipmentIds.capeId() != null) ids.add(equipmentIds.capeId());
        if (equipmentIds.quiverId() != null) ids.add(equipmentIds.quiverId());

        var entities = equipmentRepository.findAllById(ids);
        EquipmentModel equipment = equipmentMapper.fromEntities(entities);
        equipment.fillMissingWithEmpty();

        return equipment;
    }
}
