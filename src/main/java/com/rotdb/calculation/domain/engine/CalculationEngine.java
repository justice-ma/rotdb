package com.rotdb.calculation.domain.engine;

import com.rotdb.calculation.application.normalization.DamageRequestNormalizer;
import com.rotdb.calculation.application.validation.DamageRequestValidator;
import com.rotdb.calculation.application.validation.PrayerRequestValidator;
import com.rotdb.calculation.domain.model.*;
import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.ContextBuilder;
import com.rotdb.calculation.domain.model.context.DamageContext;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.BaseCritResolver;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.CritBonus;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.GlobalCritResolver;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public final class CalculationEngine {
    private final PostCriticalPipeline postCriticalPipeline = new PostCriticalPipeline();
    private final PreCriticalPipeline preCriticalPipeline = new PreCriticalPipeline();
    private final StatPreparation statPreparation = new StatPreparation();
    private final DamageRequestNormalizer normalizer = new DamageRequestNormalizer();
    private final DamageRequestValidator validator = new DamageRequestValidator();
    private final PrayerRequestValidator prayerValidator = new PrayerRequestValidator();

    public DamageResult calculateAbilityDamage(DamageRequest request) {
        return calculateAbilityDamage(request, null, CalculationMode.ABILITY, null);
    }

    public DamageResult calculateAbilityDamage(DamageRequest snapshotRequest, CalculationMode mode, Integer hitIndex) {
        return calculateAbilityDamage(snapshotRequest, null, mode, hitIndex);
    }

    public DamageResult calculateAbilityDamage(DamageRequest snapshotRequest,
                                               DamageRequest liveRequest, CalculationMode mode,
                                               Integer hitIndex) {
        validator.validate(snapshotRequest);
        snapshotRequest = normalizer.normalize(snapshotRequest);
        liveRequest = liveRequest == null ? snapshotRequest : normalizer.normalize(liveRequest);

        CalculationContext snapshotContext = ContextBuilder.build(snapshotRequest);
        CalculationContext liveContext = ContextBuilder.build(liveRequest);

        if (mode == CalculationMode.HIT) {
            retainSingleHit(snapshotContext, hitIndex);
            retainSingleHit(liveContext, hitIndex);
        }

        snapshotContext.setEffectiveStatsResult(calculateEffectiveStats(snapshotContext.getSkills(),
                snapshotContext.getEquipment(), snapshotContext.getBuffs(),
                snapshotContext.getFamiliar(), snapshotContext.getPerks()));

        prayerValidator.validatePrayers(snapshotContext.getSelectedPrayers());

        if (liveContext.getSkills() != snapshotContext.getSkills()) {
            liveContext.setEffectiveStatsResult(calculateEffectiveStats(liveContext.getSkills(),
                    liveContext.getEquipment(), liveContext.getBuffs(),
                    liveContext.getFamiliar(), liveContext.getPerks()));
        } else {
            liveContext.setEffectiveStatsResult(snapshotContext.getEffectiveStatsResult());
        }

        preCriticalPipeline.run(new AggregatedCalculationContext(snapshotContext, liveContext));
        postCriticalPipeline.run(new AggregatedCalculationContext(snapshotContext, liveContext));

        return mapToResult(snapshotContext);
    }

    private void retainSingleHit(CalculationContext context, Integer hitIndex) {
        List<AbilityHitsContext> hits = context.getAbility().getHits();
        context.getAbility().setHits(new ArrayList<>(List.of(hits.get(hitIndex))));
    }

    public DerivedStatsResult calculateDerivedStats(DamageRequest request) {
        request = normalizer.normalize(request);
        return mapDerivedStats(request.getSkills(), request.getEquipment(), request.getBuffs());
    }

    private EffectiveStatsResult calculateEffectiveStats(SkillsContext skills, EquipmentModel equipment, BuffContext buffs,
                                                         FamiliarContext familiar, PerkContext perks) {
        skills.fillMissingWithOne();
        statPreparation.run(skills, buffs);
        double baseChance = 0.1;
        double baseDamage = BaseCritResolver.resolve(skills, equipment);
        CritBonus globalBonus = GlobalCritResolver.resolve(buffs, familiar, equipment, perks);
        double globalChance = baseChance + globalBonus.getChanceDelta();
        double globalDamage = baseDamage + globalBonus.getDamageDelta();

        return mapEffectiveStats(equipment, buffs, globalChance, globalDamage, skills);
    }

    public EffectiveStatsResult calculateEffectiveStats(EffectiveStatsRequest request) {
        return calculateEffectiveStats(request.getSkillsContext(), request.getEquipmentModel(),
                request.getBuffContext(), request.getFamiliarContext(), request.getPerkContext());
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
                            h.getHitIndex(),
                            h.getHitTiming(),
                            h.getCritChanceModifier(),
                            h.getType(),
                            context.getAbility().getId(),
                            h.isDot()
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
                mapDerivedStats(context.getSkills(), context.getEquipment(), context.getBuffs()),
                context.getEffectiveStatsResult());
    }

    private DerivedStatsResult mapDerivedStats(SkillsContext skills, EquipmentModel equipment, BuffContext buffs) {
        int baseMaxHp = skills.getMaxHp();
        int equipmentLifeBonus = (int) equipment.getTotalLife();
        int effectiveMaxHp = baseMaxHp + equipmentLifeBonus;

        return new DerivedStatsResult(
                baseMaxHp,
                equipmentLifeBonus,
                effectiveMaxHp
        );
    }

    private EffectiveStatsResult mapEffectiveStats(EquipmentModel equipment, BuffContext buffs, double critChance, double critDamage, SkillsContext skillsContext) {
        double prayer = equipment.getTotalPrayer();
        double armour = equipment.getTotalArmour(skillsContext);

        return new EffectiveStatsResult(critChance, critDamage, armour, prayer);
    }
}
