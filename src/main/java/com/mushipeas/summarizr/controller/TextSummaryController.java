package com.mushipeas.summarizr.controller;

import com.mushipeas.summarizr.controller.model.TextSummaryRequest;
import com.mushipeas.summarizr.controller.model.TextSummaryResponse;
import com.mushipeas.summarizr.domain.summarizetext.SummarizeText;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/summarize")
class TextSummaryController {

  private final SummarizeText summarizeText;

  @Secured("ROLE_USER")
  @PostMapping("/text")
  public Mono<TextSummaryResponse> getTextSummary(@Valid @RequestBody TextSummaryRequest request) {
    return summarizeText.summarize(request.text(), request.minWords(), request.maxWords())
        .map(TextSummaryResponse::new);
  }
}