package pe.edu.unamba.academic.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Universidad Nacional Micaela Bastidas de Apurimac",
                version = "1.0",
                description = "Proyecto de la Universidad Nacional Micaela Bastidas de Apurimac",
                contact = @Contact(name = "Desarrollado por estudiante de la UNAMBA", email = "soporte@unamba.edu.pe"),
                license = @License(name = "Apache 2.0", url = "http://springdoc.org")
        ),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenApiConfig {
}