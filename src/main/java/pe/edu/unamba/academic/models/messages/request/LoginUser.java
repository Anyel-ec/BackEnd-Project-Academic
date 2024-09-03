package pe.edu.unamba.academic.models.messages.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Login", description = "Modelo para iniciar sesión")
public record LoginUser(String username, String password) {}