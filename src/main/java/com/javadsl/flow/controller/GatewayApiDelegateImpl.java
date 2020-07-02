package com.javadsl.flow.controller;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.model.SimpleRequest;
import com.atlassian.oai.validator.model.SimpleResponse;
import com.atlassian.oai.validator.report.LevelResolverFactory;
import com.atlassian.oai.validator.report.ValidationReport;
import com.definition.api.IntegratorApiDelegate;
import com.definition.api.model.JsonRequest;
import com.definition.api.model.JsonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadsl.flow.InboundCustomGateway;
import com.javadsl.flow.routingslip.*;
import com.javadsl.flow.routingslip.config.OutboundDetails;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.SneakyThrows;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class GatewayApiDelegateImpl implements IntegratorApiDelegate {

    @Autowired
    private InboundCustomGateway gateway;
    @Autowired
    private MessageProcessor messageProcessor;
    @Autowired
    private ObjectMapper jsonMapper;
    @Autowired
    public Map<String, RouteDetails> outboundConfigDetails;

    private static final Logger LOG = LoggerFactory.getLogger(GatewayApiDelegateImpl.class);

    @SneakyThrows
    public ResponseEntity<JsonResponse> integrator(String participantCode, JsonRequest body) {

        LOG.info("*************************GatewayApiDelegateApiImpl.java************************************");
        /*try {
            Message<?> message = gateway.customRestGateway(messageProcessor.prepareMessage(GenericRequest.builder()
                    .payload(jsonMapper.writeValueAsString(body))
                    .flowDetail(FlowDetail.builder()
                            .headers(Header.builder().httpHeaders(Map.of("Participant-Code", participantCode))
                                    .actualRequest(body)
                                    .domainHeaders(null).build())
                            .finalResponseClazz(JsonResponse.class).build())
                    .routeDetails(RouteDetails.builder().contentType(outboundConfigDetails.get("GATEWAY-API.OutboundRequest").getContentType())
                            .outboundEndpointUrl(outboundConfigDetails.get("GATEWAY-API.OutboundRequest").getOutboundEndpointUrl())
                            .httpMethod(outboundConfigDetails.get("GATEWAY-API.OutboundRequest").getHttpMethod())
                            .messageType("GATEWAY-API")
                            .build())
                    .build()));


            return ResponseEntity.ok().body((JsonResponse) message.getPayload());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/
        JsonResponse jsonResponse = new JsonResponse().result("Success Result");

        final OpenApiInteractionValidator validator = OpenApiInteractionValidator
                .createForSpecificationUrl("src/main/resources/integrator-api.yaml")
                //.createFor("/integrator-api.yaml")
                .withLevelResolver(LevelResolverFactory.withAdditionalPropertiesIgnored())

                .build();

        ValidationReport validationReport = validator.validateRequest(SimpleRequest.Builder.post("/gateway").withBody(jsonMapper.writeValueAsString(body)).
                withHeader("Participant-code", participantCode).withContentType("application/json").build());


        if (validationReport.hasErrors()) {
            LOG.info("dkfndslfkndsklfndsklnfdlsknfdlnfdlsnfld;snf;lsdnfdlnfl;sdnflsdknflsd;nfsd;lnfksd;lnf;l");
        }


        return ResponseEntity.ok().header("header-1", "custom header").
                body((jsonResponse));
    }
}
