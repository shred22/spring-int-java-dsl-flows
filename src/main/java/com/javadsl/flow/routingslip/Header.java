package com.javadsl.flow.routingslip;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Header {

    private Object originalRequest;
    private Map<String, Object> httpHeaders;
    private Map<String, Object> domainHeaders;
    private Map<String, Object> routeHeaders;


}
