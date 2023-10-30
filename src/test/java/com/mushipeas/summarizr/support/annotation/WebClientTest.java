package com.mushipeas.summarizr.support.annotation;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.mushipeas.summarizr.domain.summarizetext.infrastructure.WebClientConfig;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Meta annotation for testing WebClient calls.
 * <p>
 * {@link #classes()} must contain {@link WebClientConfig} at minimum to enable proper functionality.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@WireMockTest(httpPort = 40213)
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "openai.chat.url=http://localhost:40213/chat")
@ContextConfiguration
public @interface WebClientTest {

  @AliasFor(annotation = ContextConfiguration.class, attribute = "classes")
  Class<?>[] classes() default {WebClientConfig.class};
}
