package com.rotdb.calculation.persistence.mapper;

import com.rotdb.calculation.domain.model.enums.CombatStyles;
import com.rotdb.calculation.domain.model.enums.WeaponStyle;
import com.rotdb.calculation.domain.model.equipment.EquipmentModel;
import com.rotdb.calculation.domain.model.equipment.EquipmentSlot;
import com.rotdb.calculation.persistence.entity.EquipmentEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EquipmentMapper {
    public final EquipmentModel fromEntities(List<EquipmentEntity> items) {
        EquipmentModel eq = new EquipmentModel();

        for (EquipmentEntity item : items) {
            EquipmentSlot slot = new EquipmentSlot();
            switch (item.getSlot()) {
                case MAINHAND:
                    slot.setId(item.getId());
                    slot.setDamageTier(item.getDamageTier() == null ? item.getTier() == null ? 0 :
                            item.getTier(): item.getDamageTier());
                    slot.setAccuracyTier(item.getAccuracyTier() == null ? item.getTier() == null ? 0 : item.getTier() : item.getAccuracyTier());
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(0);
                    slot.setRanged(0);
                    slot.setMagic(0);
                    slot.setNecromancy(0);
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setMainhand(slot);
                    break;
                case TWOHANDED:
                    slot.setId(item.getId());
                    slot.setDamageTier(item.getDamageTier() == null ? item.getTier() == null ? 0 :
                            item.getTier(): item.getDamageTier());
                    slot.setAccuracyTier(item.getAccuracyTier() == null ? item.getTier() == null ? 0 : item.getTier() : item.getAccuracyTier());
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(0);
                    slot.setRanged(0);
                    slot.setMagic(0);
                    slot.setNecromancy(0);
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setMainhand(slot);
                    break;
                case OFFHAND:
                    slot.setId(item.getId());
                    slot.setDamageTier(item.getDamageTier() == null ? item.getTier() == null ? 0 : item.getTier() : item.getDamageTier());
                    slot.setAccuracyTier(item.getAccuracyTier() == null ? item.getTier() == null ? 0 : item.getTier() : item.getAccuracyTier());
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(0);
                    slot.setRanged(0);
                    slot.setMagic(0);
                    slot.setNecromancy(0);
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setOffhand(slot);
                    break;
                case HEAD:
                    slot.setId(item.getId());
                    slot.setDamageTier(0);
                    slot.setAccuracyTier(0);
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setHead(slot);
                    break;
                case BODY:
                    slot.setId(item.getId());
                    slot.setDamageTier(0);
                    slot.setAccuracyTier(0);
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setBody(slot);
                    break;
                case GLOVES:
                    slot.setId(item.getId());
                    slot.setDamageTier(0);
                    slot.setAccuracyTier(0);
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setGloves(slot);
                    break;
                case LEGS:
                    slot.setId(item.getId());
                    slot.setDamageTier(0);
                    slot.setAccuracyTier(0);
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setLegs(slot);
                    break;
                case BOOTS:
                    slot.setId(item.getId());
                    slot.setDamageTier(0);
                    slot.setAccuracyTier(0);
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setBoots(slot);
                    break;
                case POCKET:
                    slot.setId(item.getId());
                    slot.setDamageTier(0);
                    slot.setAccuracyTier(0);
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setPocket(slot);
                    break;
                case AMMO:
                    slot.setId(item.getId());
                    slot.setDamageTier(item.getDamageTier() == null ? 0 : item.getDamageTier());
                    slot.setAccuracyTier(item.getAccuracyTier() == null ? 0 : item.getAccuracyTier());
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setAmmo(slot);
                    break;
                case QUIVER:
                    slot.setId(item.getId());
                    slot.setDamageTier(0);
                    slot.setAccuracyTier(0);
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setQuiver(slot);
                    break;
                case RING:
                    slot.setId(item.getId());
                    slot.setDamageTier(0);
                    slot.setAccuracyTier(0);
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setRing(slot);
                    break;
                case NECK:
                    slot.setId(item.getId());
                    slot.setDamageTier(0);
                    slot.setAccuracyTier(0);
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setNeck(slot);
                    break;
                case CAPE:
                    slot.setId(item.getId());
                    slot.setDamageTier(0);
                    slot.setAccuracyTier(0);
                    slot.setRequiredLevel(item.getRequirementsRaw() == null ? "0" : item.getRequirementsRaw());
                    slot.setStrength(item.getStrength() == null ? 0 : item.getStrength());
                    slot.setRanged(item.getRanged() == null ? 0 : item.getRanged());
                    slot.setMagic(item.getMagic() == null ? 0 : item.getMagic());
                    slot.setNecromancy(item.getNecromancy() == null ? 0 : item.getNecromancy());
                    slot.setTitle(item.getName());
                    slot.setClazz(item.getClazz() == null ? CombatStyles.ALL : item.getClazz());
                    slot.setStyle(item.getStyle() == null ? WeaponStyle.NONE : item.getStyle());
                    slot.setEffect(item.getEffectSet());
                    slot.setSlot(item.getSlot());
                    eq.setCape(slot);
                    break;
                default:
                    break;
            }
        }
        return eq;
    }


}
