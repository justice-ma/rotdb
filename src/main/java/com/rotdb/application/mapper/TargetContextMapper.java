package com.rotdb.application.mapper;

import com.rotdb.domain.model.context.TargetContext;
import com.rotdb.domain.model.enums.CombatStyles;
import com.rotdb.domain.model.equipment.EquipmentModel;
import com.rotdb.persistence.repository.TargetRepository;
import org.springframework.stereotype.Component;

import static com.rotdb.domain.model.enums.CombatStyles.*;

@Component
public class TargetContextMapper {
    private final TargetRepository repo;

    public TargetContextMapper(TargetRepository targetRepository) {
        this.repo = targetRepository;
    }
    public TargetContext from(String targetRequest, EquipmentModel equipment) {
        TargetContext target = new TargetContext();
        CombatStyles style = equipment.getCombatStyle();

        if (targetRequest == null) {
            return target;
        }
        var entityOpt = repo.findByTitle(targetRequest);

        if (entityOpt.isEmpty()) {
            return target;
        }

        var entity = entityOpt.get();

        target.setName(entity.getName());
        target.setArmour(entity.getArmour1());
        target.setDefence(entity.getDefence1());

        if (style == MAGIC) {
            target.setAffinity(entity.getAffMagic() == null ? 90 : entity.getAffMagic());
        } else if (style == MELEE)  {
            target.setAffinity(entity.getAffMelee() == null ? 90 : entity.getAffMelee());
        } else if (style == RANGED) {
            target.setAffinity(entity.getAffRanged() == null ? 90 : entity.getAffRanged());
        } else {
            target.setAffinity(60);
        }

        return target;
    }
}
