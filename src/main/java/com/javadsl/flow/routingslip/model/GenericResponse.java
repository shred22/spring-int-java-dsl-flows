package com.javadsl.flow.routingslip.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenericResponse {

  private HttpResponse httpResponse;
  private Header  header;

}
