package com.javadsl.flow.routingslip.config;

import lombok.Data;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class EndpointConfig {


    @Bean
    public CompositeConfiguration getConfigProperties() throws ConfigurationException {
        CompositeConfiguration config = new CompositeConfiguration();

        // add config sources.
        // add SystemConfiguration first below we need to override properties
        // using java system properties
        config.addConfiguration(new SystemConfiguration());
        config.addConfiguration(new PropertiesConfiguration("routing.properties"));
        config.getProperty("12345.REST");
        return config;
    }
}
