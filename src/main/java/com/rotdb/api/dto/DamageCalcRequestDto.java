package com.rotdb.api.dto;

import com.rotdb.ability.AbilityId;
import com.rotdb.domain.model.enums.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record DamageCalcRequestDto(
        Skills skills,
        AbilityId abilityId,
        EquipmentIds equipment,
        Long ammoId,
        BuffIds buffs,
        String targetTitle,
        List<PotionSelection> potions,
        Spells spell,
        Set<Prayer> selectedPrayers,
        Boolean berserkersFury,
        Familiars selectedFamiliar,
        PerkSelection perks
) {
    public record Skills(Integer strength, Integer magic,Integer ranged, Integer necromancy, Integer attack) {}
    public record EquipmentIds(Long mainhandId, Long offhandId, Long headId, Long bodyId,
                               Long glovesId, Long legsId, Long bootsId, Long pocketId, Long ammoId,
                               Long ringId, Long neckId, Long capeId, Long quiverId) {}
    public record BuffIds (Set<BuffId> enabledBuffs, Map<BuffId, Integer> buffStacks) {}
    public record PotionSelection (Potions pot, Stats stat) {}
    public record PerkSelection (Map<Perks, Integer> selectedPerks, Boolean itemLevel20) {}
}
