package com.device.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI deviceManagementAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development Server");

        Contact contact = new Contact();
        contact.setName("Device Management Team");
        contact.setEmail("support@device.com");

        Info info = new Info()
                .title("Device Management API")
                .version("1.0.0")
                .description("REST API for managing devices with different states")
                .contact(contact);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}