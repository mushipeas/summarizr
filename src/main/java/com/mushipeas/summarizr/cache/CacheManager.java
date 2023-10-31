package com.mushipeas.summarizr.cache;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Caching service for responses from a publisher of {@link V}.
 * The {@link #get(K, Mono)} method intercepts request to the publisher (Mono) if there is no cached value
 * to populate the cache for following requests.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value to cache
 */
@Component
@RequiredArgsConstructor
public final class CacheManager<K, V> {

  private final Cache<K, V> cache;

  /**
   * Emits the result from the cache, if present, otherwise subscribes to the publisher
   *
   * @param key       the immutable key to fetch for
   * @param publisher alternative publisher if the key is not present
   * @return value from the cache, if present, otherwise from the publisher
   */
  public Mono<V> get(K key, Mono<V> publisher) {
    return Mono.justOrEmpty(cache.getIfPresent(key))
        .switchIfEmpty(Mono.defer(() -> publisher.flatMap(it -> this.put(key, it))));
  }

  private Mono<V> put(K key, V object) {
    cache.put(key, object);
    return Mono.just(object);
  }
}