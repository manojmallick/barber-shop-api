package com.ing.barber.shop.api.validators;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import com.ing.barber.shop.api.util.BarberShopApiConstants;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class BarberValidator implements ConstraintValidator<ValidBarber, String> {


  @Autowired
  private BarberRepository barberRepository;

  @Override
  public boolean isValid(final String barberId, final ConstraintValidatorContext context) {
    if (barberId != null && !barberId.isEmpty()) {
      Optional<Barber> storedBarber = barberRepository.findById(barberId);
      if (storedBarber.isPresent()) {
        return true;
      }
    } else {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(BarberShopApiConstants.BARBER_IS_NOT_FOUND)
        .addConstraintViolation();

    return false;
  }
}
