package com.example.demo.infra.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Banco ",
                description = "API Banco disponibilizado pelo time Start",
                version = "V1",
                contact = @Contact(
                        name = "Wendell Vinicius",
                        url = "https://github.com/wendellvsilva/desafio-start-banco",
                        email = "wendell.silva@dbserver.com.br"
                )
        ),
        servers = @Server(url = "/")
)
public class SwaggerConfig {
}
