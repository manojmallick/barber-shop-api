package com.ing.barber.shop.api.error;

/** The type Resource already exists. */
public class ResourceAlreadyExists extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private ErrorCodes errorCodes;

  /**
   * Instantiates a new Resource already exists.
   *
   * @param message the message
   * @param errorCodes the error codes
   */
  public ResourceAlreadyExists(String message, ErrorCodes errorCodes) {
    super(message);
    this.errorCodes = errorCodes;
  }

  /**
   * Instantiates a new Resource already exists.
   *
   * @param message the message
   * @param errorCodes the error codes
   * @param exception the exception
   */
  public ResourceAlreadyExists(String message, ErrorCodes errorCodes, Exception exception) {

    super(message, exception);
    this.errorCodes = errorCodes;
  }

  /**
   * Instantiates a new Resource already exists.
   *
   * @param message the message
   */
  public ResourceAlreadyExists(String message) {
    super(message);
  }

  public ErrorCodes getErrorCodes() {
    return errorCodes;
  }
}
