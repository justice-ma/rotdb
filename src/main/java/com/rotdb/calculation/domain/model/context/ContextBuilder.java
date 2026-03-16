package com.rotdb.calculation.domain.model.context;

import com.rotdb.calculation.ability.AbilityProvider;
import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.calculation.domain.model.enums.Effect;
import com.rotdb.calculation.domain.model.enums.Prayer;
import com.rotdb.calculation.domain.model.equipment.EquipmentModel;
import com.rotdb.calculation.domain.model.equipment.FamiliarContext;
import com.rotdb.calculation.domain.model.equipment.PerkContext;
import com.rotdb.calculation.domain.model.player.BuffContext;
import com.rotdb.calculation.domain.model.player.SkillsContext;
import com.rotdb.calculation.domain.model.player.SpellContext;
import com.rotdb.calculation.domain.resolvers.PrayerResolver;

import java.util.EnumSet;

public class ContextBuilder {
    private EquipmentModel equipment;
    private AbilityContext ability;
    private BuffContext buffs;
    private TargetContext target;
    private SkillsContext skills;
    private PerkContext perks;
    private FamiliarContext familiar;
    private SpellContext spell;
    private EnumSet<Prayer> selectedPrayers;
    private boolean zealotsEquipped;

    public ContextBuilder equipment(EquipmentModel e) {
        this.equipment = e;
        return this;
    }

    public ContextBuilder ability(AbilityContext a) {
        this.ability = a;
        return this;
    }

    public ContextBuilder buffs(BuffContext b) {
        this.buffs = b;
        return this;
    }

    public ContextBuilder target(TargetContext t) {
        this.target = t;
        return this;
    }

    public ContextBuilder skills(SkillsContext s) {
        this.skills = s;
        return this;
    }

    public ContextBuilder perks(PerkContext p) {
        this.perks = p;
        return this;
    }

    public ContextBuilder familiar(FamiliarContext f) {
        this.familiar = f;
        return this;
    }

    public ContextBuilder selectedPrayers(EnumSet<Prayer> selectedPrayers) {
        this.selectedPrayers = selectedPrayers;
        return this;
    }

    public ContextBuilder zealotsEquipped(boolean zealotsEquipped) {
        this.zealotsEquipped = zealotsEquipped;
        return this;
    }

    public ContextBuilder spell(SpellContext spell) {
        this.spell = spell;
        return this;
    }

    public static CalculationContext build(DamageRequest request) {
        CalculationContext context = new CalculationContext();
        context.setEquipment(request.getEquipment());
        context.setAbility(AbilityProvider.get(request.getAbilityId(), context));
        context.setBuffs(request.getBuffs());
        context.setTarget(request.getTarget());
        context.setSkills(request.getSkills());
        context.setPerks(request.getPerks());
        context.setFamiliar(request.getFamiliar());
        context.setSpellContext(request.getSpell());

        context.setSelectedPrayers(request.getSelectedPrayers().getSelected());
        context.setZealotsEquipped(request.getEquipment().getNeck().getEffect().contains(Effect.ZEALOTS));

        context.setPrayer(PrayerResolver.resolve(context));
        return context;
    }
}