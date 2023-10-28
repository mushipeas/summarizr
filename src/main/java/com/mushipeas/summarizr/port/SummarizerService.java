package com.mushipeas.summarizr.port;

import reactor.core.publisher.Mono;

public interface SummarizerService {

  Mono<String> summarize(String input, int minWords, int maxWords);
}
