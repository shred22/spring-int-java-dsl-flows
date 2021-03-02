package com.javadsl.flow.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;

@Configuration
public abstract class ApplicationBaseConfiguration {

  @Bean("integration.http.req.channel")
  public DirectChannel httpRequestChannel() {
    return new DirectChannel();
  }

  @Bean("integration.http.resp.channel")
  public DirectChannel httpResponseChannel() {
    return new DirectChannel();
  }

  @Bean("outboundChannel")
  public DirectChannel outboundChannel() {
    return new DirectChannel();
  }

  @Bean("response.transform.channel")
  public DirectChannel responseTransformChannel() {
    return new DirectChannel();
  }

  @Bean
  public MessagingTemplate messagingTemplate() {
    return new MessagingTemplate();
  }

  @Bean
  public SimpleClientHttpRequestFactory requestFactory() {
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(2000);
    requestFactory.setReadTimeout(4000);
    return requestFactory;
  }

  @Bean
  public ObjectMapper jsonMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules();
    return objectMapper;
  }

}
