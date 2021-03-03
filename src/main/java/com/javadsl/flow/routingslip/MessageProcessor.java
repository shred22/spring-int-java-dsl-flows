package com.javadsl.flow.routingslip;

import com.javadsl.flow.routingslip.config.Flows;
import com.javadsl.flow.routingslip.config.Route;
import com.javadsl.flow.routingslip.config.RoutingConfigProperties;
import com.javadsl.flow.routingslip.model.GenericRequest;
import com.javadsl.flow.routingslip.model.GenericResponse;

import java.util.List;

import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class MessageProcessor {

    private MessagingTemplate template;
    private RoutingConfigProperties routingConfigProperties;

    public MessageProcessor(MessagingTemplate template, RoutingConfigProperties routingConfigProperties) {
        this.template = template;
        this.routingConfigProperties = routingConfigProperties;
    }

    public Message<?> prepareMessage(GenericRequest genericRequest) {
        return MessageBuilder.withPayload(genericRequest).copyHeaders(genericRequest.getFlowDetail().convertToHeaderMap(
                genericRequest.getRouteDetails().getMessageType())).build();
    }

    public Message<?> executeRoutingSlip(Message<?> request) {
        Flows route = findStartingRoute(request);
        ((GenericRequest) request.getPayload()).getRouteDetails().setExecutedRoute(route.getFlow().get(0));
        ((FlowDetail)request.getHeaders().get(((GenericRequest) request.getPayload()).getRouteDetails().getMessageType())).getHeaders().getDomainHeaders().put("executedRoute-"+route.getFlow().get(0).getHandlerClass(), route.getFlow());
        Message<?> firstStepResponse = template.sendAndReceive(route.getFlow().get(0).getChannel(), request);
        findNextStepInCurrentRoute(firstStepResponse, route.getFlow());
        return firstStepResponse;
    }

    private Flows findStartingRoute(Message<?> request) {
        List<Flows> flows = routingConfigProperties.getFlows();
        for (Flows flow : flows) {
            if (flow.getMessageType().contains(((GenericRequest) request.getPayload()).getRouteDetails().getMessageType())) {
                return flow;
            } else {
                throw new RuntimeException("No RoutingSlip found for Incoming message type, no configured route found ..!!");
            }
        }
        return null;
    }

    private Message<?> findNextStepInCurrentRoute(Message<?> message, List<Route> routes) {
        for(int i=1; i < routes.size(); i++) {
            List<Route> flow = ((List<Route>)((GenericResponse) message.getPayload()).getHeader().getDomainHeaders()
                .get("executedRoute-" + routes.get(i - 1).getHandlerClass()));
            Message<?> responseMessage = template.sendAndReceive(flow.get(i).getChannel(), message);

        }
        return message;
    }
}


