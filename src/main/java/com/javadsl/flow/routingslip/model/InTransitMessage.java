package com.javadsl.flow.routingslip.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class InTransitMessage {

  private HttpRequest executedHttpRequest;
  private HttpResponse executedHttpResponse;
  private Header header;

}
