package com.mushipeas.summarizr.domain.summarizetext.infrastructure;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Configuration
public class WebClientConfig {

  private static final Integer CONNECTION_TIMEOUT = 30000;
  private static final long REQUEST_TIMEOUT = 30000;
  private static final int READ_TIMEOUT = 30;

  @Value("${openai.api-key}")
  private String openAIKey;

  @Bean
  HttpClient httpClient() {
    return HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECTION_TIMEOUT)
        .responseTimeout(Duration.ofMillis(REQUEST_TIMEOUT))
        .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(READ_TIMEOUT)));
  }

  @Bean
  WebClient webClient(HttpClient httpClient) {
    return WebClient.builder()
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openAIKey)
        .filters(exchangeFilterFunctions -> {
          exchangeFilterFunctions.add(logRequest());
          exchangeFilterFunctions.add(logResponse());
        })
        .clientConnector(new ReactorClientHttpConnector(httpClient)).build();
  }

  ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
      return Mono.just(clientRequest);
    });
  }

  ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
      log.info("Response status: {}", clientResponse.statusCode());
      return Mono.just(clientResponse);
    });
  }
}
