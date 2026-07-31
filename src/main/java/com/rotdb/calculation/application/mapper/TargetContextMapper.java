package com.rotdb.calculation.application.mapper;

import com.rotdb.shared.combat.domain.model.context.TargetContext;
import com.rotdb.shared.combat.domain.model.enums.CombatStyles;
import com.rotdb.shared.combat.domain.model.enums.TargetTags;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.calculation.persistence.repository.TargetRepository;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class TargetContextMapper {
    private final TargetRepository repo;

    public TargetContextMapper(TargetRepository targetRepository) {
        this.repo = targetRepository;
    }

    public TargetContext from(
            String targetRequest,
            Integer targetCurrentHp,
            Integer targetMaxHp,
            Integer targetSize,
            EquipmentModel equipment
    ) {
        TargetContext target = new TargetContext();
        CombatStyles style = equipment.getCombatStyle();

        if (targetRequest == null) {
            applyDefaultTargetValues(target, targetCurrentHp, targetMaxHp, targetSize);
            return target;
        }

        var entityOpt = repo.findByTitle(targetRequest);

        if (entityOpt.isEmpty()) {
            applyDefaultTargetValues(target, targetCurrentHp, targetMaxHp, targetSize);
            return target;
        }

        var entity = entityOpt.get();

        target.setName(entity.getName());
        target.setArmour(entity.getArmour1());
        target.setDefence(entity.getDefence1());

        int resolvedSize = targetSize != null ? targetSize : (entity.getSize() != null ? entity.getSize() : 5);

        target.setSize(clampSize(resolvedSize));

        if (style == CombatStyles.MAGIC) {
            target.setAffinity(entity.getAffMagic() == null ? 90 : entity.getAffMagic());
        } else if (style == CombatStyles.MELEE) {
            target.setAffinity(entity.getAffMelee() == null ? 90 : entity.getAffMelee());
        } else if (style == CombatStyles.RANGED) {
            target.setAffinity(entity.getAffRanged() == null ? 90 : entity.getAffRanged());
        } else {
            target.setAffinity(60);
        }

        if (entity.getTags() != null) {
            target.setTags(entity.getTags());
        } else {
            target.setTags(EnumSet.noneOf(TargetTags.class));
        }

        int resolvedMaxHp = targetMaxHp != null
                ? targetMaxHp
                : (entity.getLifepoints1() != null ? entity.getLifepoints1() : 100000);

        int resolvedCurrentHp = targetCurrentHp != null
                ? targetCurrentHp
                : resolvedMaxHp;

        target.setMaxHp(resolvedMaxHp);
        target.setCurrentHp(resolvedCurrentHp);
        target.normalizeHp();

        return target;
    }

    private void applyDefaultTargetValues(
            TargetContext target,
            Integer targetCurrentHp,
            Integer targetMaxHp,
            Integer targetSize
    ) {
        int resolvedMaxHp = targetMaxHp != null ? targetMaxHp : 100000;
        int resolvedCurrentHp = targetCurrentHp != null ? targetCurrentHp : resolvedMaxHp;
        int resolvedSize = targetSize != null ? targetSize : 1;

        target.setMaxHp(resolvedMaxHp);
        target.setCurrentHp(resolvedCurrentHp);
        target.setSize(resolvedSize);
        target.normalizeHp();
        target.setName("Training Dummy");
        target.setArmour(1);
        target.setDefence(1);
        target.setAffinity(90);
        target.setTags(EnumSet.allOf(TargetTags.class));
        target.setSize(clampSize(resolvedSize));
    }

    private int clampSize(int size) {
        if (size < 1) return 1;
        if (size > 10) return 10;
        return size;
    }
}
