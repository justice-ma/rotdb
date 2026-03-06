package com.rotdb.application.mapper;

import com.rotdb.domain.model.player.RelicsContext;
import org.springframework.stereotype.Component;

@Component
public class RelicsContextMapper {
    public RelicsContext from(Boolean request) {
        RelicsContext context = new RelicsContext();
        context.setBerserkersFury(
                request != null && request
        );
        return context;
    }
}
