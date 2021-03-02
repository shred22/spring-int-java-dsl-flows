package com.javadsl.flow.routingslip;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class GenericRequest {

    private Object payload;

    private RouteDetails routeDetails;

    private FlowDetail flowDetail;


}
