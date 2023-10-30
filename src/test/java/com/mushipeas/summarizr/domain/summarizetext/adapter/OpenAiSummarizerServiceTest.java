package com.mushipeas.summarizr.domain.summarizetext.adapter;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.mushipeas.summarizr.domain.summarizetext.infrastructure.WebClientConfig;
import com.mushipeas.summarizr.support.annotation.WebClientTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebClientTest(classes = {OpenAiSummarizerService.class, WebClientConfig.class})
class OpenAiSummarizerServiceTest {

  @Autowired
  private OpenAiSummarizerService classUnderTest;

  @Test
  void summarize_givenTextAndWordLimits_returnsSummary() {
    stubFor(post(urlEqualTo("/chat")).willReturn(
        aResponse().withHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .withBodyFile("openai/stub/bodies/avanti_train_services_reduced.json")));

    Mono<String> summaryMono = classUnderTest.summarize(
        """
            Train operator Avanti West Coast will be cancelling nearly 20 services\
             every Saturday between London Euston and Manchester Piccadilly until the end of the year.
            It had earlier announced plans to scrap two in five of the trains it runs on the popular \
            route from December 9.
            However the BBC understands the reduced timetable will start immediately.""", 10, 20);

    StepVerifier.create(summaryMono)
        .expectNextMatches(summary -> summary.equals(
            "Avanti West Coast will cancel almost 20 services every Saturday between London and "
                + "Manchester until the end of the year."))
        .verifyComplete();
  }
}