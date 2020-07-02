package com.javadsl.flow.routingslip;

import com.javadsl.flow.routingslip.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageProcessor {

    @Autowired
    private MessagingTemplate template;

    @Autowired
    private RoutingConfig routingConfig;

    public Message<?> prepareMessage(GenericRequest genericRequest) {
        return MessageBuilder.withPayload(genericRequest).copyHeaders(genericRequest.getFlowDetail().convertToHeaderMap(
                genericRequest.getRouteDetails().getMessageType())).build();
    }

    public Message<?> executeRoutingSlip(Message<?> request) {
        Flows route = findStartingRoute(request);
        ((FlowDetail)request.getHeaders().get(((GenericRequest) request.getPayload()).getRouteDetails().getMessageType())).getHeaders().getDomainHeaders().put("ExecutedRoute", route);
        Message<?> outboundChannel = template.sendAndReceive(route.getFlow().get(0).getChannel(), request);
        return outboundChannel;
    }

    private Flows findStartingRoute(Message<?> request) {

        List<Flows> flows = routingConfig.getFlows();

        for (Flows flow : flows) {
            if (flow.getMessageType().contains(((GenericRequest) request.getPayload()).getRouteDetails().getMessageType())) {
                return flow;
            } else {
                throw new RuntimeException("No RoutingSlip found for Incoming message type, no configured route found ..!!");
            }
        }
        return null;
    }

    private Route findNextRoute(Message<?> responseMessage, Route route) {


        return null;
    }
}


