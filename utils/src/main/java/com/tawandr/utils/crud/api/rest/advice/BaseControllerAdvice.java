package com.tawandr.utils.crud.api.rest.advice;

import com.tawandr.utils.crud.api.rest.advice.dtos.ApiError;
import com.tawandr.utils.messaging.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class BaseControllerAdvice extends ResponseEntityExceptionHandler  {

  private static final Logger log = LoggerFactory.getLogger(BaseControllerAdvice.class);
  
  @ResponseBody
  @ExceptionHandler({
          SystemException.class
  })
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> handleGeneralError(Exception ex) {
    final String error = "Critical Error: " + ex.getMessage();
    return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
  }

  @ResponseBody
  @ExceptionHandler({
          BusinessException.class,
          ConversionException.class,
          DuplicateRecordException.class,
          NullRequestException.class,
          ValidationException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleBadRequestException(Exception ex) {
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
  }


  @ResponseBody
  @ExceptionHandler({
          RecordNotFoundException.class
  })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleRecordNotFoundException(Exception ex) {
    return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex));
  }

  @ResponseBody
  @ExceptionHandler({
          SecurityValidationException.class
  })
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<Object> handleSecurityValidationException(Exception ex) {
    return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex));
  }

  @ResponseBody
  @ExceptionHandler({
          InvalidStateException.class,
          ConfigurationException.class
  })
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<Object> handleForbiddenException(Exception ex) {
    return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, ex.getMessage(), ex));
  }

  @ResponseBody
  @ExceptionHandler({
          ClientIntegrationException.class
  })
  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  public ResponseEntity<Object> handleConnectionException(Exception ex) {
    return buildResponseEntity(new ApiError(HttpStatus.BAD_GATEWAY, ex.getMessage(), ex));
  }

  protected ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    final String errorMessage = String.format("API Error Occurred with message: %s", apiError.getMessage());
    log.error(errorMessage);
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

}