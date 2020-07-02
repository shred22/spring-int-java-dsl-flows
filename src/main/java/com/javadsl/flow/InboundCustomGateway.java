package com.javadsl.flow;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway(name = "customInboundGateway", defaultRequestChannel= "integration.http.req.channel")
public interface InboundCustomGateway {

    @Gateway
    Message<?> customRestGateway(@Payload Message<?> message);
}
