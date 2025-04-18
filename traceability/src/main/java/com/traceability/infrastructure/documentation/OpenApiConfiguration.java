package com.traceability.infrastructure.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(" API de trazabilidad de un  proyecto de aprendizaje" )
                        .version("1.0")
                        .description(
                                "proyecto de aprendizaje pragma el cual permite mirar " +
                                        "reportes de trazabilidad de un proyecto de aprendizaje")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }

}
