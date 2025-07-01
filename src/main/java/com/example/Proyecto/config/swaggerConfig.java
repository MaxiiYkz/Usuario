package com.example.Proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class swaggerConfig {

    @Bean
    OpenAPI customAPI() {
        return new OpenAPI().info(new Info().title("API 2025 V1 Sistema de Usuarios").version("1.0").description("Documentacion de la API enfocado en los Usuarios"));
            
        }


}
