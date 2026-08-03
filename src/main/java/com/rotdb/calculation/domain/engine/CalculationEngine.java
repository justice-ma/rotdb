package com.rotdb.calculation.domain.engine;

import com.rotdb.calculation.application.normalization.DamageRequestNormalizer;
import com.rotdb.calculation.application.validation.DamageRequestValidator;
import com.rotdb.calculation.application.validation.PrayerRequestValidator;
import com.rotdb.calculation.domain.model.HitResult;
import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.calculation.domain.model.DerivedStatsResult;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.ContextBuilder;
import com.rotdb.calculation.domain.model.context.DamageContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public final class CalculationEngine {
    private final AbilityDamagePipeline abilityPipeline = new AbilityDamagePipeline();
    private final DamageRequestNormalizer normalizer = new DamageRequestNormalizer();
    private final DamageRequestValidator validator = new DamageRequestValidator();
    private final PrayerRequestValidator prayerValidator = new PrayerRequestValidator();
    public DamageResult calculateAbilityDamage(DamageRequest request) {
        validator.validate(request);
        request = normalizer.normalize(request);

        CalculationContext context = ContextBuilder.build(request);

        prayerValidator.validatePrayers(context.getSelectedPrayers());

        abilityPipeline.run(context);
        return mapToResult(context);
    }

    public DerivedStatsResult calculateDerivedStats(DamageRequest request) {
        request = normalizer.normalize(request);
        return mapDerivedStats(request.getSkills(), request.getEquipment(), request.getBuffs());
    }

    public DamageResult mapToResult(CalculationContext context) {
        DamageContext damage = context.getDamage();
        List<AbilityHitsContext> abilityHits = context.getAbility().getHits();

        List<HitResult> hits = IntStream.range(0, abilityHits.size())
                .mapToObj(i -> {
                    var h = abilityHits.get(i);
                    return new HitResult(
                            h.getCurrentMin(),
                            h.getCurrentMax(),
                            h.getCurrentDamage(),
                            h.getCritMin(),
                            h.getCritMax(),
                            h.getCritDamage(),
                            h.getNonCritMin(),
                            h.getNonCritMax(),
                            h.getNonCritDamage(),
                            i,
                            h.getType()
                    );
                })
                .toList();

        return new DamageResult(
                damage.getCurrentMin(),
                damage.getCurrentMax(),
                damage.getCurrentDamage(),
                damage.getCritMin(),
                damage.getCritMax(),
                damage.getCritDamage(),
                damage.getNonCritMin(),
                damage.getNonCritMax(),
                damage.getNonCritDamage(),
                (int) (damage.getMinPercent()),
                (int) (damage.getMaxPercent()),
                hits,
                mapDerivedStats(context.getSkills(), context.getEquipment(), context.getBuffs()));
    }

    private DerivedStatsResult mapDerivedStats(SkillsContext skills, EquipmentModel equipment, BuffContext buffs) {
        int baseMaxHp = skills.getMaxHp();
        int equipmentLifeBonus = (int) equipment.getTotalLife();
        int effectiveMaxHp = buffs.has(BuffId.BIG_BONED)
                ? (int) ((baseMaxHp + equipmentLifeBonus) * 1.5)
                : baseMaxHp + equipmentLifeBonus;

        return new DerivedStatsResult(
                baseMaxHp,
                equipmentLifeBonus,
                effectiveMaxHp
        );
    }
}
