package com.javadsl.flow.routingslip;

import com.definition.api.model.JsonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadsl.flow.routingslip.config.EndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GenericConfigurableIntegrationFlow {

    private String OUTBOUND_CHANNEL = "outboundChannel";
    @Autowired
    private EndpointConfig endpointConfig;
    @Autowired
    private SimpleClientHttpRequestFactory requestFactory;
    @Autowired
    private ObjectMapper jsonMapper;

    private GenericTransformer<String, JsonResponse> responseTransformer = (String m) -> {

        try {
            return jsonMapper.readValue(m, JsonResponse.class);            // jsonMapper.readTree((String)m.getPayload()).;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    };



    @Bean
    public IntegrationFlow configureGenericFlow() {
        return IntegrationFlows.from(OUTBOUND_CHANNEL)
                .enrichHeaders(Map.of("Content-Type", "application/json"))
                .handle(Http.outboundGateway(m -> {
                    System.out.println("In Flow Now : "+((GenericRequest) m.getPayload()).getRouteDetails().getOutboundEndpointUrl());
                     return ((GenericRequest) m.getPayload()).getRouteDetails().getOutboundEndpointUrl();})
                        .httpMethodFunction(m -> ((GenericRequest) m.getPayload()).getRouteDetails().getHttpMethod())
                        .requestFactory(requestFactory)
                        .mappedRequestHeaders("*")
                        .extractPayload(true)
                        .expectedResponseType(String.class))
                .channel("response.handler.channel")
                        .get();

    }

    @Transformer(inputChannel = "response.handler.channel")
    public void responseTransformer(Message<?> responseMessage) {
        responseMessage.getHeaders();
    }
}
