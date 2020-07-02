package com.javadsl.flow.routingslip.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutboundDetails {

    private String outboundEndpointUrl;
    private String httpMethod;
    private String contentType;
    private String messageType;

}
