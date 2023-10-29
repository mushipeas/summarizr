package com.mushipeas.summarizr.domain.summarizetext;

import com.mushipeas.summarizr.domain.summarizetext.SummarizeText;
import com.mushipeas.summarizr.domain.summarizetext.port.SummarizerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SummarizeTextTest {

  @Mock
  private SummarizerService summarizerService;

  @InjectMocks
  private SummarizeText classUnderTest;

  @Test
  void summarize_whenSummaryIsRestrictedToOneWord_shouldSummarizeText() {
    Mockito.when(summarizerService.summarize("text to summarize", 1, 1))
        .thenReturn(Mono.just("summary"));

    Mono<String> summary = classUnderTest.summarize("text to summarize", 1, 1);

    StepVerifier.create(summary)
        .expectNext("summary")
        .verifyComplete();
  }

  @Test
  void summarize_whenSummaryIsRestrictedToTwoWords_shouldSummarizeText() {
    Mockito.when(summarizerService.summarize("text to summarize", 2, 2))
        .thenReturn(Mono.just("summarized text"));

    Mono<String> summary = classUnderTest.summarize("text to summarize", 2, 2);

    StepVerifier.create(summary)
        .expectNext("summarized text")
        .verifyComplete();
  }
}