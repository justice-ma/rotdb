package com.rotdb.calculation.domain.engine;

import com.rotdb.calculation.application.mapper.*;
import com.rotdb.calculation.application.normalization.DamageRequestNormalizer;
import com.rotdb.calculation.application.validation.DamageRequestValidator;
import com.rotdb.calculation.application.validation.PrayerRequestValidator;
import com.rotdb.calculation.domain.model.*;
import com.rotdb.calculation.domain.resolvers.abilityDamage.criticalStrike.*;
import com.rotdb.shared.combat.domain.model.enums.BuffId;
import com.rotdb.shared.combat.domain.model.context.AbilityHitsContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.calculation.domain.model.context.ContextBuilder;
import com.rotdb.calculation.domain.model.context.DamageContext;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import org.springframework.stereotype.Service;

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
        validator.validate(request);
        request = normalizer.normalize(request);

        CalculationContext context = ContextBuilder.build(request);

        context.setEffectiveStatsResult(calculateEffectiveStats(context.getSkills(), context.getEquipment(), context.getBuffs(), context.getFamiliar(), context.getPerks()));

        prayerValidator.validatePrayers(context.getSelectedPrayers());
        applyEffectiveLeagueStatAdjustments(context.getSkills(), context.getEquipment(), context.getBuffs());
        preCriticalPipeline.run(context);

        postCriticalPipeline.run(context);
        return mapToResult(context);
    }

    public DerivedStatsResult calculateDerivedStats(DamageRequest request) {
        request = normalizer.normalize(request);
        applyEffectiveLeagueStatAdjustments(request.getSkills(), request.getEquipment(), request.getBuffs());
        return mapDerivedStats(request.getSkills(), request.getEquipment(), request.getBuffs());
    }

    private EffectiveStatsResult calculateEffectiveStats(SkillsContext skills, EquipmentModel equipment, BuffContext buffs,
                                                         FamiliarContext familiar, PerkContext perks) {
        statPreparation.run(skills, buffs);
        double baseChance = 0.1;
        double baseDamage = BaseCritResolver.resolve(skills, equipment);
        CritBonus globalBonus = GlobalCritResolver.resolve(buffs, familiar, equipment, perks);
        double globalChance = baseChance + globalBonus.getChanceDelta();
        double globalDamage = baseDamage + globalBonus.getDamageDelta();

        return mapEffectiveStats(equipment, buffs, globalChance, globalDamage, skills);
    }

    public EffectiveStatsResult calculateEffectiveStats(EffectiveStatsRequest request) {
        applyEffectiveLeagueStatAdjustments(request.getSkillsContext(), request.getEquipmentModel(), request.getBuffContext());
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
                mapDerivedStats(context.getSkills(), context.getEquipment(), context.getBuffs()),
                context.getEffectiveStatsResult());
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

    private EffectiveStatsResult mapEffectiveStats(EquipmentModel equipment, BuffContext buffs, double critChance, double critDamage, SkillsContext skillsContext) {
        double prayer = equipment.getTotalPrayer();
        double armour = equipment.getTotalArmour(skillsContext);

        if (buffs.has(BuffId.UNHOLY_CRITUAL)) {
            critChance += 0.15;
            double excess = Math.max(0, critChance - 0.5);
            critChance = Math.max(0, Math.min(critChance, 0.5));
            critDamage += excess;
        }

        return new EffectiveStatsResult(critChance, critDamage, armour, prayer);
    }

    private void applyEffectiveLeagueStatAdjustments(SkillsContext skills, EquipmentModel equipmentModel, BuffContext buffs) {
        if (buffs.has(BuffId.HAVOC_BORN)) {
            equipmentModel.applyTotalArmourModifier(0.75);
            equipmentModel.applyTotalLifeModifier(0.75);
            skills.setMaxHp((int) (skills.getMaxHp() * 0.75));
        }

        if (buffs.has(BuffId.TRUE_EQUILIBRIUM)) {
            equipmentModel.setFlatArmourBonus(equipmentModel.getFlatArmourBonus() + (50 * buffs.getBlessingsPerAlignment()));
            equipmentModel.setFlatPrayerBonus(equipmentModel.getFlatPrayerBonus() + (5 * buffs.getBlessingsPerAlignment()));
            skills.setMaxHp(skills.getMaxHp() + (500 * buffs.getBlessingsPerAlignment()));
        }
    }
}
