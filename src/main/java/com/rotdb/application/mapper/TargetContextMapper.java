package com.rotdb.application.mapper;

import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.enums.TargetTags;
import com.rotdb.domain.model.equipment.EquipmentModel;
import com.rotdb.persistence.repository.TargetRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumSet;

import static com.rotdb.domain.model.enums.CombatStyles.*;

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
            EquipmentModel equipment
    ) {
        TargetContext target = new TargetContext();
        CombatStyles style = equipment.getCombatStyle();

        if (targetRequest == null) {
            applyDefaultHp(target, targetCurrentHp, targetMaxHp);
            return target;
        }

        var entityOpt = repo.findByTitle(targetRequest);

        if (entityOpt.isEmpty()) {
            applyDefaultHp(target, targetCurrentHp, targetMaxHp);
            return target;
        }

        var entity = entityOpt.get();

        target.setName(entity.getName());
        target.setArmour(entity.getArmour1());
        target.setDefence(entity.getDefence1());

        if (style == MAGIC) {
            target.setAffinity(entity.getAffMagic() == null ? 90 : entity.getAffMagic());
        } else if (style == MELEE) {
            target.setAffinity(entity.getAffMelee() == null ? 90 : entity.getAffMelee());
        } else if (style == RANGED) {
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

    private void applyDefaultHp(TargetContext target, Integer targetCurrentHp, Integer targetMaxHp) {
        int resolvedMaxHp = targetMaxHp != null ? targetMaxHp : 100000;
        int resolvedCurrentHp = targetCurrentHp != null ? targetCurrentHp : resolvedMaxHp;

        target.setMaxHp(resolvedMaxHp);
        target.setCurrentHp(resolvedCurrentHp);
    }
}
