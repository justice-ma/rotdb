package com.rotdb.application.mapper;

import com.rotdb.domain.model.enums.Familiars;
import com.rotdb.domain.model.equipment.FamiliarContext;
import org.springframework.stereotype.Component;

@Component
public class FamiliarContextMapper {
    public FamiliarContext from(Familiars request) {
        if (request == null) {
            return new FamiliarContext();
        }
        FamiliarContext familiar = new FamiliarContext();
        familiar.setName(request);
        return familiar;
    }
}
