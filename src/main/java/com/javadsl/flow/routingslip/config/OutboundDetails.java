package com.javadsl.flow.routingslip.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OutboundDetails {

    private String outboundEndpointUrl;
    private String httpMethod;
    private String contentType;
    private String messageType;

}
