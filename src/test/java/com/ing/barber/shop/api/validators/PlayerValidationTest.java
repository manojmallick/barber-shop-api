package com.ing.barber.shop.api.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
 
public class PlayerValidationTest {

    @Mock
    BarberRepository barberRepository;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
 
    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
 
    @AfterClass
    public static void close() {
        validatorFactory.close();
    }
 
    @Test
    public void shouldHaveNoViolations() {
        //given:
        Barber player = new Barber();
        player.setId("");
 
        //when:
        Set<ConstraintViolation<Barber>> violations
                = validator.validate(player);
 
        //then:
        assertTrue(violations.isEmpty());
    }
 
    @Test
    public void shouldDetectInvalidName() {
        //given too short name:
        Barber player = new Barber();
        player.setId("");
 
        //when:
        Set<ConstraintViolation<Barber>> violations
                = validator.validate(player);
 
        //then:
        assertEquals(violations.size(), 1);
 
        ConstraintViolation<Barber> violation
                = violations.iterator().next();
        assertEquals("size must be between 3 and 3",
                     violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals("a", violation.getInvalidValue());
    }
}
