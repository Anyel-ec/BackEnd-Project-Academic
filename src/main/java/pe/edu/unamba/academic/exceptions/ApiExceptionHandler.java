package pe.edu.unamba.academic.exceptions;

import pe.edu.unamba.academic.dto.response.JsonResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    // Handles cases where no handler is found for the request (404 error)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.warn("No se encontró el recurso solicitado: {}", ex.getRequestURL());
        return ApiErrorResponse.notFound("No se encontró la página solicitada");
    }

    // Handles cases where the requested media type is not acceptable
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        log.warn("Tipo de contenido no aceptable: {}", ex.getMessage());
        return ApiErrorResponse.notAcceptable("No se pudo encontrar una representación aceptable");
    }

    // Handles conflicts or state-related exceptions (409 error)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleConflict(IllegalStateException ex) {
        log.warn("Conflicto: {}", ex.getMessage());
        return ApiErrorResponse.conflict("Conflicto en la solicitud");
    }

    // Handles cases where the HTTP method used is not supported by the endpoint
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.warn("Método no soportado: {}", ex.getMethod());
        String message = String.format("Método de solicitud '%s' no soportado", ex.getMethod());
        return ApiErrorResponse.methodNotAllowed(new HttpHeaders(), message);
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<JsonResponseDto> timeoutException(TimeoutException ex, HttpServletRequest request) {
        log.info("ERROR, LA SOLICITUD HA TARDADO DEMASIADO => {}", ex.getMessage());
        HttpStatus estado = HttpStatus.INTERNAL_SERVER_ERROR;
        JsonResponseDto jsonResponse = new JsonResponseDto(false, estado.value(), "Ha tardado demasiado al consumir el servicio, por favor comunicate con desarrollo", null);
        return new ResponseEntity<>(jsonResponse, estado);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<JsonResponseDto> sQLException(SQLException ex, HttpServletRequest request) {
        log.info("ERROR AL CONECTAR CON LA BASE DE DATOS => {}", ex.getMessage());
        HttpStatus estado = HttpStatus.INTERNAL_SERVER_ERROR;
        JsonResponseDto jsonResponse = new JsonResponseDto(false, estado.value(), "Error al conectar con la base de datos", null);
        return new ResponseEntity<>(jsonResponse, estado);
    }

    // Handles cases where the media type in the request is not supported
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        log.warn("Tipo de contenido no soportado: {}", ex.getContentType());
        return ApiErrorResponse.unsupportedMediaType(new HttpHeaders(), "Tipo de contenido no soportado");
    }


    // Handles cases where a required request parameter is missing
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        log.warn("Missing request parameter: {}", ex.getParameterName());
        return ApiErrorResponse.badRequest("Falta el parámetro de solicitud: " + ex.getParameterName());
    }

    // Handles cases where there is a binding error in the request
    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex) {
        log.warn("Request binding error: {}", ex.getMessage());
        return ApiErrorResponse.badRequest("Error de enlace de solicitud");
    }

    // Handles cases where the response message cannot be written
    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex) {
        log.error("Message not writable: ", ex);
        return ApiErrorResponse.internalServerError("Error al escribir el mensaje de respuesta");
    }

    // Handles cases where access is denied to a resource
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return ApiErrorResponse.forbidden("Acceso denegado");
    }

    // Handles validation errors when method arguments are not valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ApiError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> ApiError.fieldApiError(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue()))
                .toList();
        return ApiErrorResponse.unprocessableEntity(errors, "Validación de argumentos fallida");
    }

    // Handles constraint violation exceptions (validation failures)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ApiError> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> ApiError.fieldApiError(violation.getPropertyPath().toString(), violation.getMessage(), violation.getInvalidValue()))
                .toList();
        return ApiErrorResponse.unprocessableEntity(errors, "Validación de argumentos fallida");
    }

    // Handles type mismatch errors (e.g., incorrect data types in requests)
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex) {
        log.warn("Tipo de dato no coincide: {}", ex.getPropertyName());
        return ApiErrorResponse.badRequest("Solicitud de no coincidencia de tipos");
    }

    // Hanlde not find username
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("Username not found: ", ex);
        return ApiErrorResponse.notFound("No se encontró el usuario");
    }

    // Handle bad credentials
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Bad credentials: ", ex);
        return ApiErrorResponse.badRequest("Credenciales incorrectas");
    }

    // Handles general exceptions that are not specifically defined
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        log.error("Internal Server Error: ", ex);
        return ApiErrorResponse.internalServerError("Ocurrió un error inesperado");
    }

    // Handles any throwable errors, providing a fallback for unhandled cases
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleThrowable(Throwable throwable) {
        log.error("Request handling failed", throwable);
        return ApiErrorResponse.internalServerError("Ocurrió un error inesperado");
    }

    // Handles JSON parsing errors (e.g., incorrect data types or malformed JSON)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("JSON parse error: {}", ex.getMessage());
        return ApiErrorResponse.badRequest("Error de formato JSON ");
    }

}