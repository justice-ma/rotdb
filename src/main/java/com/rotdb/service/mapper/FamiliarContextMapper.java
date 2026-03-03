package com.rotdb.service.mapper;

import com.rotdb.model.enums.Familiars;
import com.rotdb.model.equipment.FamiliarContext;
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
