package com.javadsl.flow.config;

import com.javadsl.flow.validation.ResponseValidator;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfiguration extends ApplicationBaseConfiguration{

  @Bean
  public Map<String, ResponseValidator> validationConfigDetails() {

    return Map.of("GATEWAY-API.response", (responseMessage) ->
        responseMessage.getHeaders().get("http_statusCode").toString().contains("200"));
  }
}
