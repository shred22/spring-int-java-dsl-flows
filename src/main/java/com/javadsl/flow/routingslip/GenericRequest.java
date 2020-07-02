package com.javadsl.flow.routingslip;


import com.javadsl.flow.routingslip.config.Route;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class GenericRequest {

    private Object payload;

    private RouteDetails routeDetails;

    private FlowDetail flowDetail;


}
