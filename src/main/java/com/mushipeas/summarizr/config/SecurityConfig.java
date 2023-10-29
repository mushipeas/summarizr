package com.mushipeas.summarizr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@ConditionalOnProperty(prefix = "spring.security", name = "enabled", havingValue = "true")
public class SecurityConfig {

  @Value("${spring.security.user.password}")
  private String userPassword;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.csrf(CsrfSpec::disable);
    http.authorizeExchange(
        authorizeExchangeSpec -> authorizeExchangeSpec.anyExchange().authenticated());
    http.httpBasic(Customizer.withDefaults());

    return http.build();
  }
}