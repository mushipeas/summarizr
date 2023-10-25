package com.mushipeas.summarizr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SummarizerTest {

  private final Summarizer classUnderTest = new Summarizer();

  @Test
  void summarize_shouldSummarizeText() {
    String summary = classUnderTest.summarize("text to summarize");
    assertThat(summary)
        .isEqualTo("summary");
  }
}