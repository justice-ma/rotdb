package com.rotdb.application.validation;

import com.rotdb.domain.model.enums.Prayer;
import com.rotdb.domain.model.enums.PrayerBook;
import com.rotdb.domain.model.enums.PrayerExclusivityGroup;

import java.util.*;
import java.util.stream.Collectors;

public class PrayerRequestValidator {
    public void validatePrayers(Set<Prayer> selectedPrayers) {
        Set<PrayerBook> books = selectedPrayers.stream()
                .map(Prayer::getBook)
                .collect(Collectors.toSet());

        if (books.size() > 1) {
            throw new IllegalArgumentException("Prayers from multiple books cannot be active together.");
        }

        List<Prayer> prayers = new ArrayList<>(selectedPrayers);

        for (int i = 0; i < prayers.size(); i++) {
            for (int j = i + 1; j < prayers.size(); j++) {
                Prayer a = prayers.get(i);
                Prayer b = prayers.get(j);

                if (canStackTogether(a, b)) {
                    continue;
                }

                if (!Collections.disjoint(a.getExclusivityGroup(), b.getExclusivityGroup())) {
                    throw new IllegalArgumentException(
                            "Conflicting prayers selected: " + a.getName() + " and " + b.getName()
                    );
                }
            }
        }
    }

    private boolean canStackTogether(Prayer a, Prayer b) {
        return a.getExclusivityGroup() != b.getExclusivityGroup();
    }
}
