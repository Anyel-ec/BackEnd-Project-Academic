package pe.edu.unamba.academic.security.jwt;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import pe.edu.unamba.academic.models.messages.response.JsonResponse;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOG = LoggerFactory.getLogger(JwtEntryPoint.class);

    private String noAutorizado = "inhautorized";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse res,
                         org.springframework.security.core.AuthenticationException e) throws IOException, ServletException {

        LOG.error("FILTRO ENTY POINT -> ", e.getMessage());
        JsonResponse resp = new JsonResponse(Boolean.FALSE, HttpStatus.UNAUTHORIZED.value(), noAutorizado);
        res.setContentType("application/json");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(new ObjectMapper().writeValueAsString(resp));
        res.getWriter().flush();
        res.getWriter().close();
    }
}