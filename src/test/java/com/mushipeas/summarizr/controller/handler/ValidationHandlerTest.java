package com.mushipeas.summarizr.controller.handler;

import com.mushipeas.summarizr.controller.handler.ValidationHandlerTest.TestController;
import com.mushipeas.summarizr.controller.handler.ValidationHandlerTest.TestController.TestClass;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@WebFluxTest(
    controllers = TestController.class,
    excludeAutoConfiguration = {SecurityAutoConfiguration.class, ReactiveSecurityAutoConfiguration.class},
    properties = {"spring.security.enabled=false"}
)
@Import({TestController.class})
class ValidationHandlerTest {

  @Autowired
  private WebTestClient webClient;

  @RestController
  @Validated
  static class TestController {

    @PostMapping("/validationExceptionTest/")
    Mono<Void> testValidationException(@Valid @RequestBody TestClass body) {
      return Mono.empty();
    }

    record TestClass(@NotNull String testField) {}
  }

  @Test
  void handleException_givenBindException_willReturnBadRequestWithErrorMessages() {
    webClient.post()
        .uri("/validationExceptionTest/")
        .bodyValue(new TestClass(null))
        .exchange()
        .expectAll(
            responseSpec -> responseSpec.expectStatus().isBadRequest(),
            responseSpec -> responseSpec.expectBody(String.class)
                .isEqualTo("[\"testField: must not be null\"]")
        );
  }
}