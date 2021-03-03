package com.javadsl.flow.routingslip.model;

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

    private Map<String, String> httpHeaders;
    private Map<String, Object> domainHeaders;
    private Map<String, Object> routeHeaders;


}
