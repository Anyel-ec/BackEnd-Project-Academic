package pe.edu.unamba.academic.models.messages.response;

import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "JsonResponse", description = "Modelo de respuesta predeterminado")
@Data
@NoArgsConstructor
public class JsonResponse {

    private Boolean respuesta;
    private int codeStatus = HttpStatus.OK.value();
    private String mensaje;
    private Object resultado;

    // Constructores
    public JsonResponse(Boolean respuesta, Object resultado) {
        this.respuesta = respuesta;
        this.resultado = resultado;
    }

    public JsonResponse(Boolean respuesta, String mensaje) {
        this.respuesta = respuesta;
        this.mensaje = mensaje;
    }

    public JsonResponse(Boolean respuesta, String mensaje, Object resultado) {
        this.respuesta = respuesta;
        this.mensaje = mensaje;
        this.resultado = resultado;
    }

    public JsonResponse(Boolean respuesta, int codeStatus, Object resultado) {
        this.respuesta = respuesta;
        this.codeStatus = codeStatus;
        this.resultado = resultado;
    }

    public JsonResponse(Boolean respuesta, int codeStatus, String mensaje) {
        this.respuesta = respuesta;
        this.codeStatus = codeStatus;
        this.mensaje = mensaje;
    }

    public JsonResponse(Boolean respuesta, int codeStatus, String mensaje, Object resultado) {
        this.respuesta = respuesta;
        this.codeStatus = codeStatus;
        this.mensaje = mensaje;
        this.resultado = resultado;
    }

    // Método isSuccess: devolverá el valor de "respuesta"
    public boolean isSuccess() {
        return this.respuesta != null && this.respuesta;
    }

    // Método getMessage: devolverá el valor de "mensaje"
    public String getMessage() {
        return this.mensaje;
    }

    public boolean isPresent() {
        // TODO document why this method is empty
            return this.respuesta != null && this.respuesta;

    }
}
