package com.ing.barber.shop.api.error;

import org.springframework.http.ResponseEntity;

/** The type Response entity builder. */
public class ResponseEntityBuilder {

  /**
   * Build response entity.
   *
   * @param apiError the api error
   * @return the response entity
   */
  public static ResponseEntity<Object> build(ApiError apiError) {
    return new ResponseEntity(apiError, apiError.getStatus());
  }
}
