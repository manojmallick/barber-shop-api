package com.ing.barber.shop.api.error;

import org.springframework.http.HttpStatus;


/**
 * The type Generic api exception.
 */
public class GenericApiException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private final HttpStatus httpStatus;
  private final ErrorCodes errorCodes;

  /**
   * Instantiates a new Generic api exception.
   *
   * @param message    the message
   * @param httpStatus the http status
   * @param errorCodes the error codes
   */
  public GenericApiException(String message, HttpStatus httpStatus, ErrorCodes errorCodes) {
    super(message);
    this.httpStatus = httpStatus;
    this.errorCodes = errorCodes;
  }

  /**
   * Instantiates a new Generic api exception.
   *
   * @param message    the message
   * @param httpStatus the http status
   * @param errorCodes the error codes
   * @param exception  the exception
   */
  public GenericApiException(String message, HttpStatus httpStatus,
      ErrorCodes errorCodes, Exception exception) {

    super(message, exception);
    this.httpStatus = httpStatus;
    this.errorCodes = errorCodes;
  }

  /**
   * Gets http status.
   *
   * @return the http status
   */
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  /**
   * Gets error codes.
   *
   * @return the error codes
   */
  public ErrorCodes getErrorCodes() {
    return errorCodes;
  }
}