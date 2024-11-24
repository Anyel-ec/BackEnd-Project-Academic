package pe.edu.unamba.academic.security.jwt;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.*;
import pe.edu.unamba.academic.models.User;
import pe.edu.unamba.academic.security.UsuarioPrincipal;

@RequiredArgsConstructor
@Component
public class JwtUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .subject(usuarioPrincipal.getUsername())
                // .claim("roles", roles)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                // .expiration(new Date(System.currentTimeMillis() + 60 * 1000))
                .signWith(getSecret(secret))
                .compact();
    }

    public String generateJwtByUsername(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration * 60 * 1000)) // Expiración en minutos
                .signWith(getSecret(secret))
                .compact();
    }

    public String getNombreUsuarioFromToken(String token) {
        Jws<Claims> parsed = Jwts.parser().verifyWith(getSecret(secret)).build().parseSignedClaims(token);
        return parsed.getPayload().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSecret(secret)).build().parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            LOG.error("Invalid JWT token");
        } catch (UnsupportedJwtException e) {
            LOG.error("Unsupported JWT token");
        } catch (ExpiredJwtException e) {
            LOG.error("Expired JWT token");
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty");
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature");
        } catch (JwtException e) {
            LOG.error("Invalid JWT");
        }
        return false;
    }

    private SecretKey getSecret(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String encryptar(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
