package com.mushipeas.summarizr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public record ChatRequest (String model,
                           List<Message> messages,
                           int n,
                           @JsonProperty("max_tokens") int maxTokens,
                           double temperature) implements Serializable {

  private static final String GPT_3_5_TURBO = "gpt-3.5-turbo";

  public static ChatRequest createSummaryRequest(String testToSummarize, int minWords, int maxWords) {
    List<Message> messages = List.of(
        new Message("system",
            "Summarize content you are provided to between %s and %s words".formatted(minWords, maxWords)),
        new Message("user", testToSummarize)
    );

    return new ChatRequest(GPT_3_5_TURBO ,messages,1,400,0.1);
  }
}
