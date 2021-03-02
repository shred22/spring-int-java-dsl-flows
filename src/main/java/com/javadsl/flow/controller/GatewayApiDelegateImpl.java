package com.javadsl.flow.controller;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.model.SimpleRequest;
import com.atlassian.oai.validator.report.LevelResolverFactory;
import com.atlassian.oai.validator.report.ValidationReport;
import com.definition.api.IntegratorApiDelegate;
import com.definition.api.model.JsonRequest;
import com.definition.api.model.JsonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javadsl.flow.gateway.InboundCustomGateway;
import com.javadsl.flow.routingslip.FlowDetail;
import com.javadsl.flow.routingslip.GenericRequest;
import com.javadsl.flow.routingslip.Header;
import com.javadsl.flow.routingslip.MessageProcessor;
import com.javadsl.flow.routingslip.RouteDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GatewayApiDelegateImpl implements IntegratorApiDelegate {

  private final InboundCustomGateway gateway;
  private final MessageProcessor messageProcessor;
  private final ObjectMapper jsonMapper;
  private final Map<String, RouteDetails> outboundConfigDetails;
  private static final Logger LOG = LoggerFactory.getLogger(GatewayApiDelegateImpl.class);

  private GatewayApiDelegateImpl(InboundCustomGateway gateway,
      MessageProcessor messageProcessor, ObjectMapper jsonMapper,
      Map<String, RouteDetails> outboundConfigDetails) {
    this.gateway = gateway;
    this.messageProcessor = messageProcessor;
    this.jsonMapper = jsonMapper;
    this.outboundConfigDetails = outboundConfigDetails;
  }

  @SneakyThrows
  @RequestMapping(value = "/gateway",
      produces = {"application/json"},
      consumes = {"application/json"},
      method = RequestMethod.POST)
  public ResponseEntity<JsonResponse> integrator(
      @RequestHeader(value = "Participant-Code") String participantCode,
      @RequestBody JsonRequest body) {

    LOG.info(
        "*************************GatewayApiDelegateApiImpl.java************************************");
    try {
      Message<?> message = gateway.customRestGateway(messageProcessor.prepareMessage(GenericRequest.builder()
              .payload(jsonMapper.writeValueAsString(body))
              .flowDetail(FlowDetail.builder()
                  .headers(Header.builder().httpHeaders(Map.of("Participant-Code", participantCode))
                      .originalRequest(body)
                      .domainHeaders(new HashMap<>())
                      .build())
                  .finalResponseClazz(JsonResponse.class).build())
              .routeDetails(RouteDetails.builder().contentType(
                  outboundConfigDetails.get("GATEWAY-API.OutboundRequest").getContentType())
                  .outboundEndpointUrl(outboundConfigDetails.get("GATEWAY-API.OutboundRequest")
                      .getOutboundEndpointUrl())
                  .httpMethod(
                      outboundConfigDetails.get("GATEWAY-API.OutboundRequest").getHttpMethod())
                  .messageType("GATEWAY-API")
                  .validationKeys(List.of("GATEWAY-API.response"))
                  .build())
              .build()));

      return ResponseEntity.ok().body((JsonResponse) message.getPayload());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    JsonResponse jsonResponse = new JsonResponse().result("Success Result");

    final OpenApiInteractionValidator validator = OpenApiInteractionValidator
        .createForSpecificationUrl("src/main/resources/integrator-api.yaml")
        //.createFor("/integrator-api.yaml")
        .withLevelResolver(LevelResolverFactory.withAdditionalPropertiesIgnored())

        .build();

    ValidationReport validationReport = validator.validateRequest(
        SimpleRequest.Builder.post("/gateway").withBody(jsonMapper.writeValueAsString(body)).
            withHeader("Participant-code", participantCode).withContentType("application/json")
            .build());

    if (validationReport.hasErrors()) {
      LOG.info(
          "dkfndslfkndsklfndsklnfdlsknfdlnfdlsnfld;snf;lsdnfdlnfl;sdnflsdknflsd;nfsd;lnfksd;lnf;l");
    }

    return ResponseEntity.ok().header("header-1", "custom header").
        body((jsonResponse));
  }
}
