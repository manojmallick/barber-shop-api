package com.ing.barber.shop.api.validators;

import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/** The type Shop validator. */
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ShopValidator implements ConstraintValidator<ValidShop, String> {

  @Autowired private final ShopRepository shopRepository;

  @Override
  public boolean isValid(final String shopId, final ConstraintValidatorContext context) {
    if (shopId != null) {
      Optional<Shop> storedShop = shopRepository.findById(shopId);
      if (storedShop.isPresent()) {
        return true;
      }
    }
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("shop is not found").addConstraintViolation();

    return false;
  }
}
