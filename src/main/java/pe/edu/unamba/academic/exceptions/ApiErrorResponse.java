package pe.edu.unamba.academic.exceptions;
import pe.edu.unamba.academic.dto.response.JsonResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ToString
@Getter
public final class ApiErrorResponse {

    private final boolean response;
    private final int httpCode;
    private final String message;
    private final Object result;

    @Builder
    private ApiErrorResponse(boolean response, HttpStatus httpStatus, String message, Collection<ApiError> errores) {
        this.response = response;
        this.httpCode = httpStatus.value();
        this.message = message;
        this.result = isNull(errores) ? emptyList() : errores;
    }

    private static ResponseEntity<Object> buildResponse(HttpStatus status, String message, Object resultado) {
        JsonResponseDto response = new JsonResponseDto(false, status.value(), message, resultado);
        return ResponseEntity.status(status).contentType(APPLICATION_JSON).body(response);
    }

    public static ResponseEntity<Object> badRequest(String message) {
        return buildResponse(BAD_REQUEST, message, null);
    }

    public static ResponseEntity<Object> notFound(String message) {
        return buildResponse(NOT_FOUND, message, null);
    }

    public static ResponseEntity<Object> unprocessableEntity(Collection<ApiError> errors, String message) {
        return buildResponse(UNPROCESSABLE_ENTITY, message, errors);
    }

    public static ResponseEntity<Object> conflict(String message) {
        return buildResponse(CONFLICT, message, null);
    }

    public static ResponseEntity<Object> methodNotAllowed(HttpHeaders headers, String message) {
        return buildResponse(METHOD_NOT_ALLOWED, message, null);
    }

    public static ResponseEntity<Object> notAcceptable(String message) {
        return buildResponse(NOT_ACCEPTABLE, message, null);
    }

    public static ResponseEntity<Object> unsupportedMediaType(HttpHeaders headers, String message) {
        return buildResponse(UNSUPPORTED_MEDIA_TYPE, message, null);
    }

    public static ResponseEntity<Object> internalServerError(String message) {
        return buildResponse(INTERNAL_SERVER_ERROR, message, null);
    }

    public static ResponseEntity<Object> internalServerError(String message, Collection<ApiError> errors) {
        return buildResponse(INTERNAL_SERVER_ERROR, message, errors);
    }

    public static ResponseEntity<Object> forbidden(String message) {
        return buildResponse(FORBIDDEN, message, null);
    }

}