package com.mushipeas.summarizr.domain.summarizetext;

import com.mushipeas.summarizr.domain.summarizetext.port.SummarizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public final class SummarizeText {

  private final SummarizerService summarizerService;

  public Mono<String> summarize(String text, int minWords, int maxWords) {
    return summarizerService.summarize(text, minWords, maxWords);
  }
}
