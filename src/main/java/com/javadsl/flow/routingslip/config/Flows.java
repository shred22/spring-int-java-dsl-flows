package com.javadsl.flow.routingslip.config;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 *
 * /**routing:
 *   flows:
 *     - messageType: 'GATEWAY-API'
 *       routes:
 *         - handlerClass: 'OutboundRequest'
 *           channel: 'outboundChannel'
 *           index: 0
 *
 *         - handlerClass: 'InternalRequest'
 *           channel: 'outboundChannel'
 *           index: 1
 *     - messageType: 'GATEWAY-API-2'
 *         routes:
 *           - handlerClass: 'OutboundRequest'
 *             channel: 'outboundChannel-2'
 *             index: 0
 *
 *           - handlerClass: 'InternalRequest'
 *             channel: 'outboundChannel-2'
 *             index: 1
 *  */

@Service
@ConfigurationProperties(prefix = "routing")
@Setter
@Getter
@Validated
@ToString
public class Flows {


    private String messageType;
    private List<Route> flow;

}
