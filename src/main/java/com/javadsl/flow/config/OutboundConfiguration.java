package com.javadsl.flow.config;


import com.javadsl.flow.routingslip.RouteDetails;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OutboundConfiguration extends ApplicationBaseConfiguration {

    @Bean
    public Map<String, RouteDetails> outboundConfigDetails() {

        return  Map.of("GATEWAY-API.OutboundRequest", RouteDetails.builder().contentType("application/json")
        .httpMethod("POST").outboundEndpointUrl("http://localhost:8088/spboot/multimodule/test").build(),
                "GATEWAY-API.InternalRequest", RouteDetails.builder().contentType("application/json")
                        .httpMethod("POST").outboundEndpointUrl("http://localhost:8088/spboot/multimodule/test").build());
    }

}
