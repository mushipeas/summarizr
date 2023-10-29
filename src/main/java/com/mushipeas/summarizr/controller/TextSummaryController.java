package com.mushipeas.summarizr.controller;

import com.mushipeas.summarizr.domain.Summarizer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/summarize")
public final class TextSummaryController {

  private final Summarizer summarizer;

  @PostMapping("/text")
  public Mono<TextSummaryResponse> getTextSummary(@RequestBody TextSummaryRequest request) {
    return summarizer
        .summarize(request.text, request.minWords, request.maxWords)
        .map(TextSummaryResponse::new);
  }

  public record TextSummaryRequest(String text, int minWords, int maxWords) {};

  public record TextSummaryResponse(String summary) {};
}
