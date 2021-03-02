package com.javadsl.flow.config;

import com.javadsl.flow.routingslip.MessageProcessor;
import com.javadsl.flow.routingslip.config.RoutingConfigProperties;
import com.javadsl.flow.service.RoutingFlowService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessagingTemplate;

@Configuration
public class RoutingConfiguration extends ApplicationBaseConfiguration {

  @Bean
  public MessageProcessor messageProcessor(MessagingTemplate messagingTemplate, RoutingConfigProperties configProperties) {
    return new MessageProcessor(messagingTemplate, configProperties);
  }

  @Bean
  public RoutingFlowService routingFlowService(MessageProcessor messageProcessor) {
    return new RoutingFlowService(messageProcessor);
  }
}
