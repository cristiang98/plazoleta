package com.user.infrastructure.documentation;

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
                        .title(" API de usuarios proyecto de aprendizaje" )
                        .version("1.0")
                        .description(
                                "Este es un proyecto de aprendizaje para el manejo de usuarios se creara un CRUD de " +
                                        "usuarios y se implementara un sistema de autenticacion con JWT")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }

}
