package com.rotdb.application.mapper;

import com.rotdb.domain.model.enums.Spells;
import com.rotdb.domain.model.player.SpellContext;
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
