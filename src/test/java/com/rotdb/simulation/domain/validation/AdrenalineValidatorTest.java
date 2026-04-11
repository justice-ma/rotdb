package com.rotdb.simulation.domain.validation;

import com.rotdb.calculation.domain.model.context.CalculationContext;
import com.rotdb.shared.ability.AbilityId;
import com.rotdb.simulation.domain.model.context.AbilityContext;
import com.rotdb.simulation.domain.model.context.AdrenalineContext;
import com.rotdb.simulation.domain.model.context.RotationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AdrenalineValidator")
class AdrenalineValidatorTest {

    private AdrenalineValidator validator;
    private RotationContext rotationContext;
    private CalculationContext calculationContext;

    @BeforeEach
    void setUp() {
        validator = new AdrenalineValidator();
        
        // Create rotation context
        rotationContext = new RotationContext();
        AbilityContext abilityContext = new AbilityContext();
        abilityContext.setAbilityId(AbilityId.ASSAULT); // Example ability ID
        rotationContext.setAbilityContext(abilityContext);
        
        AdrenalineContext adrenalineContext = new AdrenalineContext();
        adrenalineContext.setAdrenaline(0);
        rotationContext.setAdrenalineContext(adrenalineContext);
        
        // Create calculation context
        calculationContext = new CalculationContext();
    }

    @Test
    @DisplayName("Manual test - run with output")
    void manualDebugTest() {
        System.out.println("\n=== AdrenalineValidator Manual Testing ===\n");
        
        // Test 1
        System.out.println("Test 1: Starting with 50 adrenaline, using ASSAULT");
        RotationContext rc1 = createRotationContext(50, AbilityId.ASSAULT);
        CalculationContext cc1 = new CalculationContext();
        try {
            boolean result = validator.validate(rc1, cc1);
            System.out.println("  Result: " + result);
            System.out.println("  Adrenaline after: " + rc1.getAdrenalineContext().getAdrenaline());
        } catch (Exception e) {
            System.out.println("  Exception: " + e.getMessage());
        }
        
        // Test 2
        System.out.println("\nTest 2: Starting with 80 adrenaline, using ASSAULT (should cap at 100)");
        RotationContext rc2 = createRotationContext(80, AbilityId.ASSAULT);
        CalculationContext cc2 = new CalculationContext();
        try {
            boolean result = validator.validate(rc2, cc2);
            System.out.println("  Result: " + result);
            System.out.println("  Adrenaline after: " + rc2.getAdrenalineContext().getAdrenaline());
        } catch (Exception e) {
            System.out.println("  Exception: " + e.getMessage());
        }
        
        // Test 3
        System.out.println("\nTest 3: Starting with 10 adrenaline, using ASSAULT (insufficient)");
        RotationContext rc3 = createRotationContext(10, AbilityId.ASSAULT);
        CalculationContext cc3 = new CalculationContext();
        try {
            boolean result = validator.validate(rc3, cc3);
            System.out.println("  Result: " + result);
            System.out.println("  Adrenaline after: " + rc3.getAdrenalineContext().getAdrenaline());
        } catch (Exception e) {
            System.out.println("  Exception: " + e.getMessage());
        }
        
        System.out.println();
    }

    @Test
    @DisplayName("Should validate ability with sufficient adrenaline")
    void testValidateWithSufficientAdrenaline() {
        rotationContext.getAdrenalineContext().setAdrenaline(50);
        
        boolean result = validator.validate(rotationContext, calculationContext);
        
        assertTrue(result);
    }

    @Test
    @DisplayName("Should cap adrenaline at 100")
    void testAdrenalineCappedAt100() {
        rotationContext.getAdrenalineContext().setAdrenaline(80);
        
        validator.validate(rotationContext, calculationContext);
        
        assertTrue(rotationContext.getAdrenalineContext().getAdrenaline() <= 100);
    }

    @Test
    @DisplayName("Should throw when adrenaline would go negative")
    void testThrowsWhenAdrenalineNegative() {
        rotationContext.getAdrenalineContext().setAdrenaline(-1);
        
        assertThrows(RuntimeException.class, () -> 
            validator.validate(rotationContext, calculationContext)
        );
    }

    private RotationContext createRotationContext(int startingAdrenaline, AbilityId abilityId) {
        RotationContext rc = new RotationContext();
        
        AbilityContext ac = new AbilityContext();
        ac.setAbilityId(abilityId);
        rc.setAbilityContext(ac);
        
        AdrenalineContext adc = new AdrenalineContext();
        adc.setAdrenaline(startingAdrenaline);
        rc.setAdrenalineContext(adc);
        
        return rc;
    }
}
