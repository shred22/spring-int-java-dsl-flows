package com.javadsl.flow.routingslip;

import com.javadsl.flow.routingslip.model.Header;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class FlowDetail {

    private Header headers;
    private  Class finalResponseClazz;

    public Map<String, FlowDetail> convertToHeaderMap(String mapKey) {
        return Map.of(mapKey, this);
    }
}
