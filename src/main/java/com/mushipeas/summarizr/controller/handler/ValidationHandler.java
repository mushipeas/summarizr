package com.mushipeas.summarizr.controller.handler;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
class ValidationHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<List<String>> handleException(WebExchangeBindException e) {
    var errors = e.getBindingResult()
        .getAllErrors()
        .stream()
        .map(ValidationHandler::parseValidationErrorMessage)
        .collect(Collectors.toList());
    return ResponseEntity.badRequest().body(errors);
  }

  private static String parseValidationErrorMessage(ObjectError objectError) {
    return "%s: %s".formatted(((FieldError) objectError).getField(), objectError.getDefaultMessage());
  }
}

