package com.mushipeas.summarizr.controller.model;

import java.io.Serializable;

/**
 * Holds the generated summary
 *
 * @param summary The summary to return to the user
 */
public record TextSummaryResponse(String summary) implements Serializable {}
