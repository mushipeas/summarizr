package com.mushipeas.summarizr.cache;

import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

@ExtendWith(MockitoExtension.class)
class CacheManagerTest {

  @Mock
  private Cache<String, String> cache;

  @InjectMocks
  private CacheManager<String, String> classUnderTest;

  @Test
  void get_givenCacheMiss_requestsFromPublisherAndPopulatesCache() {
    String missedKey = "missedKey";
    String expectedValue = "expectedValue";
    Mockito.when(cache.getIfPresent(missedKey)).thenReturn(null);

    PublisherProbe<String> publisherProbe = PublisherProbe.of(Mono.just(expectedValue));

    Mono<String> actual = classUnderTest.get(missedKey, publisherProbe.mono());

    StepVerifier.create(actual)
        .expectNext(expectedValue)
        .verifyComplete();

    publisherProbe.assertWasRequested();
    Mockito.verify(cache).put(missedKey, expectedValue);
  }

  @Test
  void get_givenCacheHit_returnsFromCacheAndDoesNotRequestFromPublisher() {
    String hitKey = "hitKey";
    String expectedValue = "expectedValue";
    Mockito.when(cache.getIfPresent(hitKey)).thenReturn(expectedValue);

    PublisherProbe<String> publisherProbe = PublisherProbe.empty();

    Mono<String> actual = classUnderTest.get(hitKey, publisherProbe.mono());

    StepVerifier.create(actual)
        .expectNext(expectedValue)
        .verifyComplete();

    publisherProbe.assertWasNotRequested();
    Mockito.verify(cache).getIfPresent(hitKey);
  }
}