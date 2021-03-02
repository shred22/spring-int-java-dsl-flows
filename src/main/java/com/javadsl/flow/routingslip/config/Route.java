package com.javadsl.flow.routingslip.config;

import lombok.*;

/**
 *
 * /**routing:
 *     flows:
 *        -
 *           messageType: GATEWAY-API
 *           handlerClass: OutboundRequest
 *           channel: outboundChannel
 *           index: 0
 *        -
 *           messageType: GATEWAY-API
 *           handlerClass: InternalRequest
 *           channel: outboundChannel
 *           index: 1
 *  */


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Route {

    private String handlerClass;
    private String channel;
    private int index;
}
