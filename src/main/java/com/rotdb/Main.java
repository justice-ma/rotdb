package com.rotdb;

import com.rotdb.ability.AbilityId;
import com.rotdb.ability.AbilityProvider;

public class Main {
    public static void main(String[] args) {
        for (AbilityId a : AbilityId.values()) {
            System.out.println(
                    a.name() + "(" + a.getStyle() + ", " + a.getTier() + ",\"" + AbilityProvider.get(a).getName() + "\"),"
            );
        }
    }
}
