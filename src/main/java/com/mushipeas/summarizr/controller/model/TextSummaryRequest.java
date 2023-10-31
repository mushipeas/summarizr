package com.mushipeas.summarizr.controller.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

/**
 * Holds the request body for a summarization request
 *
 * @param text     the text to be summarized
 * @param minWords minimum words for the summary
 * @param maxWords maximum words for the summary
 */
public record TextSummaryRequest(
    @NotNull @Length(max = 3000, message = "length must be between {min} and {max} chars") String text,
    @Min(0) @Max(300) int minWords,
    @NotNull @Min(0) @Max(300) Integer maxWords) implements Serializable {}
