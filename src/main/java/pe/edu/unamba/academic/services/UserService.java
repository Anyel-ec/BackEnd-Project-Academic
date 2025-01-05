package pe.edu.unamba.academic.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.dto.response.JsonResponseDto;
import pe.edu.unamba.academic.models.UserInfo;
import pe.edu.unamba.academic.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserInfo> getAllUsers() {
        return userRepository.findAll();
    }

    public JsonResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new JsonResponseDto(true, HttpStatus.OK.value(), "Usuario encontrado", user))
                .orElseGet(() -> new JsonResponseDto(false, HttpStatus.NOT_FOUND.value(), "Usuario no encontrado", null));
    }

    public JsonResponseDto createUser(UserInfo user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new JsonResponseDto(true, HttpStatus.CREATED.value(), "Usuario creado exitosamente", null);
        } catch (Exception e) {
            LOG.error("Error al crear usuario: {}", e.getMessage(), e);
            return new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al crear el usuario", e.getMessage());
        }
    }

    public JsonResponseDto updateUser(Long id, UserInfo userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            user.setRol(userDetails.getRol());
            user.setResearchUnit(userDetails.getResearchUnit());
            userRepository.save(user);
            return new JsonResponseDto(true, HttpStatus.OK.value(), "Usuario actualizado correctamente", user);
        }).orElseGet(() -> new JsonResponseDto(false, HttpStatus.NOT_FOUND.value(), "Usuario no encontrado", null));
    }

    public JsonResponseDto deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return new JsonResponseDto(false, HttpStatus.NOT_FOUND.value(), "Usuario no encontrado", null);
        }
        try {
            userRepository.deleteById(id);
            return new JsonResponseDto(true, HttpStatus.OK.value(), "Usuario eliminado correctamente", null);
        } catch (Exception e) {
            LOG.error("Error al eliminar usuario: {}", e.getMessage(), e);
            return new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar el usuario", e.getMessage());
        }
    }

    public JsonResponseDto validateUserJWT(String token) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            LOG.info("Validando usuario con token: {}", username);
            return userRepository.findByUsernameAndStateTrue(username)
                    .map(user -> new JsonResponseDto(true, HttpStatus.OK.value(), "Usuario autorizado", user))
                    .orElseGet(() -> new JsonResponseDto(false, HttpStatus.UNAUTHORIZED.value(), "No autorizado", null));
        } catch (Exception e) {
            LOG.error("Error al validar usuario con JWT: {}", e.getMessage(), e);
            return new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor", null);
        }
    }

    public JsonResponseDto getUserByUsername(String username) {
        return userRepository.findByUsernameAndStateTrue(username)
                .map(user -> new JsonResponseDto(true, HttpStatus.OK.value(), "Usuario encontrado", user))
                .orElseGet(() -> new JsonResponseDto(false, HttpStatus.NOT_FOUND.value(), "Usuario no registrado", null));
    }
}
