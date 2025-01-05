package pe.edu.unamba.academic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.dto.response.JsonResponseDto;
import pe.edu.unamba.academic.models.PasswordUpdateRequest;
import pe.edu.unamba.academic.models.UserInfo;
import pe.edu.unamba.academic.models.messages.request.LoginUser;
import pe.edu.unamba.academic.services.AutenticationServiceImpl;
import pe.edu.unamba.academic.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AutenticationServiceImpl authService;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    // Obtener usuario por username
    @GetMapping("/obtener/{username}")
    public ResponseEntity<JsonResponseDto> getUserByUsername(@PathVariable String username) {
        JsonResponseDto response = userService.getUserByUsername(username);
        return ResponseEntity.status(response.httpCode()).body(response);
    }

    // Login del usuario
    @PostMapping("/auth/login")
    public ResponseEntity<JsonResponseDto> loginCustom(
            @Validated @RequestBody(required = true) LoginUser datosLogin) throws JsonProcessingException {
        JsonResponseDto response = authService.logear(datosLogin);
        return ResponseEntity.status(response.httpCode()).body(response);
    }

    // Crear un nuevo usuario
    @PostMapping("/")
    public ResponseEntity<JsonResponseDto> createUser(@RequestBody UserInfo user) {
        try {
            JsonResponseDto response = userService.createUser(user);
            return ResponseEntity.status(response.httpCode()).body(response);
        } catch (DataIntegrityViolationException e) {
            LOG.error("Error de integridad al crear el usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new JsonResponseDto(false, HttpStatus.CONFLICT.value(), "El usuario ya existe", null));
        } catch (Exception e) {
            LOG.error("Error inesperado al crear usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno", null));
        }
    }

    // Actualizar contraseña
    @PostMapping("/actualizar-contrasena/{username}")
    public ResponseEntity<JsonResponseDto> updatePassword(
            @PathVariable String username,
            @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        String newPassword = passwordUpdateRequest.getPassword();

        if (username == null || username.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new JsonResponseDto(false, HttpStatus.BAD_REQUEST.value(), "Datos incorrectos", null));
        }

        JsonResponseDto response = authService.updatePassword(username, newPassword);
        return ResponseEntity.status(response.httpCode()).body(response);
    }

    // Actualizar primer acceso
    @PutMapping("/actualizar-primer-acceso/{username}")
    public ResponseEntity<JsonResponseDto> updateFirstAccess(@PathVariable String username) {
        JsonResponseDto response = authService.updateFirstAccess(username);
        return ResponseEntity.status(response.httpCode()).body(response);
    }

    // Obtener todos los usuarios
    @GetMapping("/")
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        List<UserInfo> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<JsonResponseDto> getUserById(@PathVariable Long id) {
        JsonResponseDto response = userService.getUserById(id);
        return ResponseEntity.status(response.httpCode()).body(response);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<JsonResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserInfo userDetails) {
        try {
            JsonResponseDto response = userService.updateUser(id, userDetails);
            return ResponseEntity.status(response.httpCode()).body(response);
        } catch (Exception e) {
            LOG.error("Error al actualizar usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno", null));
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponseDto> deleteUser(@PathVariable Long id) {
        try {
            JsonResponseDto response = userService.deleteUser(id);
            return ResponseEntity.status(response.httpCode()).body(response);
        } catch (Exception e) {
            LOG.error("Error al eliminar usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new JsonResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno", null));
        }
    }
}
