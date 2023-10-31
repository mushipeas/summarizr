package com.mushipeas.summarizr.controller.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TextSummaryRequestTest {

  private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private final Validator validator = factory.getValidator();

  @ParameterizedTest
  @MethodSource("textInvalidValueSupplier")
  void text_InvalidValue_throwsError(String value, String errorMessage) {
    Set<ConstraintViolation<TextSummaryRequest>> constraintViolations = validator.validate(
        new TextSummaryRequest(value, 100, 200));

    assertThat(constraintViolations)
        .extracting(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage)
        .contains(tuple(path("text"), errorMessage));
  }

  @ParameterizedTest
  @MethodSource("minWordsInvalidValueSupplier")
  void minWords_givenInvalidValue_throwsError(Integer value, String errorMessage) {
    Set<ConstraintViolation<TextSummaryRequest>> constraintViolations = validator.validate(
        new TextSummaryRequest("text", value, 200));

    assertThat(constraintViolations)
        .extracting(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage)
        .contains(tuple(path("minWords"), errorMessage));
  }

  @ParameterizedTest
  @MethodSource("maxWordsInvalidValueSupplier")
  void maxWords_givenInvalidValue_throwsError(Integer value, String errorMessage) {
    Set<ConstraintViolation<TextSummaryRequest>> constraintViolations = validator.validate(
        new TextSummaryRequest("text", 200, value));

    assertThat(constraintViolations)
        .extracting(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage)
        .contains(tuple(path("maxWords"), errorMessage));
  }

  static Stream<Arguments> textInvalidValueSupplier() {
    return Stream.of(
        Arguments.of(null, "must not be null"),
        Arguments.of("a".repeat(3001), "length must be between 0 and 3000 chars")
    );
  }

  static Stream<Arguments> minWordsInvalidValueSupplier() {
    return Stream.of(
        Arguments.of(-1, "must be greater than or equal to 0"),
        Arguments.of(301, "must be less than or equal to 300")
    );
  }

  static Stream<Arguments> maxWordsInvalidValueSupplier() {
    return Stream.of(
        Arguments.of(null, "must not be null"),
        Arguments.of(-1, "must be greater than or equal to 0"),
        Arguments.of(301, "must be less than or equal to 300")
    );
  }

  private static PathImpl path(String field) {
    return PathImpl.createPathFromString(field);
  }
}