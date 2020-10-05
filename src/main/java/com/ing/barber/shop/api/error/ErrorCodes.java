package com.ing.barber.shop.api.error;

public enum ErrorCodes {
  GENERIC_ERROR("errors.generic"),
  ERROR_UNKNOWN_MEDIA_TYPE("errors.unknownMediaType"),
  ERROR_NOT_READABLE_REQUEST("errors.notReadableRequest"),
  ERROR_INVALID_METHOD_ARGUMENT("error.invalidMethodArgument"),
  ERROR_MISSING_PARAMETERS("error.missingParameters"),
  ERROR_TYPE_MISMATCH("error.typeMisMatch"),
  ERROR_NO_HANDLER("error.noHandler"),
  ERROR_NOT_FOUND("error.notFound"),
  ERROR_RESOURCE_EXIST("error.resourceExist"),
  ERROR_INTERNAL_SERVER_ERROR("error.internalServerError"),
  ERROR_BOOKING_TIME_NOT_AVAILABLE("error.validation.bookingTimeNotAvailable"),
  ERROR_BOOKING_TIME_NOT_VALID("error.validation.bookingTimeNotValid"),
  ERROR_WHILE_PARSING("error.generic.400"),
  ERROR_FEATURE_NOT_AVAILABLE("error.validation.featureNotAvailable");
  private String code;

  ErrorCodes(final String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
