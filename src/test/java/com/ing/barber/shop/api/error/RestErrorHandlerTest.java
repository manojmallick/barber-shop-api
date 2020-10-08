package com.ing.barber.shop.api.error;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import com.ing.barber.shop.api.util.BarberShopApiConstants;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mapping.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RunWith(MockitoJUnitRunner.class)
public class RestErrorHandlerTest {

    @Mock
    WebRequest webRequest;
    @Mock
    BindingResult bindingResult;
    @InjectMocks
    GenericExceptionHandler restErrorHandler;

    @Test
    public void handleAllExceptions() {
        Exception exception = new Exception("Error");
        ResponseEntity<Object> responseEntity = restErrorHandler.handleAll(exception,webRequest);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void handleGenericApiException() {
        GenericApiException exception = new GenericApiException(
            BarberShopApiConstants.START_TIME_IS_INVALID,
            HttpStatus.BAD_REQUEST,
            ErrorCodes.ERROR_BOOKING_TIME_NOT_VALID);
        ResponseEntity<Object> responseEntity = restErrorHandler.handleGenericApiException(exception,webRequest);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void handleNoHandlerFoundException() {
        NoHandlerFoundException exception = new NoHandlerFoundException("get","test",null);
        ResponseEntity<Object> responseEntity = restErrorHandler.handleNoHandlerFoundException(exception,null,HttpStatus.BAD_REQUEST,webRequest);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void handleMappingException() {
        MappingException exception = new MappingException("mapping failed");
        ResponseEntity<Object> responseEntity = restErrorHandler.handleMappingException(exception);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
    @Test
    public void handleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("mapping failed");
        ResponseEntity<Object> responseEntity = restErrorHandler.handleIllegalArgumentException(exception);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
    @Test
    public void handleResourceAlreadyExists() {
        ResourceAlreadyExists exception = new ResourceAlreadyExists("mapping failed",ErrorCodes.ERROR_DUPLICATE_BOOKING_BARBER,null);
        ResponseEntity<Object> responseEntity = restErrorHandler.handleResourceAlreadyExists(exception);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CONFLICT));
    }
    @Test
    public void handleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("mapping failed");
        ResponseEntity<Object> responseEntity = restErrorHandler.handleResourceNotFoundException(exception);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void handleMethodArgumentTypeMismatch() {
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException("name",null,null,null,null);
        ResponseEntity<Object> responseEntity = restErrorHandler.handleMethodArgumentTypeMismatch(exception,null);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void handleMissingServletRequestParameter() {
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException("name","string");
        ResponseEntity<Object> responseEntity = restErrorHandler.handleMissingServletRequestParameter(exception,null,HttpStatus.BAD_REQUEST,webRequest);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void handleMethodArgumentNotValid() {

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null,bindingResult);
        ResponseEntity<Object> responseEntity = restErrorHandler.handleMethodArgumentNotValid(exception,null,HttpStatus.BAD_REQUEST,webRequest);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void handleHttpMessageNotReadable() {

        HttpMessageNotReadableException exception = new HttpMessageNotReadableException(null,null,null);
        ResponseEntity<Object> responseEntity = restErrorHandler.handleHttpMessageNotReadable(exception,null,HttpStatus.BAD_REQUEST,webRequest);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void handleHttpMediaTypeNotSupported() {

        HttpMediaTypeNotSupportedException exception = new HttpMediaTypeNotSupportedException(
            MediaType.ALL_VALUE);
        ResponseEntity<Object> responseEntity = restErrorHandler.handleHttpMediaTypeNotSupported(exception,null,HttpStatus.BAD_REQUEST,webRequest);
        assertNotNull(responseEntity);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}