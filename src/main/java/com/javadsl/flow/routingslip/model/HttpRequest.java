package com.javadsl.flow.routingslip.model;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class HttpRequest {

  private Map<String, String> httpRequestHeaders;
  private Object httpRequest;

}
