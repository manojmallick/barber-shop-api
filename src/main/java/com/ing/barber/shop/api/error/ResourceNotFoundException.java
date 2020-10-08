package com.ing.barber.shop.api.error;

/**
 * The type Resource not found exception.
 */
public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private ErrorCodes errorCodes;

  /**
   * Instantiates a new Resource not found exception.
   *
   * @param message    the message
   * @param errorCodes the error codes
   */
  public ResourceNotFoundException(String message, ErrorCodes errorCodes) {
    super(message);
    this.errorCodes = errorCodes;
  }

  /**
   * Instantiates a new Resource not found exception.
   *
   * @param message    the message
   * @param exception  the exception
   * @param errorCodes the error codes
   */
  public ResourceNotFoundException(String message, Exception exception,
      ErrorCodes errorCodes) {

    super(message, exception);
    this.errorCodes = errorCodes;
  }

  /**
   * Instantiates a new Resource not found exception.
   *
   * @param message the message
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }


  public ErrorCodes getErrorCodes() {
    return errorCodes;
  }
}