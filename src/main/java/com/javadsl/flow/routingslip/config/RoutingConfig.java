package com.javadsl.flow.routingslip.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;




@Service
@ConfigurationProperties(prefix = "routing")
@Setter
@Getter
@Validated
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoutingConfig {

    private List<Flows> flows = Collections.emptyList();
}
