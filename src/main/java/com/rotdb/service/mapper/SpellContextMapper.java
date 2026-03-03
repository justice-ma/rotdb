package com.rotdb.service.mapper;

import com.rotdb.api.dto.DamageCalcRequest;
import com.rotdb.model.enums.Spells;
import com.rotdb.model.player.SpellContext;
import org.springframework.stereotype.Component;

@Component
public class SpellContextMapper {
    public SpellContext from(Spells request) {
        SpellContext spell = new SpellContext();
        if (request == null) {
            spell.setSpell(Spells.WINDRUSH);
            return spell;
        }
        spell.setSpell(request);
        return spell;
    }
}
