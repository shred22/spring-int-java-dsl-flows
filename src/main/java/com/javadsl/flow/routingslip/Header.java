package com.javadsl.flow.routingslip;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Header {

    private Object actualRequest;
    private Map<String, Object> httpHeaders;
    private Map<String, Object> domainHeaders;
    private Map<String, Object> routeHeaders;


}
