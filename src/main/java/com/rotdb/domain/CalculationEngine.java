package com.rotdb.domain;

import com.rotdb.calculator.AbilityDamagePipeline;
import com.rotdb.model.context.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
public final class CalculationEngine {
    private final AbilityDamagePipeline abilityPipeline = new AbilityDamagePipeline();
    private final DamageRequestNormalizer normalizer = new DamageRequestNormalizer();
    private final DamageRequestValidator validator = new DamageRequestValidator();
    public DamageResult calculateAbilityDamage(DamageRequest request) {
        validator.validate(request);
        request = normalizer.normalize(request);

        CalculationContext context = ContextBuilder.build(request);
        abilityPipeline.run(context);
        System.out.println("BASE=" + context.getDamage().getBaseDamage() + " STYLE=" +
                context.getEquipment().getMainhand().getClazz() + " MAGIC=" + context.getSkills().getBoostedMagic() +
                " MHTIER=" + context.getEquipment().getMainhand().getDamageTier() +
                " ACCTIER=" + context.getEquipment().getMainhand().getAccuracyTier() +
                " DW=" + (context.getEquipment().getOffhand().getId() != null) +
                " BONUS=" + context.getEquipment().getTotalStrength() +
                " EFFECTS=" + context.getEquipment().getMainhand().getEffect() +
                " HITCHANCE=" + context.getHitChance());
        return mapToResult(context);
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
                (int) (damage.getMinPercent() * 100),
                (int) (damage.getMaxPercent() * 100),
                hits);
    }
}