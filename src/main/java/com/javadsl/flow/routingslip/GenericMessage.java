package com.javadsl.flow.routingslip;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class GenericMessage {

  private GenericRequest genericRequest;
  private GenericResponse genericResponse;

}
