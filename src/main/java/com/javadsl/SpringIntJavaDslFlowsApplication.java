package com.javadsl;

import org.openapitools.configuration.OpenAPIDocumentationConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.Optional;

/*
routing:
  flow:
    route:
      -
          messageType: GATEWAY-API
          handlerClass: OutboundRequest
          channel: outboundChannel
          index: 0
      -
          messageType: GATEWAY-API
          handlerClass: InternalRequest
          channel: outboundChannel
          index: 1


 */
@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "com.javadsl.flow")
@ComponentScan(basePackages = {"com.javadsl.flow"})
@EnableIntegration
@EnableSwagger2
@IntegrationComponentScan(value = "com.javadsl.flow")
public class SpringIntJavaDslFlowsApplication {



    public static void main(String[] args) {
        SpringApplication.run(SpringIntJavaDslFlowsApplication.class, args);
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResolutionMethod(YamlProcessor.ResolutionMethod.OVERRIDE_AND_IGNORE);
        yaml.setResources(new ClassPathResource("router.yml"));

        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
        return propertySourcesPlaceholderConfigurer;
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Routing Slip Implementation")
                .description("This is REST API that opens doorways for routing slip pattern, this will process message of any type and acts as an orchestrator")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("","", "shreyas.dange22@gmail.com"))
                .build();
    }

    @Bean
    public Docket customImplementation(ServletContext servletContext, @Value("${openapi.routingSlipImplementation.base-path:}") String basePath) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.definition.api"))
                .build()
                .pathProvider(new BasePathAwareRelativePathProvider(servletContext, basePath))
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.OffsetDateTime.class, java.util.Date.class)
                .genericModelSubstitutes(Optional.class)
                .apiInfo(apiInfo());
    }

    class BasePathAwareRelativePathProvider extends RelativePathProvider {
        private String basePath;

        public BasePathAwareRelativePathProvider(ServletContext servletContext, String basePath) {
            super(servletContext);
            this.basePath = basePath;
        }

        @Override
        protected String applicationPath() {
            return  Paths.removeAdjacentForwardSlashes(UriComponentsBuilder.fromPath(super.applicationPath()).path(basePath).build().toString());
        }

        @Override
        public String getOperationPath(String operationPath) {
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");
            return Paths.removeAdjacentForwardSlashes(
                    uriComponentsBuilder.path(operationPath.replaceFirst("^" + basePath, "")).build().toString());
        }
    }


}
