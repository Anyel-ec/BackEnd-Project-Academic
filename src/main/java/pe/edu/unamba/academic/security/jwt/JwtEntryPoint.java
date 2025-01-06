package pe.edu.unamba.academic.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pe.edu.unamba.academic.dto.response.JsonResponseDto;

import java.io.IOException;

@Component
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {

    // This method intercepts unauthenticated requests and returns a response with an error message in JSON format.
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e)
            throws IOException {
        log.error("Error no autorizado {}", e.getMessage());
        JsonResponseDto resp = new JsonResponseDto(Boolean.FALSE, HttpStatus.UNAUTHORIZED.value(), "Unauthorized", null);
        res.setContentType("application/json");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(new ObjectMapper().writeValueAsString(resp));
        res.getWriter().flush();
        res.getWriter().close();
    }

}
