package com.javadsl.flow.routingslip;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class FlowDetail {

    private Header headers;
    private  Class finalResponseClazz;

    public Map<String, FlowDetail> convertToHeaderMap(String mapKey) {
        return Map.of(mapKey, this);
    }
}
