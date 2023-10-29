package com.mushipeas.summarizr.domain.summarizetext.adapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 * @param model private final OffsetDateTime created;
 */
public record ChatResponse(String id, String object, String model, Usage usage,
                           List<Choice> choices) implements Serializable {

  public record Choice(int index, Message message,
                       @JsonProperty("finish_reason") String finishReason) implements Serializable {}
}
