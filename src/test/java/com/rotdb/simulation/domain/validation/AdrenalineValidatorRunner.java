package com.rotdb.simulation.domain.validation;

import com.rotdb.shared.ability.AbilityId;
import com.rotdb.simulation.domain.model.context.AbilityContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;
import com.rotdb.simulation.domain.model.context.AbilityCooldownContext;
import com.rotdb.simulation.domain.model.context.RotationContext;
import com.rotdb.calculation.domain.model.context.CalculationContext;

import java.util.HashMap;

/**
 * Manual test runner for AdrenalineValidator.
 * Run this class directly to see console output of validator behavior.
 */
public class AdrenalineValidatorRunner {

    public static void main(String[] args) {
        AdrenalineValidator adrenalineValidator = new AdrenalineValidator();
        AbilityCooldownValidator cooldownValidator = new AbilityCooldownValidator();
        
        System.out.println("=== Validator Manual Testing (Adrenaline + Cooldown) ===\n");
        
        // Test 1: Normal case with 50 adrenaline
        System.out.println("Test 1: Starting with 50 adrenaline, using ASSAULT");
        RotationContext rc1 = createRotationContext(50, AbilityId.ASSAULT);
        CalculationContext cc1 = new CalculationContext();
        try {
            boolean adrenalineResult = adrenalineValidator.validate(rc1, cc1);
            boolean cooldownResult = cooldownValidator.validate(rc1, cc1);
            System.out.println("Adrenaline result: " + adrenalineResult);
            System.out.println("Cooldown result: " + cooldownResult);
            System.out.println("Adrenaline after: " + rc1.getAdrenalineContext().getAdrenaline());
            System.out.println("Cooldown map: " + rc1.getAbilityCooldownContext().getCooldownMap());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 2: Adrenaline capping at 100
        System.out.println("Test 2: Starting with 80 adrenaline, using ASSAULT");
        RotationContext rc2 = createRotationContext(80, AbilityId.ASSAULT);
        CalculationContext cc2 = new CalculationContext();
        try {
            boolean adrenalineResult = adrenalineValidator.validate(rc2, cc2);
            boolean cooldownResult = cooldownValidator.validate(rc2, cc2);
            System.out.println("Adrenaline result: " + adrenalineResult);
            System.out.println("Cooldown result: " + cooldownResult);
            System.out.println("Adrenaline after: " + rc2.getAdrenalineContext().getAdrenaline());
            System.out.println("(Should be capped at 100)");
            System.out.println("Cooldown map: " + rc2.getAbilityCooldownContext().getCooldownMap());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 3: Insufficient adrenaline
        System.out.println("Test 3: Starting with 10 adrenaline, using ASSAULT");
        RotationContext rc3 = createRotationContext(10, AbilityId.ASSAULT);
        CalculationContext cc3 = new CalculationContext();
        try {
            boolean adrenalineResult = adrenalineValidator.validate(rc3, cc3);
            boolean cooldownResult = cooldownValidator.validate(rc3, cc3);
            System.out.println("Adrenaline result: " + adrenalineResult);
            System.out.println("Cooldown result: " + cooldownResult);
            System.out.println("Adrenaline after: " + rc3.getAdrenalineContext().getAdrenaline());
            System.out.println("Cooldown map: " + rc3.getAbilityCooldownContext().getCooldownMap());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println();
        
        // Test 4: Low adrenaline ability
        System.out.println("Test 4: Starting with 5 adrenaline, using MELEEAUTO");
        RotationContext rc4 = createRotationContext(5, AbilityId.MELEEAUTO);
        CalculationContext cc4 = new CalculationContext();
        try {
            boolean adrenalineResult = adrenalineValidator.validate(rc4, cc4);
            boolean cooldownResult = cooldownValidator.validate(rc4, cc4);
            System.out.println("Adrenaline result: " + adrenalineResult);
            System.out.println("Cooldown result: " + cooldownResult);
            System.out.println("Adrenaline after: " + rc4.getAdrenalineContext().getAdrenaline());
            System.out.println("Cooldown map: " + rc4.getAbilityCooldownContext().getCooldownMap());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println();

        // Test 5: Cooldown enforcement with repeated ability
        System.out.println("Test 5: Cooldown check on repeated ASSAULT use");
        RotationContext rc5 = createRotationContext(100, AbilityId.ASSAULT);
        CalculationContext cc5 = new CalculationContext();
        try {
            boolean firstUse = cooldownValidator.validate(rc5, cc5);
            System.out.println("First use cooldown result: " + firstUse);
            System.out.println("Cooldown map after first use: " + rc5.getAbilityCooldownContext().getCooldownMap());

            boolean secondUse = cooldownValidator.validate(rc5, cc5);
            System.out.println("Second use cooldown result: " + secondUse);
            System.out.println("Cooldown map after second use: " + rc5.getAbilityCooldownContext().getCooldownMap());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Cooldown map at exception: " + rc5.getAbilityCooldownContext().getCooldownMap());
        }
        System.out.println();
    }

    private static RotationContext createRotationContext(int startingAdrenaline, AbilityId abilityId) {
        RotationContext rc = new RotationContext();
        
        AbilityContext ac = new AbilityContext();
        ac.setAbilityId(abilityId);
        rc.setAbilityContext(ac);
        
        AdrenalineContext adc = new AdrenalineContext();
        adc.setAdrenaline(startingAdrenaline);
        rc.setAdrenalineContext(adc);

        AbilityCooldownContext cdc = new AbilityCooldownContext();
        cdc.setCooldownMap(new HashMap<>());
        rc.setAbilityCooldownContext(cdc);
        
        return rc;
    }
}
