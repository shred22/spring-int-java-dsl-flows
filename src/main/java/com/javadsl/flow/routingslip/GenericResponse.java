package com.javadsl.flow.routingslip;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GenericResponse {

  private Object httpResponse;
  private Map<String, Object> responseHeaders;


  public static final class GenericResponseBuilder {

    private Object httpResponse;
    private Map<String, Object> responseHeaders;

    private GenericResponseBuilder() {
    }

    public static GenericResponseBuilder builder() {
      return new GenericResponseBuilder();
    }

    public GenericResponseBuilder withHttpResponse(Object httpResponse) {
      this.httpResponse = httpResponse;
      return this;
    }

    public GenericResponseBuilder withResponseHeaders(Map<String, Object> responseHeaders) {
      this.responseHeaders = responseHeaders;
      return this;
    }

    public GenericResponse build() {
      GenericResponse genericResponse = new GenericResponse();
      genericResponse.responseHeaders = this.responseHeaders;
      genericResponse.httpResponse = this.httpResponse;
      return genericResponse;
    }
  }
}
