package pe.edu.unamba.academic.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.dto.response.JsonResponseDto;
import pe.edu.unamba.academic.models.UserInfo;
import pe.edu.unamba.academic.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    // Validar usuario con JWT
    public JsonResponseDto validateUserJWT(String token) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            LOG.info("Username obtenido del token: {}", username);

            Optional<UserInfo> user = userRepository.findByUsernameAndStateTrue(username);
            if (user.isEmpty()) {
                return new JsonResponseDto(false, HttpStatus.UNAUTHORIZED.value(), "No autorizado", null);
            }

            return new JsonResponseDto(true, HttpStatus.OK.value(), "Usuario autorizado", user.get());

        } catch (Exception e) {
            LOG.error("Error al validar usuario con JWT: {}", e.getMessage());
            return new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor", null);
        }
    }

    // Crear usuario
    public JsonResponseDto createUser(UserInfo user) {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new JsonResponseDto(true, HttpStatus.CREATED.value(), "Usuario creado exitosamente", null);
        } catch (Exception e) {
            LOG.error("Error al crear usuario: {}", e.getMessage());
            return new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al crear el usuario", e.getMessage());
        }
    }
}
