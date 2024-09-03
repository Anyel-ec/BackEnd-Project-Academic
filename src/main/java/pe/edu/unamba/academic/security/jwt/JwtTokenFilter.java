package pe.edu.unamba.academic.security.jwt;


import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.unamba.academic.security.UserDetailsServiceImpl;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private final static Logger LOG = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request,
                                    @SuppressWarnings("null") HttpServletResponse response,
                                    @SuppressWarnings("null") @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            LOG.info("-----filtro-----");
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                String jwt = authHeader.split(" ")[1].trim();
                if (jwtUtil.validateToken(jwt)) {
                    String username = jwtUtil.getNombreUsuarioFromToken(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(userDetails,
                            null, null);
                    SecurityContextHolder.getContext().setAuthentication(authUser);
                }
            }

        } catch (JwtException e) {
            LOG.error("Excepción al procesar el token", e.getMessage());
        } catch (SecurityException e) {
            LOG.error("No se pudo autenticar al usuario", e.getMessage());
            throw new ServletException("Error de autenticación.");
        }
        filterChain.doFilter(request, response);

    }

}
