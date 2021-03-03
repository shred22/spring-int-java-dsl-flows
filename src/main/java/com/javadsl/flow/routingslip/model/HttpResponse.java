package com.javadsl.flow.routingslip.model;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@Builder
public class HttpResponse {

  private Object httpResponse;
  private Map<String, Object> responseHttpHeaders;
  private HttpStatus httpStatus;

}
