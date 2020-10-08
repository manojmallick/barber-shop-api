package com.ing.barber.shop.api.error;

/**
 * The enum Error codes.
 */
public enum ErrorCodes {
  /**
   * Generic error error codes.
   */
  GENERIC_ERROR("errors.generic"),
  /**
   * Error unknown media type error codes.
   */
  ERROR_UNKNOWN_MEDIA_TYPE("errors.unknownMediaType"),
  /**
   * Error not readable request error codes.
   */
  ERROR_NOT_READABLE_REQUEST("errors.notReadableRequest"),
  /**
   * Error invalid method argument error codes.
   */
  ERROR_INVALID_METHOD_ARGUMENT("error.invalidMethodArgument"),
  /**
   * Error missing parameters error codes.
   */
  ERROR_MISSING_PARAMETERS("error.missingParameters"),
  /**
   * Error type mismatch error codes.
   */
  ERROR_TYPE_MISMATCH("error.typeMisMatch"),
  /**
   * Error no handler error codes.
   */
  ERROR_NO_HANDLER("error.noHandler"),
  /**
   * Error not found error codes.
   */
  ERROR_NOT_FOUND("error.notFound"),

  ERROR_SHOP_NOT_FOUND("error.notFoundShop"),

  ERROR_APPOINTMENT_NOT_FOUND("error.notFoundAppointment"),

  /**
   * Error resource exist error codes.
   */
  ERROR_RESOURCE_EXIST("error.resourceExist"),

  /**
   * Error duplicate booking customer error codes.
   */
  ERROR_DUPLICATE_BOOKING_CUSTOMER("error.duplicateBookingForCustomer"),

  /**
   * Error resource exist barber error codes.
   */
  ERROR_DUPLICATE_BOOKING_BARBER("error.duplicateBookingWithBarber"),
  /**
   * Error internal server error error codes.
   */
  ERROR_INTERNAL_SERVER_ERROR("error.internalServerError"),
  /**
   * Error booking time not available error codes.
   */
  ERROR_BOOKING_TIME_NOT_AVAILABLE("error.validation.bookingTimeNotAvailable"),
  /**
   * Error booking time not valid error codes.
   */
  ERROR_BOOKING_TIME_NOT_VALID("error.validation.bookingTimeNotValid"),
  /**
   * Error while parsing error codes.
   */
  ERROR_WHILE_PARSING("error.generic.400"),
  /**
   * Error feature not available error codes.
   */
  ERROR_FEATURE_NOT_AVAILABLE("error.validation.featureNotAvailable");
  private final String code;

  ErrorCodes(final String code) {
    this.code = code;
  }

  /**
   * Gets code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }
}
