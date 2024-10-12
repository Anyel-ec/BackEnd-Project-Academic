package pe.edu.unamba.academic.services.steps;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.User;
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
@Slf4j
public class TitleReservationStepOneService {

    private final TitleReservationStepOneRepository titleReservationStepOneRepository;
    private final RolService rolService;
    private final StudentRepository studentRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger LOG = LoggerFactory.getLogger(TitleReservationStepOneService.class);

    public List<TitleReservationStepOne> getAllTitleReservations() {
        LOG.info("Obteniendo todas las reservaciones de título.");
        return titleReservationStepOneRepository.findAll();
    }

    public Optional<TitleReservationStepOne> getTitleReservationById(Long id) {
        LOG.info("Buscando reservación de título con ID: {}", id);
        return titleReservationStepOneRepository.findById(id);
    }

    @Transactional
    public TitleReservationStepOne saveTitleReservation(TitleReservationStepOne titleReservation) {
        try {
            // Validar y asignar el estudiante a la reservación
            if (titleReservation.getStudent() != null && titleReservation.getStudent().getId() != null) {
                Long studentId = titleReservation.getStudent().getId();

                Optional<Student> studentOpt = studentRepository.findById(studentId);
                if (studentOpt.isPresent()) {
                    Student student = studentOpt.get();
                    titleReservation.setStudent(student);

                    // Manejar usuario del estudiante
                    handleStudentUser(student);

                    // Manejar segundo estudiante, si existe
                    if (titleReservation.getStudentTwo() != null && titleReservation.getStudentTwo().getId() != null) {
                        Long studentTwoId = titleReservation.getStudentTwo().getId();
                        Optional<Student> studentTwoOpt = studentRepository.findById(studentTwoId);
                        if (studentTwoOpt.isPresent()) {
                            Student studentTwo = studentTwoOpt.get();
                            titleReservation.setStudentTwo(studentTwo);
                            handleStudentUser(studentTwo);  // Asegurar que se maneje el usuario para el segundo estudiante
                        } else {
                            LOG.error("Segundo estudiante no encontrado con ID: {}", studentTwoId);
                        }
                    }

                    // Validar la línea de investigación (LineResearch)
                    if (titleReservation.getLineOfResearch() != null && titleReservation.getLineOfResearch().getId() != null) {
                        LOG.info("Asignando línea de investigación con ID: {}", titleReservation.getLineOfResearch().getId());
                    } else {
                        LOG.warn("No se encontró una línea de investigación válida, no se asignará.");
                        titleReservation.setLineOfResearch(null);  // No asignar ninguna línea de investigación si no es válida
                    }

                    // Guardar la reservación
                    return titleReservationStepOneRepository.save(titleReservation);
                } else {
                    LOG.error("Estudiante no encontrado con ID: {}", studentId);
                    return null;
                }
            } else {
                LOG.error("Datos de estudiante no válidos en la reservación.");
                return null;
            }
        } catch (Exception e) {
            LOG.error("Error al guardar la reservación: {}", e.getMessage());
            return null;
        }
    }


    // Manejo del usuario para un estudiante
    @Transactional
    protected void handleStudentUser(Student student) {
        LOG.info("Iniciando manejo de usuario para el estudiante con código: {}", student.getStudentCode());
        try {
            String studentCode = student.getStudentCode();
            if (studentCode != null && !studentCode.isEmpty()) {
                JsonResponse existingUser = userService.getUserByUsername(studentCode);

                if (!existingUser.isPresent()) {
                    LOG.info("No existe un usuario para el estudiante con código {}, procediendo a crear uno.", studentCode);
                    createUserForStudent(student);
                } else {
                    LOG.info("El usuario ya existe para el estudiante con código: {}", studentCode);
                }
            } else {
                LOG.error("El estudiante con ID {} no tiene un código válido.", student.getId());
            }
        } catch (Exception e) {
            LOG.error("Error al manejar el usuario del estudiante: {}", e.getMessage());
        }
        LOG.info("Finalizado manejo de usuario para el estudiante con código: {}", student.getStudentCode());
    }

    // Método para crear un nuevo usuario para un estudiante
    @Transactional
    protected void createUserForStudent(Student student) {
        try {
            String studentCode = student.getStudentCode();
            String randomPassword = generateRandomPassword(8);

            User newUser = new User();
            newUser.setUsername(studentCode);
            newUser.setPassword(passwordEncoder.encode(randomPassword));
            newUser.setFirstName(student.getFirstNames());
            newUser.setLastName(student.getLastName());
            newUser.setEmail(student.getEmail());
            newUser.setState(true);
            newUser.setRol(rolService.getRolById(2).orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")));

            LOG.info("Intentando enviar email a {}, con código de usuario {}", student.getEmail(), studentCode);
            emailService.sendEmail(student.getEmail(), "REGISTRO", student.getFirstNames(), studentCode, randomPassword);

            userService.saveUser(newUser);
            LOG.info("Usuario creado y email enviado para el estudiante con código: {}", studentCode);
        } catch (DataAccessException e) {
            LOG.error("Error de acceso a base de datos al crear usuario para el estudiante: {}", e.getMessage());
        } catch (MailException e) {
            LOG.error("Error al enviar email al estudiante: {}", e.getMessage());
        } catch (Exception e) {
            LOG.error("Error general al crear usuario para el estudiante: {}", e.getMessage());
        }
    }


    public boolean deleteTitleReservation(Long id) {
        Optional<TitleReservationStepOne> reservationOpt = titleReservationStepOneRepository.findById(id);
        if (reservationOpt.isPresent()) {
            titleReservationStepOneRepository.deleteById(id);
            LOG.info("Reservación con ID {} eliminada correctamente.", id);
            return true;
        } else {
            LOG.warn("Reservación con ID {} no encontrada.", id);
            return false;
        }
    }

    @Transactional
    public TitleReservationStepOne updateTitleReservation(Long id, TitleReservationStepOne titleReservationDetails) {
        return titleReservationStepOneRepository.findById(id).map(existingReservation -> {
            // Actualizar campos relevantes
            existingReservation.setMeetsRequirements(titleReservationDetails.isMeetsRequirements());
            existingReservation.setObservations(titleReservationDetails.getObservations());

            if (titleReservationDetails.getLineOfResearch() != null && titleReservationDetails.getLineOfResearch().getId() != null) {
                existingReservation.setLineOfResearch(titleReservationDetails.getLineOfResearch());
            } else {
                existingReservation.setLineOfResearch(null); // Eliminar la línea de investigación si no se proporciona
            }

            // Guardar cambios y devolver la entidad actualizada
            return titleReservationStepOneRepository.save(existingReservation);
        }).orElse(null);
    }



    // Método para eliminar la referencia al documento PDF dentro de una reservación de título
    public void removePDFDocumentFromReservation(TitleReservationStepOne titleReservation) {
        titleReservation.setPdfDocument(null);  // Eliminar la referencia al documento PDF
        titleReservationStepOneRepository.save(titleReservation);  // Guardar la actualización en la base de datos
    }
    // Método para generar una contraseña aleatoria
    private String generateRandomPassword(int length) {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }

        return password.toString();
    }
}
