package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.User;
import pe.edu.unamba.academic.models.actors.Rol;
import pe.edu.unamba.academic.models.actors.Student;
import pe.edu.unamba.academic.models.messages.response.JsonResponse;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
import pe.edu.unamba.academic.repositories.actors.StudentRepository;
import pe.edu.unamba.academic.repositories.steps.TitleReservationStepOneRepository;
import pe.edu.unamba.academic.services.EmailService;
import pe.edu.unamba.academic.services.UserService;
import pe.edu.unamba.academic.services.actors.RolService;
import java.security.SecureRandom;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TitleReservationStepOneService {

    private final TitleReservationStepOneRepository titleReservationStepOneRepository;
    private final RolService rolService;
    private final StudentRepository studentRepository;
        private final UserService userService; // Inyección del UserService para manejar la creación de usuarios
    private final EmailService emailService;
    private static final Logger LOG = LoggerFactory.getLogger(TitleReservationStepOneService.class);
    private final PasswordEncoder passwordEncoder;

    /**
     * Obtiene todas las reservaciones de título del paso uno.
     *
     * @return Lista de todas las reservaciones.
     */
    public List<TitleReservationStepOne> getAllTitleReservations() {
        LOG.info("Obteniendo todas las reservaciones de título.");
        return titleReservationStepOneRepository.findAll();
    }

    /**
     * Busca una reservación de título por su ID.
     *
     * @param id El ID de la reservación.
     * @return Un Optional conteniendo la reservación si existe, de lo contrario, vacío.
     */
    public Optional<TitleReservationStepOne> getTitleReservationById(Long id) {
        LOG.info("Buscando reservación de título con ID: {}", id);
        return titleReservationStepOneRepository.findById(id);
    }

    /**
     * Guarda una nueva reservación de título, asignando el estudiante si ya existe en la base de datos,
     * y crea el usuario si no existe.
     *
     * @param titleReservation La reservación de título a guardar.
     * @return La reservación guardada o null si hubo un error.
     */


    public TitleReservationStepOne saveTitleReservation(TitleReservationStepOne titleReservation) {
        try {
            // Asegurarse de que se envía un estudiante con ID
            if (titleReservation.getStudent() != null && titleReservation.getStudent().getId() != null) {
                Long studentId = titleReservation.getStudent().getId();

                // Recuperar al estudiante completo desde la base de datos
                Optional<Student> studentOpt = studentRepository.findById(studentId);
                if (studentOpt.isPresent()) {
                    Student student = studentOpt.get();
                    LOG.info("Estudiante encontrado: {}", student);

                    // Asignar el estudiante completo a la reserva
                    titleReservation.setStudent(student);

                    // Verificar si el código del estudiante está disponible
                    String studentCode = student.getStudentCode();
                    if (studentCode == null || studentCode.isEmpty()) {
                        LOG.error("El estudiante con ID {} no tiene un código asignado.", studentId);
                        return null; // Detener el proceso si el estudiante no tiene código
                    }

                    // Verificar si el usuario ya existe usando el código de estudiante como username
                    JsonResponse existingUser = userService.getUserByUsername(studentCode);
                    if (!existingUser.isPresent()) {
                        // Si el usuario no existe, creamos uno nuevo
                        User newUser = new User();
                        newUser.setUsername(studentCode);  // Usamos el código de estudiante como nombre de usuario
                        newUser.setFirstName(student.getFirstNames());
                        newUser.setLastName(student.getLastName());
                        newUser.setEmail(student.getEmail());

                        // Generar contraseña aleatoria
                        String randomPassword = generateRandomPassword(8); // Genera una contraseña de 8 caracteres
                        newUser.setPassword(passwordEncoder.encode(randomPassword));
                        newUser.setState(true);  // Estado activo

                        // Asignar el rol por defecto (ID=2, estudiante)
                        Rol studentRole = rolService.getRolById(2).orElseThrow(() ->
                                new IllegalArgumentException("Rol de estudiante no encontrado."));
                        newUser.setRol(studentRole); // Asigna el rol directamente

                        // Asignar la unidad de investigación por defecto (ID=1)

                        // Establecer el primer login como 1 por defecto
                        newUser.setFirstLogin(true); // Configurar que sea el primer login

                        // Guardar el nuevo usuario
                        emailService.sendEmail(student.getEmail(), "REGISTRO", student.getFirstNames(), studentCode, randomPassword);

                        userService.saveUser(newUser);
                        LOG.info("Los datos para el envío del correo son: {}", student.getEmail());

                        LOG.info("Nuevo usuario creado para el estudiante con ID {}: {}", student.getId(), studentCode);
                    } else {
                        LOG.info("El usuario ya existe para el estudiante con ID {}: {}", student.getId(), studentCode);
                    }

                    // Guardar la reservación de título con el estudiante completo
                    return titleReservationStepOneRepository.save(titleReservation);

                } else {
                    LOG.error("Estudiante con ID {} no encontrado en la base de datos.", studentId);
                    return null; // Si el estudiante no es encontrado, detener el proceso
                }
            } else {
                LOG.error("El objeto TitleReservationStepOne no tiene un estudiante válido.");
                return null; // Detener el proceso si no se proporciona un estudiante con ID
            }
        } catch (Exception e) {
            LOG.error("Error al guardar la reservación de título: {}", e.getMessage());
            return null; // Manejar cualquier excepción
        }
    }

    // Método para generar una contraseña aleatoria
    private String generateRandomPassword(int length) {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(index));
        }

        return password.toString();
    }



    /**
     * Elimina una reservación de título por su ID.
     *
     * @param id El ID de la reservación.
     */
    public void deleteTitleReservation(Long id) {
        Optional<TitleReservationStepOne> reservation = titleReservationStepOneRepository.findById(id);
        if (reservation.isPresent()) {
            titleReservationStepOneRepository.deleteById(id);
            LOG.info("Reservación con ID {} eliminada correctamente", id);
        } else {
            LOG.warn("Reservación con ID {} no encontrada", id);
        }
    }

    /**
     * Actualiza una reservación de título con nuevos detalles.
     *
     * @param id El ID de la reservación a actualizar.
     * @param titleReservationDetails Los detalles actualizados.
     * @return La reservación actualizada.
     */
    public TitleReservationStepOne updateTitleReservation(Long id, TitleReservationStepOne titleReservationDetails) {
        Optional<TitleReservationStepOne> titleReservationOpt = titleReservationStepOneRepository.findById(id);
        if (titleReservationOpt.isPresent()) {
            TitleReservationStepOne titleReservation = titleReservationOpt.get();
            titleReservation.setMeetsRequirements(titleReservationDetails.isMeetsRequirements());
            titleReservation.setObservations(titleReservationDetails.getObservations());
            LOG.info("Reservación de título con ID {} actualizada correctamente.", id);
            return titleReservationStepOneRepository.save(titleReservation);
        } else {
            LOG.warn("Reservación con ID {} no encontrada para actualización", id);
            return null;
        }
    }


}
