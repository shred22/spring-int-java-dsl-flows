package com.javadsl.flow.routingslip.config;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class Routes {

    private List<Route> route;
}
