package pe.edu.unamba.academic.dto.response;

public record JsonResponseDto(boolean success, int httpCode, String message, Object result) {
}