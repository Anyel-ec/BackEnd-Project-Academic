package pe.edu.unamba.academic.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.User;
import pe.edu.unamba.academic.models.messages.response.JsonResponse;
import pe.edu.unamba.academic.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).get();
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRol(userDetails.getRol());
        user.setResearchUnit(userDetails.getResearchUnit());
        return userRepository.save(user);
    }
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);


    public JsonResponse validateUserJWT(String token) {
        Optional<User> user;
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            LOG.info("Username " + username);
            user = userRepository.findByUsernameAndStateTrue(username);
            if (!user.isPresent()) {
                return new JsonResponse(false, HttpStatus.UNAUTHORIZED.value(),"inhautorized");
            }

        } catch (Exception e) {
            return new JsonResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

        }
        return new JsonResponse(true, "Autorizado", user.get());
    }

    public JsonResponse createUser(User user) {
        try {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new JsonResponse(true, "Usuario creado");
        } catch (Exception e) {
            return new JsonResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    public JsonResponse getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsernameAndStateTrue(username);
        if (!user.isPresent()) {
            return new JsonResponse(false, "El usuario no registrado");
        }
        return new JsonResponse(true, user.get());
    }
}