package com.javadsl.flow.routingslip;

import com.javadsl.flow.routingslip.config.Route;
import lombok.*;
import org.springframework.stereotype.Component;


@Getter
@Setter
@ToString
@Builder
public class RouteDetails {

    private String outboundEndpointUrl;
    private String httpMethod;
    private String contentType;
    private String messageType;
    private Route executedRoute;

}
