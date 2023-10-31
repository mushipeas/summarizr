package com.mushipeas.summarizr.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CacheConfig {

  @Bean
  <K, V> Cache<K, V> getCache() {
    return Caffeine.newBuilder()
        .expireAfterAccess(Duration.ofHours(24L))
        .maximumSize(10000L)
        .evictionListener((key, item, cause) -> log.info(this.getClass().getName().concat(": key {} was evicted "), key))
        .removalListener((key, item, cause) -> log.info(this.getClass().getName().concat(": Key {} was removed "), key))
        .scheduler(Scheduler.systemScheduler())
        .build();
  }
}
