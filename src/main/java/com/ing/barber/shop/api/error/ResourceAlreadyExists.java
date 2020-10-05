package com.ing.barber.shop.api.error;

public class ResourceAlreadyExists extends RuntimeException {

  public ResourceAlreadyExists(String message) {
    super(message);
  }
}