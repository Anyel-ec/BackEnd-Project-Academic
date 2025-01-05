package pe.edu.unamba.academic.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.dto.response.JsonResponseDto;
import pe.edu.unamba.academic.models.UserInfo;
import pe.edu.unamba.academic.models.messages.request.LoginUser;
import pe.edu.unamba.academic.repositories.UserRepository;
import pe.edu.unamba.academic.security.jwt.JwtProvider;

import java.util.Optional;

@Service
public class AutenticationServiceImpl {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AutenticationServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public JsonResponseDto logear(LoginUser login) {
        try {
            LOG.info("============= PROCESO DE LOGIN ========================");

            String username = login.username() != null ? login.username().trim() : "";
            String pass = login.password() != null ? login.password().trim() : "";

            if (username.isEmpty() || pass.isEmpty()) {
                return new JsonResponseDto(false, HttpStatus.BAD_REQUEST.value(), "Parámetros inválidos", null);
            }

            Optional<UserInfo> userFound = userRepository.findByUsernameAndStateTrue(username);
            if (userFound.isEmpty()) {
                LOG.info("USUARIO NO REGISTRADO");
                return new JsonResponseDto(false, HttpStatus.NOT_FOUND.value(), "El usuario no está registrado", null);
            }

            UserInfo user = userFound.get();

            if (!passwordEncoder.matches(pass, user.getPassword())) {
                return new JsonResponseDto(false, HttpStatus.UNAUTHORIZED.value(), "La contraseña es incorrecta", null);
            }

            String jwt = jwtProvider.generateJwtByUsername(user);
            return new JsonResponseDto(true, HttpStatus.OK.value(), "Login exitoso", jwt);

        } catch (Exception e) {
            LOG.error("Error en login: {}", e.getMessage());
            return new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error en el login", null);
        }
    }

    public JsonResponseDto createUserLogin(UserInfo user) {
        try {
            if (userRepository.findByUsernameAndStateTrue(user.getUsername()).isPresent()) {
                return new JsonResponseDto(false, HttpStatus.CONFLICT.value(), "El usuario ya existe", null);
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new JsonResponseDto(true, HttpStatus.CREATED.value(), "Usuario creado exitosamente", null);

        } catch (Exception e) {
            LOG.error("Error al crear usuario: {}", e.getMessage());
            return new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al crear el usuario", null);
        }
    }

    public JsonResponseDto updatePassword(String username, String password) {
        try {
            Optional<UserInfo> userOptional = userRepository.findByUsernameAndStateTrue(username);
            if (userOptional.isEmpty()) {
                return new JsonResponseDto(false, HttpStatus.NOT_FOUND.value(), "El usuario no está registrado", null);
            }

            UserInfo user = userOptional.get();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            userRepository.save(user);

            return new JsonResponseDto(true, HttpStatus.OK.value(), "Contraseña actualizada correctamente", null);

        } catch (Exception e) {
            LOG.error("Error al actualizar contraseña: {}", e.getMessage());
            return new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar la contraseña", null);
        }
    }

    public JsonResponseDto updateFirstAccess(String username) {
        try {
            Optional<UserInfo> userOptional = userRepository.findByUsernameAndStateTrue(username);
            if (userOptional.isEmpty()) {
                return new JsonResponseDto(false, HttpStatus.NOT_FOUND.value(), "El usuario no está registrado", null);
            }

            UserInfo user = userOptional.get();
            user.setFirstLogin(false);
            userRepository.save(user);

            return new JsonResponseDto(true, HttpStatus.OK.value(), "Primer acceso actualizado correctamente", null);

        } catch (Exception e) {
            LOG.error("Error al actualizar primer acceso: {}", e.getMessage());
            return new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar el primer acceso", null);
        }
    }
}
