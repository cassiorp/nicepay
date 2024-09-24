package com.cassio.nicepay.controller.exception;

import com.cassio.nicepay.controller.exception.ApiExceptionSchema;
import com.cassio.nicepay.exception.DocumentAlreadyExistsException;
import com.cassio.nicepay.exception.EmailAlreadyExistsException;
import com.cassio.nicepay.exception.ForbiddenException;
import com.cassio.nicepay.exception.InsufficientBalanceException;
import com.cassio.nicepay.exception.UserNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ValidationAdvice {

  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiExceptionSchema methodArgumentNotValidException(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    String errorMessage = fieldErrors.get(0).getDefaultMessage();
    String field = fieldErrors.get(0).getField();
    String message = field + " " + errorMessage;
    return new ApiExceptionSchema(message, 400, BAD_REQUEST.getReasonPhrase(), ZonedDateTime.now());
  }

  @ResponseStatus(UNPROCESSABLE_ENTITY)
  @ResponseBody
  @ExceptionHandler(DocumentAlreadyExistsException.class)
  public ApiExceptionSchema handleDocumentAlreadyExistsException(DocumentAlreadyExistsException ex) {
    return new ApiExceptionSchema(
        ex.getMessage(),
        UNPROCESSABLE_ENTITY.value(),
        UNPROCESSABLE_ENTITY.getReasonPhrase(),
        ZonedDateTime.now()
    );
  }

  @ResponseStatus(UNPROCESSABLE_ENTITY)
  @ResponseBody
  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ApiExceptionSchema handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
    return new ApiExceptionSchema(
        ex.getMessage(),
        UNPROCESSABLE_ENTITY.value(),
        UNPROCESSABLE_ENTITY.getReasonPhrase(),
        ZonedDateTime.now()
    );
  }

  @ResponseStatus(UNPROCESSABLE_ENTITY)
  @ResponseBody
  @ExceptionHandler(InsufficientBalanceException.class)
  public ApiExceptionSchema handleInsufficientBalanceException(InsufficientBalanceException ex) {
    return new ApiExceptionSchema(
        ex.getMessage(),
        UNPROCESSABLE_ENTITY.value(),
        UNPROCESSABLE_ENTITY.getReasonPhrase(),
        ZonedDateTime.now()
    );
  }

  @ResponseStatus(NOT_FOUND)
  @ResponseBody
  @ExceptionHandler(UserNotFoundException.class)
  public ApiExceptionSchema handleUserNotFoundException(UserNotFoundException ex) {
    return new ApiExceptionSchema(
        ex.getMessage(),
        NOT_FOUND.value(),
        NOT_FOUND.getReasonPhrase(),
        ZonedDateTime.now()
    );
  }

  @ResponseStatus(FORBIDDEN)
  @ResponseBody
  @ExceptionHandler(ForbiddenException.class)
  public ApiExceptionSchema handleForbiddenException(ForbiddenException ex) {
    return new ApiExceptionSchema(
        "Access Denied",
        FORBIDDEN.value(),
        FORBIDDEN.getReasonPhrase(),
        ZonedDateTime.now()
    );
  }

}