package com.ing.barber.shop.api.error;

import org.springframework.http.HttpStatus;


public class GenericApiException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private HttpStatus httpStatus;
  private ErrorCodes errorCodes;

  public GenericApiException(String message, HttpStatus httpStatus, ErrorCodes errorCodes) {
    super(message);
    this.httpStatus = httpStatus;
    this.errorCodes = errorCodes;
  }

  public GenericApiException(String message, HttpStatus httpStatus, Exception exception,
      ErrorCodes errorCodes) {

    super(message, exception);
    this.httpStatus = httpStatus;
    this.errorCodes = errorCodes;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public ErrorCodes getErrorCodes() {
    return errorCodes;
  }
}