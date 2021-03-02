package com.javadsl.flow.routingslip;

import com.javadsl.flow.routingslip.config.Route;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


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
    private List<String> validationKeys;

}
