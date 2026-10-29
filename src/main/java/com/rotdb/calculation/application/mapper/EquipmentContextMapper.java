package com.rotdb.calculation.application.mapper;

import com.rotdb.calculation.api.dto.DamageCalcRequestDto;
import com.rotdb.calculation.persistence.entity.EquipmentItemIdEntity;
import com.rotdb.calculation.persistence.repository.EquipmentItemIdRepository;
import com.rotdb.shared.combat.domain.model.enums.EquipmentIdSource;
import com.rotdb.shared.combat.domain.model.enums.Slots;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.calculation.persistence.mapper.EquipmentMapper;
import com.rotdb.calculation.persistence.repository.EquipmentRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EquipmentContextMapper {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;
    private final EquipmentItemIdRepository equipmentItemIdRepository;

    public EquipmentContextMapper(EquipmentRepository equipmentRepository, EquipmentMapper equipmentMapper, EquipmentItemIdRepository equipmentItemIdRepository) {
        this.equipmentMapper = equipmentMapper;
        this.equipmentRepository = equipmentRepository;
        this.equipmentItemIdRepository = equipmentItemIdRepository;
    }

    public EquipmentModel from(DamageCalcRequestDto.EquipmentIds equipmentIds, EquipmentIdSource equipmentIdSource) {
        var ids = new ArrayList<Long>();
        if (equipmentIds != null) {
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
        }

        if (equipmentIdSource == EquipmentIdSource.OFFICIAL) {
            var officialIds = new ArrayList<Integer>();
            for (Long id : ids) {
                if (id >= Integer.MIN_VALUE && id <= Integer.MAX_VALUE) officialIds.add(id.intValue());
            }

            ids.clear();
            for (EquipmentItemIdEntity itemId : equipmentItemIdRepository.findAllById(officialIds)) {
                ids.add(itemId.getEquipmentId());
            }
        }

        var entities = equipmentRepository.findAllById(ids);
        EquipmentModel equipment = equipmentMapper.fromEntities(entities);

        if (equipment.getMainhand() != null &&
                equipment.getMainhand().getSlot() == Slots.TWOHANDED) {
            equipment.setOffhand(null);
        }

        equipment.fillMissingWithEmpty();
        return equipment;
    }
}
