package com.javadsl.flow.service;

import com.javadsl.flow.routingslip.GenericRequest;
import com.javadsl.flow.routingslip.MessageProcessor;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;


public class RoutingFlowService {

  private MessageProcessor messageProcessor;

  public RoutingFlowService(MessageProcessor messageProcessor) {
    this.messageProcessor = messageProcessor;
  }

  @ServiceActivator(inputChannel = "integration.http.req.channel")
  public Message<?> handleHttpRequest(Message<GenericRequest> requestMessage) {
    return messageProcessor.executeRoutingSlip(requestMessage);
  }

}
