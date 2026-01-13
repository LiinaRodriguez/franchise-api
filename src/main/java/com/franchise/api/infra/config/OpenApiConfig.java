package com.franchise.api.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Franchise Management API")
                        .version("1.0.0")
                        .description("Reactive REST API for managing franchises, branches, and products")
                        .contact(new Contact()
                                .name("Lina Rodriguez")
                                .url("https://github.com/liinarodriguez/franchise-api")
                        ));
    }
}