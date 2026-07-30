package com.rotdb.calculation.domain.engine;

import com.rotdb.calculation.application.normalization.DamageRequestNormalizer;
import com.rotdb.calculation.application.validation.DamageRequestValidator;
import com.rotdb.calculation.application.validation.PrayerRequestValidator;
import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.calculation.domain.model.DamageResult;
import com.rotdb.calculation.domain.model.HitResult;
import com.rotdb.calculation.domain.model.context.AggregatedCalculationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.ContextBuilder;
import com.rotdb.calculation.domain.model.context.DamageContext;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public final class CalculationEngine {
    private final AbilityDamagePipeline abilityPipeline = new AbilityDamagePipeline();
    private final DamageRequestNormalizer normalizer = new DamageRequestNormalizer();
    private final DamageRequestValidator validator = new DamageRequestValidator();
    private final PrayerRequestValidator prayerValidator = new PrayerRequestValidator();

    public DamageResult calculateAbilityDamage(DamageRequest snapshotRequest,
                                               DamageRequest liveRequest, CalculationMode mode,
                                               Integer hitIndex) {
        validator.validate(snapshotRequest);
        snapshotRequest = normalizer.normalize(snapshotRequest);

        if (liveRequest == null) {
            liveRequest = normalizer.normalize(snapshotRequest);
        } else {
            liveRequest = normalizer.normalize(liveRequest);
        }

        CalculationContext snapshotContext = ContextBuilder.build(snapshotRequest);
        CalculationContext liveContext = ContextBuilder.build(liveRequest);

        if (mode == CalculationMode.HIT) {
            CalculationContext newSnapshotContext = ContextBuilder.build(snapshotRequest);
            List<AbilityHitsContext> hits = newSnapshotContext.getAbility().getHits();
            List<AbilityHitsContext> newHits = new ArrayList<>(List.of(hits.get(hitIndex)));
            newSnapshotContext.getAbility().setHits(newHits);

            CalculationContext newLiveContext = ContextBuilder.build(liveRequest);
            List<AbilityHitsContext> liveHits = newLiveContext.getAbility().getHits();
            List<AbilityHitsContext> newLiveHits = new ArrayList<>(List.of(liveHits.get(hitIndex)));
            newLiveContext.getAbility().setHits(newLiveHits);

            AggregatedCalculationContext hitModeAggregatedCalculationContext = new AggregatedCalculationContext(
                    newSnapshotContext,
                    newLiveContext
            );

            prayerValidator.validatePrayers(newSnapshotContext.getSelectedPrayers());

            abilityPipeline.run(hitModeAggregatedCalculationContext);
            return mapToResult(newSnapshotContext);
        } else {
            AggregatedCalculationContext aggregatedCalculationContext = new AggregatedCalculationContext(
                    snapshotContext,
                    liveContext
            );
            prayerValidator.validatePrayers(snapshotContext.getSelectedPrayers());

            abilityPipeline.run(aggregatedCalculationContext);
            return mapToResult(snapshotContext);
        }
    }

    public DamageResult calculateAbilityDamage(DamageRequest snapshotRequest, CalculationMode mode, Integer hitIndex) {
        return calculateAbilityDamage(snapshotRequest, null, mode, hitIndex);
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
                hits);
    }
}
