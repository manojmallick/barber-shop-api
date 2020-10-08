package com.ing.barber.shop.api.validators;

import com.ing.barber.shop.api.barber.repo.BarberRepository;
import com.ing.barber.shop.api.util.BarberShopApiConstants;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/** The type Barber validator. */
@Slf4j

@AllArgsConstructor(onConstructor_ = {@Autowired})
public class BarberValidator implements ConstraintValidator<ValidBarber, String> {

  @Autowired private final BarberRepository barberRepository;

  @Override
  public boolean isValid(final String barberId, final ConstraintValidatorContext context) {
    if (barberId != null && !barberId.isEmpty()) {
      if (barberRepository.findById(barberId).isPresent()) {
        return true;
      }
    } else {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context
        .buildConstraintViolationWithTemplate(BarberShopApiConstants.BARBER_IS_NOT_FOUND)
        .addConstraintViolation();

    return false;
  }
}
