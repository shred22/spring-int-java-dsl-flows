package com.javadsl.flow.routingslip.config;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Routes {

    private List<Route> route;
}
