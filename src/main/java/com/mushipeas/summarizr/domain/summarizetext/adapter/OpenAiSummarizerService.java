package com.mushipeas.summarizr.domain.summarizetext.adapter;

import com.mushipeas.summarizr.domain.summarizetext.adapter.model.ChatRequest;
import com.mushipeas.summarizr.domain.summarizetext.adapter.model.ChatResponse;
import com.mushipeas.summarizr.domain.summarizetext.adapter.model.ChatResponse.Choice;
import com.mushipeas.summarizr.domain.summarizetext.adapter.model.Message;
import com.mushipeas.summarizr.domain.summarizetext.adapter.model.Usage;
import com.mushipeas.summarizr.domain.summarizetext.port.SummarizerService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OpenAiSummarizerService implements SummarizerService {

  private final WebClient client;

  private static final String OPEN_API_CHAT_URL = "https://api.openai.com/v1/chat/completions";

  @Override
  @RegisterReflectionForBinding(
      {ChatRequest.class, ChatResponse.class, Message.class, ChatResponse.Choice.class, Usage.class}
  )
  public Mono<String> summarize(String input, int minWords, int maxWords) {
    ChatRequest chatRequest = ChatRequest.createSummaryRequest(input, minWords, maxWords);

    return getSummary(chatRequest).map(ChatResponse::choices)
        .map(OpenAiSummarizerService::getSummaryFromChoices);
  }

  private Mono<ChatResponse> getSummary(ChatRequest request) {
    return client.post()
        .uri(OPEN_API_CHAT_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .bodyToMono(ChatResponse.class);
  }

  private static String getSummaryFromChoices(List<Choice> choices) {
    return choices.stream()
        .filter(choice -> choice.index() == 0)
        .filter(choice -> Objects.equals(choice.finishReason(), "stop"))
        .map(Choice::message)
        .map(Message::content)
        .findFirst()
        .orElseThrow();
  }
}
