package com.javadsl.flow.routingslip;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadsl.flow.config.ValidationConfiguration;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static com.javadsl.flow.routingslip.GenericResponse.GenericResponseBuilder.builder;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class GenericConfigurableIntegrationFlow {

  private final String OUTBOUND_CHANNEL = "outboundChannel";
  private final SimpleClientHttpRequestFactory requestFactory;
  private final ObjectMapper jsonMapper;
  private final ValidationConfiguration validationConfiguration;


  public GenericConfigurableIntegrationFlow(SimpleClientHttpRequestFactory requestFactory,
      ObjectMapper jsonMapper,
      ValidationConfiguration validationConfiguration) {
    this.requestFactory = requestFactory;
    this.jsonMapper = jsonMapper;
    this.validationConfiguration = validationConfiguration;
  }

  @Bean
  public IntegrationFlow configureGenericFlow() {
    return IntegrationFlows.from(OUTBOUND_CHANNEL)
        .enrichHeaders(Map.of("Content-Type", "application/json"))
        .enrich(enricherSpec -> enricherSpec.headerExpression("executedRequestMessage", "payload"))
        .handle(Http.outboundGateway(m -> ((GenericRequest) m.getPayload()).getRouteDetails().getOutboundEndpointUrl())
            .httpMethodFunction(m -> ((GenericRequest) m.getPayload()).getRouteDetails().getHttpMethod())
            .requestFactory(requestFactory)
            .mappedRequestHeaders("*")
            .mappedResponseHeaders("*")
            .extractPayload(true)
            .expectedResponseType(String.class))
        .channel("response.handler.channel")
        .get();
  }

  @Transformer(inputChannel = "response.handler.channel", outputChannel = "response.validation.channel")
  public Message<?> responseHandler(Message<?> responseMessage) {

    GenericRequest executedRequestMessage = (GenericRequest) responseMessage.getHeaders().get("executedRequestMessage");

    Map<String, Object> responseHeaders = responseMessage.getHeaders().entrySet()
        .stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    assert executedRequestMessage != null;
    executedRequestMessage.getFlowDetail().getHeaders().getDomainHeaders().put(
        executedRequestMessage.getRouteDetails().getExecutedRoute().getHandlerClass(),
        Pair.of(executedRequestMessage, builder()
            .withHttpResponse(responseMessage.getPayload())
            .withResponseHeaders(responseHeaders)
            .build()));

    return MessageBuilder.withPayload(GenericMessage.builder().genericRequest(executedRequestMessage)
    .genericResponse(GenericResponse.GenericResponseBuilder.builder()
        .withHttpResponse(responseMessage.getPayload())
        .withResponseHeaders(responseHeaders)
        .build())).build();
  }

  @Transformer(inputChannel = "response.validation.channel")
  public Message<?> responseTransformer(Message<GenericMessage> responseMessage) {
    //((Route)responseMessage.getPayload().getGenericRequest().getFlowDetail().getHeaders().getDomainHeaders().get(executedRequestMessage.getRouteDetails().getExecutedRoute().getHandlerClass())).getHandlerClass()
    return  responseMessage;
  }

  /*@ServiceActivator(inputChannel = "response.handler.channel")
  public Message<?> handleHttpResponse(Message<GenericRequest> requestMessage) {
    System.out.println("Validation Channel .!!!!!!!!!!");
    return requestMessage;

  }

  @ServiceActivator(inputChannel = "response.transformer.channel")
  public Message<?> transformResponseToHttpRequest(Message<GenericRequest> requestMessage) {
    System.out.println("Validation Channel .!!!!!!!!!!");
    return requestMessage;

  }*/
}
