package com.mushipeas.summarizr.domain.summarizetext.adapter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public record Usage(@JsonProperty("prompt_tokens") int promptTokens,
                    @JsonProperty("completion_tokens") int completionTokens,
                    @JsonProperty("total_tokens") int totalTokens) implements Serializable {

}
