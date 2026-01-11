package com.franchise.api.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Franchise Management API")
                        .version("1.0.0")
                        .description(
                            "Reactive REST API for managing franchises, branches, and products. " +
                            "Built with Spring Boot WebFlux, reactive programming, and Clean Architecture. " +
                            "\n\n**Features:**\n" +
                            "- Complete CRUD operations\n" +
                            "- Reactive programming (non-blocking)\n" +
                            "- MongoDB persistence\n" +
                            "- Business validations\n" +
                            "- Centralized exception handling"
                        )
                        .contact(new Contact()
                                .name("Franchise API Team")
                                .url("https://github.com/liinarodriguez/franchise-api")
                        )
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")
                        )
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local development server"),
                        new Server()
                                .url("https://api.franchise.com")
                                .description("Production server")
                ));
    }
}