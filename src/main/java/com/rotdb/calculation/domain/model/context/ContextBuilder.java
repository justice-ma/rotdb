package com.rotdb.calculation.domain.model.context;

import com.rotdb.shared.ability.AbilityProvider;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.calculation.domain.model.DamageRequest;
import com.rotdb.shared.combat.domain.model.context.AbilityContext;
import com.rotdb.shared.combat.domain.model.enums.Effect;
import com.rotdb.shared.combat.domain.model.enums.HitCapMode;
import com.rotdb.shared.combat.domain.model.enums.Prayer;
import com.rotdb.shared.combat.domain.model.enums.Targetting;
import com.rotdb.shared.combat.domain.model.equipment.EquipmentModel;
import com.rotdb.shared.combat.domain.model.equipment.FamiliarContext;
import com.rotdb.shared.combat.domain.model.equipment.PerkContext;
import com.rotdb.shared.combat.domain.model.player.BuffContext;
import com.rotdb.shared.combat.domain.model.player.SkillsContext;
import com.rotdb.shared.combat.domain.model.player.SpellContext;
import com.rotdb.calculation.domain.resolvers.PrayerResolver;
import com.rotdb.shared.combat.domain.model.context.TargetContext;

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
    private HitCapMode hitCapMode;

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

    public ContextBuilder hitCapMode(HitCapMode h) {
        this.hitCapMode = h;
        return this;
    }

    public static CalculationContext build(DamageRequest request) {
        CalculationContext context = new CalculationContext();
        context.setEquipment(request.getEquipment());
        context.setAbility(AbilityProvider.get(request.getAbilityId(), context.getEquipment()));
        context.setBuffs(request.getBuffs());
        context.setTarget(request.getTarget());
        context.setSkills(request.getSkills());
        context.setPerks(request.getPerks());
        context.setFamiliar(request.getFamiliar());
        context.setSpellContext(request.getSpell());
        applySpellTargetting(context);

        context.setSelectedPrayers(request.getSelectedPrayers().getSelected());
        context.setZealotsEquipped(request.getEquipment().getNeck().getEffect().contains(Effect.ZEALOTS));

        context.setPrayer(PrayerResolver.resolve(context));
        context.setHitCapMode(request.getHitCapMode());
        return context;
    }

    private static void applySpellTargetting(CalculationContext context) {
        AbilityContext ability = context.getAbility();
        SpellContext spell = context.getSpellContext();

        if (ability == null || ability.getId() != AbilityId.MAGICAUTO || spell == null || spell.getSpell() == null) {
            return;
        }

        Targetting spellTargetting = spell.getSpell().getTargetting();
        if (spellTargetting != Targetting.SINGLE_TARGET) {
            ability.setTargetting(spellTargetting);
        }
    }
}
