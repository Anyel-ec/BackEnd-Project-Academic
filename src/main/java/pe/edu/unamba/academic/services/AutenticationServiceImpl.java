package pe.edu.unamba.academic.services;

import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.interfaces.LoginService;
import pe.edu.unamba.academic.models.User;
import pe.edu.unamba.academic.models.messages.request.LoginUser;
import pe.edu.unamba.academic.models.messages.response.JsonResponse;
import pe.edu.unamba.academic.repositories.UserRepository;
import pe.edu.unamba.academic.security.jwt.JwtUtil;

@Service
public class AutenticationServiceImpl implements LoginService {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AutenticationServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder; // Utilizar la instancia de PasswordEncoder autowired

    @Override
    public JsonResponse logear(LoginUser login) {
        try {
            LOG.info("=============PROCESO DE LOGIN ========================");
            String username = login.username() != null ? login.username().trim() : "";
            String pass = login.password() != null ? login.password().trim() : "";

            if (username.isEmpty() || pass.isEmpty()) {
                return new JsonResponse(false, "Parámetros inválidos");
            }

            Optional<User> userFound = userRepository.findByUsernameAndStateTrue(username);
            if (!userFound.isPresent()) {
                LOG.info("USUARIO NO REGISTRADO");
                return new JsonResponse(false, "El usuario no registrado");
            }
            User user = userFound.get();

            if (!encoder.matches(pass, user.getPassword())) {
                return new JsonResponse(false, "La contraseña es incorrecta");
            }

            String jwt = jwtProvider.generateJwtByUsername(user);
            return new JsonResponse(true, jwt);
        } catch (Exception e) {
            LOG.error("Catch login: " + e.getMessage());
            // e.printStackTrace();
            return new JsonResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error login");
        }
    }

    public JsonResponse createUserLogin(User user) {
        try {
            if (userRepository.findByUsernameAndStateTrue(user.getUsername()).isPresent()) {
                return new JsonResponse(false, "El usuario ya existe");
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new JsonResponse(true, "Usuario creado");
        } catch (Exception e) {
            return new JsonResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // actualizar contraseña del usuario

    public JsonResponse updatePassword(String username, String password) {
        try {
            // Buscar el usuario por nombre de usuario y estado activo
            Optional<User> userOptional = userRepository.findByUsernameAndStateTrue(username);
            LOG.info("Usuario encontrado: " + userOptional.toString());
            // Verificar si el usuario existe
            if (userOptional.isEmpty()) {
                return new JsonResponse(false, "El usuario no está registrado");
            }


            User user = userOptional.get();

            LOG.info("Usuario encontrado: " + user.toString());
            // Codificar la nueva contraseña
            String encodedPassword = passwordEncoder.encode(password);

            LOG.info("Contraseña codificada: " + encodedPassword);
            // Actualizar la contraseña del usuario
            LOG.info("Contraseña actual: " + user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);

            // Retornar respuesta exitosa
            return new JsonResponse(true, "Contraseña actualizada correctamente");
        } catch (Exception e) {
            // Manejo de errores
            return new JsonResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar la contraseña: " + e.getMessage());
        }
    }

    public JsonResponse updateFirstAccess(String username) {
        try {
            // Buscar el usuario por nombre de usuario y estado activo
            Optional<User> userOptional = userRepository.findByUsernameAndStateTrue(username);
            // Verificar si el usuario existe
            if (userOptional.isEmpty()) {
                return new JsonResponse(false, "El usuario no está registrado");
            }

            User user = userOptional.get();
            user.setFirstLogin(false);
            userRepository.save(user);

            // Retornar respuesta exitosa
            return new JsonResponse(true, "Primer acceso actualizado correctamente");
        } catch (Exception e) {
            // Manejo de errores
            return new JsonResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar el primer acceso: " + e.getMessage());
        }
    }

}
