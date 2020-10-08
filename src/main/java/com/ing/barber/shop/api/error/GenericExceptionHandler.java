package com.ing.barber.shop.api.error;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.MappingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Generic exception handler.
 */
@ControllerAdvice
@Slf4j
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {


  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      final HttpMediaTypeNotSupportedException typeNotSupportedException,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    List<String> details = buildUnKnownMediaMessage(typeNotSupportedException);
    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_UNKNOWN_MEDIA_TYPE.getCode(),
        typeNotSupportedException.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST, "Invalid Media Type", details);
    return ResponseEntityBuilder.build(err);

  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException httpMessageNotReadableException,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<String> details = new ArrayList<>();
    details.add(httpMessageNotReadableException.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(),
        ErrorCodes.ERROR_NOT_READABLE_REQUEST.getCode(),
        httpMessageNotReadableException.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST,
        "Malformed request", details);

    return ResponseEntityBuilder.build(err);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException methodArgumentNotValidException,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<String> details = buildValidationErrors(methodArgumentNotValidException);

    ApiError err = new ApiError(LocalDateTime.now(),
        ErrorCodes.ERROR_INVALID_METHOD_ARGUMENT.getCode(),
        methodArgumentNotValidException.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST,
        "Validation Errors",
        details);

    return ResponseEntityBuilder.build(err);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException missingServletRequestParameterException,
      HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    List<String> details = new ArrayList<String>();
    details
        .add(missingServletRequestParameterException.getParameterName() + " parameter is missing");

    ApiError err = new ApiError(LocalDateTime.now(),
        ErrorCodes.ERROR_MISSING_PARAMETERS.getCode(),
        missingServletRequestParameterException.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST, "Missing Parameters", details);

    return ResponseEntityBuilder.build(err);
  }

  /**
   * Handle method argument type mismatch response entity.
   *
   * @param typeMismatchException the typeMismatchException
   * @param request               the request
   * @return the response entity
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException typeMismatchException,
      WebRequest request) {
    List<String> details = new ArrayList<String>();
    details.add(typeMismatchException.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_TYPE_MISMATCH.getCode(),
        typeMismatchException.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, "Mismatch Type",
        details);

    return ResponseEntityBuilder.build(err);
  }


  /**
   * Handle resource not found exception response entity.
   *
   * @param resourceNotFoundException the resourceNotFoundException
   * @return the response entity
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFoundException(
      ResourceNotFoundException resourceNotFoundException) {

    List<String> details = new ArrayList<String>();
    details.add(resourceNotFoundException.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_NOT_FOUND.getCode(),
        resourceNotFoundException.getClass().getSimpleName(), HttpStatus.NOT_FOUND,
        "Resource Not Found",
        details);

    return ResponseEntityBuilder.build(err);
  }


  /**
   * Handle resource already exists response entity.
   *
   * @param resourceAlreadyExists the resourceAlreadyExists
   * @return the response entity
   */
  @ExceptionHandler({ResourceAlreadyExists.class})
  public ResponseEntity<Object> handleResourceAlreadyExists(
      ResourceAlreadyExists resourceAlreadyExists) {

    List<String> details = new ArrayList<String>();
    details.add(resourceAlreadyExists.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(),
        resourceAlreadyExists.getErrorCodes().getCode(),
        resourceAlreadyExists.getClass().getSimpleName(), HttpStatus.CONFLICT, "Resource exists",
        details);

    return ResponseEntityBuilder.build(err);
  }


  /**
   * Handle illegal argument exception response entity.
   *
   * @param illegalArgumentException the illegalArgumentException
   * @return the response entity
   */
  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(
      IllegalArgumentException illegalArgumentException) {

    List<String> details = new ArrayList<String>();
    details.add(illegalArgumentException.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_RESOURCE_EXIST.getCode(),
        illegalArgumentException.getClass().getSimpleName(), HttpStatus.BAD_REQUEST,
        "Resource exists",
        details);

    return ResponseEntityBuilder.build(err);
  }

  /**
   * Handle mapping exception response entity.
   *
   * @param mappingException the mappingException
   * @return the response entity
   */
  @ExceptionHandler({MappingException.class})
  public ResponseEntity<Object> handleMappingException(MappingException mappingException) {

    List<String> details = new ArrayList<String>();
    details.add(mappingException.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_RESOURCE_EXIST.getCode(),
        mappingException.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, "mapping exception",
        details);

    return ResponseEntityBuilder.build(err);
  }


  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException noHandlerFoundException, HttpHeaders headers, HttpStatus status,
      WebRequest request) {

    log.debug("No Handler Found Exception. [url={}, exception={}]",
        ((ServletWebRequest) request).getRequest().getRequestURI(),
        noHandlerFoundException);
    List<String> details = new ArrayList<String>();
    details.add(String
        .format("Could not find the %s method for URL %s", noHandlerFoundException.getHttpMethod(),
            noHandlerFoundException.getRequestURL()));

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_NO_HANDLER.getCode(),
        noHandlerFoundException.getClass().getSimpleName(), HttpStatus.BAD_REQUEST,
        "Method Not Found",
        details);

    return ResponseEntityBuilder.build(err);

  }

  /**
   * Handle generic api exception response entity.
   *
   * @param genericApiException the genericApiException
   * @param request             the request
   * @return the response entity
   */
  @ExceptionHandler({GenericApiException.class})
  public ResponseEntity<Object> handleGenericApiException(GenericApiException genericApiException,
      WebRequest request) {

    List<String> details = new ArrayList<String>();
    details.add(genericApiException.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), genericApiException.getErrorCodes().getCode(),
        genericApiException.getClass().getSimpleName(), genericApiException.getHttpStatus(),
        genericApiException.getMessage(),
        details);

    return ResponseEntityBuilder.build(err);
  }

  /**
   * Handle all response entity.
   *
   * @param exception the exception
   * @param request   the request
   * @return the response entity
   */
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAll(Exception exception, WebRequest request) {

    log.error("Request to REST API failed. [url={}, exception={}]",
        ((ServletWebRequest) request).getRequest().getRequestURI(), exception);
    List<String> details = new ArrayList<String>();

    ApiError err = new ApiError(LocalDateTime.now(),
        ErrorCodes.ERROR_INTERNAL_SERVER_ERROR.getCode(),
        exception.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred",
        details);

    return ResponseEntityBuilder.build(err);

  }

  private List<String> buildValidationErrors(
      MethodArgumentNotValidException methodArgumentNotValidException) {
    List<String> details = new ArrayList<String>();
    details = methodArgumentNotValidException.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getDefaultMessage())
        .collect(Collectors.toList());
    return details;
  }

  private List<String> buildUnKnownMediaMessage(
      HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {
    List<String> details = new ArrayList<>();
    StringBuilder builder = new StringBuilder();
    builder.append(httpMediaTypeNotSupportedException.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    httpMediaTypeNotSupportedException.getSupportedMediaTypes()
        .forEach(t -> builder.append(t).append(", "));
    details.add(builder.toString());
    return details;
  }
}
