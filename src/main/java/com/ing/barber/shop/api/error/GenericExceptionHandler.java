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

@ControllerAdvice
@Slf4j
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {


  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      final HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    List<String> details = buildUnKnownMediaMessage(ex);
    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_UNKNOWN_MEDIA_TYPE.getCode(),
        ex.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST, "Invalid Media Type", details);
    return ResponseEntityBuilder.build(err);

  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<String> details = new ArrayList<>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(),
        ErrorCodes.ERROR_NOT_READABLE_REQUEST.getCode(), ex.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST,
        "Malformed request", details);

    return ResponseEntityBuilder.build(err);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<String> details = buildValidationErrors(ex);

    ApiError err = new ApiError(LocalDateTime.now(),
        ErrorCodes.ERROR_INVALID_METHOD_ARGUMENT.getCode(), ex.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST,
        "Validation Errors",
        details);

    return ResponseEntityBuilder.build(err);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getParameterName() + " parameter is missing");

    ApiError err = new ApiError(LocalDateTime.now(),
        ErrorCodes.ERROR_MISSING_PARAMETERS.getCode(), ex.getClass().getSimpleName(),
        HttpStatus.BAD_REQUEST, "Missing Parameters", details);

    return ResponseEntityBuilder.build(err);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex,
      WebRequest request) {
    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_TYPE_MISMATCH.getCode(),
        ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, "Mismatch Type", details);

    return ResponseEntityBuilder.build(err);
  }


  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_NOT_FOUND.getCode(),
        ex.getClass().getSimpleName(), HttpStatus.NOT_FOUND, "Resource Not Found",
        details);

    return ResponseEntityBuilder.build(err);
  }

  //TODO: to be added GenericApiException

  @ExceptionHandler({ResourceAlreadyExists.class})
  public ResponseEntity<Object> handleResourceAlreadyExists(ResourceAlreadyExists ex) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_RESOURCE_EXIST.getCode(),
        ex.getClass().getSimpleName(), HttpStatus.CONFLICT, "Resource exists",
        details);

    return ResponseEntityBuilder.build(err);
  }


  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_RESOURCE_EXIST.getCode(),
        ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, "Resource exists",
        details);

    return ResponseEntityBuilder.build(err);
  }

  @ExceptionHandler({MappingException.class})
  public ResponseEntity<Object> handleMappingException(MappingException ex) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_RESOURCE_EXIST.getCode(),
        ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, "mapping exception",
        details);

    return ResponseEntityBuilder.build(err);
  }


  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    log.debug("No Handler Found Exception. [url={}, exception={}]",
        ((ServletWebRequest) request).getRequest().getRequestURI().toString(), ex);
    List<String> details = new ArrayList<String>();
    details.add(String
        .format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_NO_HANDLER.getCode(),
        ex.getClass().getSimpleName(), HttpStatus.BAD_REQUEST, "Method Not Found",
        details);

    return ResponseEntityBuilder.build(err);

  }

  @ExceptionHandler({GenericApiException.class})
  public ResponseEntity<Object> handleGenericApiException(GenericApiException ex,
      WebRequest request) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(), ErrorCodes.ERROR_RESOURCE_EXIST.getCode(),
        ex.getClass().getSimpleName(), ex.getHttpStatus(), ex.getMessage(),
        details);

    return ResponseEntityBuilder.build(err);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

    log.error("Request to REST API failed. [url={}, exception={}]",
        ((ServletWebRequest) request).getRequest().getRequestURI().toString(), ex);
    List<String> details = new ArrayList<String>();

    ApiError err = new ApiError(LocalDateTime.now(),
        ErrorCodes.ERROR_INTERNAL_SERVER_ERROR.getCode(),
        ex.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred",
        details);

    return ResponseEntityBuilder.build(err);

  }

  private List<String> buildValidationErrors(MethodArgumentNotValidException ex) {
    List<String> details = new ArrayList<String>();
    details = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getDefaultMessage())
        .collect(Collectors.toList());
    return details;
  }

  private List<String> buildUnKnownMediaMessage(HttpMediaTypeNotSupportedException ex) {
    List<String> details = new ArrayList<>();
    StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
    details.add(builder.toString());
    return details;
  }
}
