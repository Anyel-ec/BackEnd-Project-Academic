package pe.edu.unamba.academic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.core.util.Json;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.PasswordUpdateRequest;
import pe.edu.unamba.academic.models.User;
import pe.edu.unamba.academic.models.messages.request.LoginUser;
import pe.edu.unamba.academic.models.messages.response.JsonResponse;
import pe.edu.unamba.academic.security.jwt.JwtUtil;
import pe.edu.unamba.academic.services.AutenticationServiceImpl;
import pe.edu.unamba.academic.services.UserService;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AutenticationServiceImpl authService;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/obtener/{username}")
    public JsonResponse getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }


    @PostMapping("/auth/login")
    public ResponseEntity<JsonResponse> loginCustom(@Validated @RequestBody(required = true) LoginUser datosLogin,
                                                    HttpServletRequest request) throws JsonProcessingException {
        JsonResponse resp = authService.logear(datosLogin);
        return ResponseEntity.ok().body(resp);

    }


    @PostMapping("/create-password")
    public JsonResponse createPassword(@RequestBody User user) {
        // si ya existe el usuario, no se crea

        return authService.createUserLogin(user);
    }



    @PostMapping("/actualizar-contrasena/{username}")
    public JsonResponse updatePassword(@PathVariable String username, @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        String newPassword = passwordUpdateRequest.getPassword();
        LOG.info("username: " + username + " password: " + newPassword);
        if (username == null || newPassword == null) {

            return new JsonResponse(false, HttpStatus.BAD_REQUEST.value(), "Datos incorrectos");
        }
        if (username.isEmpty() || newPassword.isEmpty()) {
            return new JsonResponse(false, HttpStatus.BAD_REQUEST.value(), "Datos incorrectos");
        }

        return authService.updatePassword(username, newPassword);
    }

    @PutMapping("/actualizar-primer-acceso/{username}")
    public JsonResponse updateFirstAccess(@PathVariable String username) {
        return authService.updateFirstAccess(username);
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok().body(userService.saveUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            return ResponseEntity.ok().body(userService.updateUser(id, userDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}