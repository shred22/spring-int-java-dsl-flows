package com.javadsl.flow.routingslip.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadsl.flow.routingslip.GenericRequest;
import com.javadsl.flow.routingslip.MessageProcessor;
import com.javadsl.flow.routingslip.RouteDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;

import java.util.Map;

@Configuration
public class OutboundConfig {

    @Autowired
    private MessageProcessor messageProcessor;
    @Autowired
    private EndpointConfig endpointConfig;


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
    public Map<String, RouteDetails> outboundConfigDetails() {

        return  Map.of("GATEWAY-API.OutboundRequest", RouteDetails.builder().contentType("application/json")
        .httpMethod("POST").outboundEndpointUrl("http://localhost:8088/spboot/multimodule/test").build(),
                "GATEWAY-API.InternalRequest", RouteDetails.builder().contentType("application/json")
                        .httpMethod("POST").outboundEndpointUrl(null).build());
    }

    @ServiceActivator(inputChannel = "integration.http.req.channel")
    public Message<?> handleHttpRequest(Message<GenericRequest> requestMessage) {
        return messageProcessor.executeRoutingSlip(requestMessage);

    }

}
