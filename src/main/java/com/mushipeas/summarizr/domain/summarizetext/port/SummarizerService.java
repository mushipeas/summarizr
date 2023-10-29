package com.mushipeas.summarizr.domain.summarizetext.port;

import reactor.core.publisher.Mono;

public interface SummarizerService {

  Mono<String> summarize(String input, int minWords, int maxWords);
}
