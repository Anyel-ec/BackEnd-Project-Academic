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
    import pe.edu.unamba.academic.dto.response.JsonResponseDto;
    import pe.edu.unamba.academic.models.UserInfo;
    import pe.edu.unamba.academic.models.actors.Student;
    import pe.edu.unamba.academic.models.steps.ChangeAdvisor;
    import pe.edu.unamba.academic.models.steps.PassageExpansion;
    import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;
    import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
    import pe.edu.unamba.academic.repositories.UserRepository;
    import pe.edu.unamba.academic.repositories.actors.StudentRepository;
    import pe.edu.unamba.academic.repositories.steps.ChangeAdvisorRepository;
    import pe.edu.unamba.academic.repositories.steps.PassageExpansionRepository;
    import pe.edu.unamba.academic.repositories.steps.ProjectApprovalStepTwoRepository;
    import pe.edu.unamba.academic.repositories.steps.TitleReservationStepOneRepository;
    import pe.edu.unamba.academic.services.EmailService;
    import pe.edu.unamba.academic.services.UserService;
    import pe.edu.unamba.academic.services.actors.RolService;
    
    import java.math.BigDecimal;
    import java.security.SecureRandom;
    import java.util.List;
    import java.util.Optional;
    
    @RequiredArgsConstructor
    @Service
    @Slf4j
    public class TitleReservationStepOneService {
    
        private final TitleReservationStepOneRepository titleReservationStepOneRepository;
        private final ProjectApprovalStepTwoRepository projectApprovalStepTwoRepository;
        private final ChangeAdvisorRepository changeAdvisorRepository;
        private final PassageExpansionRepository passageExpansionRepository;
        private final RolService rolService;
        private final StudentRepository studentRepository;
        private final UserRepository userRepository;
        private final UserService userService;
        private final EmailService emailService;
        private final PasswordEncoder passwordEncoder;

    
        private static final Logger LOG = LoggerFactory.getLogger(TitleReservationStepOneService.class);

        private TitleReservationStepOneRepository stepOneRepository;


        public TitleReservationStepOne getReservationByStudentCode(String studentCode) {
            return titleReservationStepOneRepository.findByAnyStudentCodeNative(studentCode)
                    .orElseThrow(() -> new RuntimeException("No se encontró una reserva para el código de estudiante: " + studentCode));
        }

        public List<TitleReservationStepOne> searchStepsByTitle(String keyword) {
            return stepOneRepository.findByTitleContaining(keyword);
        }

        public boolean doesTitleExist(String title) {
            return stepOneRepository.existsByTitle(title);
        }
        public List<TitleReservationStepOne> searchTitleReservationsByTitle(String title) {
            return titleReservationStepOneRepository.findByTitleContaining(title);
        }


        public List<TitleReservationStepOne> getAllTitleReservations() {
            LOG.info("Obteniendo todas las reservaciones de título.");
            return titleReservationStepOneRepository.findAll();
        }
        public boolean checkIfPDFExists(Long reservationId) {
            return titleReservationStepOneRepository.findById(reservationId)
                    .map(TitleReservationStepOne::getPdfDocument)
                    .isPresent();
        }
    
    
        public Optional<TitleReservationStepOne> getTitleReservationById(Long id) {
            LOG.info("Buscando reservación de título con ID: {}", id);
            return titleReservationStepOneRepository.findById(id);
        }

        @Transactional
        public TitleReservationStepOne saveTitleReservation(TitleReservationStepOne titleReservation) {
            try {
                // Retorno temprano si el título ya existe
                if (titleReservation.getTitle() != null && titleReservationStepOneRepository.existsByTitle(titleReservation.getTitle())) {
                    throw new IllegalArgumentException("Ya existe una reservación con este título.");
                }

                // Retorno temprano si los datos del estudiante son inválidos
                if (titleReservation.getStudent() == null || titleReservation.getStudent().getId() == null) {
                    LOG.error("Datos de estudiante no válidos en la reservación.");
                    return null;
                }

                // Validar y asignar el estudiante principal
                Long studentId = titleReservation.getStudent().getId();
                Optional<Student> studentOpt = studentRepository.findById(studentId);
                if (studentOpt.isEmpty()) {
                    LOG.error("Estudiante no encontrado con ID: {}", studentId);
                    return null;
                }

                Student student = studentOpt.get();
                titleReservation.setStudent(student);
                handleStudentUser(student);

                // Validar y asignar el segundo estudiante si está presente
                if (titleReservation.getStudentTwo() != null && titleReservation.getStudentTwo().getId() != null) {
                    Long studentTwoId = titleReservation.getStudentTwo().getId();
                    Optional<Student> studentTwoOpt = studentRepository.findById(studentTwoId);
                    if (studentTwoOpt.isPresent()) {
                        Student studentTwo = studentTwoOpt.get();
                        titleReservation.setStudentTwo(studentTwo);
                        handleStudentUser(studentTwo);
                    } else {
                        LOG.error("Segundo estudiante no encontrado con ID: {}", studentTwoId);
                    }
                }

                // Validar y asignar la línea de investigación
                if (titleReservation.getLineOfResearch() != null && titleReservation.getLineOfResearch().getId() != null) {
                    LOG.info("Asignando línea de investigación con ID: {}", titleReservation.getLineOfResearch().getId());
                } else {
                    LOG.warn("No se encontró una línea de investigación válida, no se asignará.");
                    titleReservation.setLineOfResearch(null);
                }

                return titleReservationStepOneRepository.save(titleReservation);

            } catch (IllegalArgumentException e) {
                LOG.error("Error de validación: {}", e.getMessage());
                throw e;
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
                    JsonResponseDto existingUser = userService.getUserByUsername(studentCode);
    
                    if (!existingUser.success()) {
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
    
                UserInfo newUser = new UserInfo();
                newUser.setUsername(studentCode);
                newUser.setPassword(passwordEncoder.encode(randomPassword));
                newUser.setFirstName(student.getFirstNames());
                newUser.setLastName(student.getLastName());
                newUser.setEmail(student.getEmail());
                newUser.setState(true);
                newUser.setFirstLogin(true);
                newUser.setRol(rolService.getRolById(2).orElseThrow(() -> new IllegalArgumentException("Rol no encontrado")));
    
                LOG.info("Intentando enviar email a {}, con código de usuario {}", student.getEmail(), studentCode);
                emailService.sendPassword(student.getEmail(), "REGISTRO", student.getFirstNames(), studentCode, randomPassword);

                userRepository.save(newUser);
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
                // Verificar unicidad solo si el título no es nulo
                if (titleReservationDetails.getTitle() != null) {
                    Optional<TitleReservationStepOne> conflictingReservation = titleReservationStepOneRepository.findByTitle(titleReservationDetails.getTitle());
                    if (conflictingReservation.isPresent() && !conflictingReservation.get().getId().equals(id)) {
                        throw new IllegalArgumentException("Ya existe una reserva con este título.");
                    }
                }
    
                existingReservation.setMeetsRequirements(titleReservationDetails.isMeetsRequirements());
                existingReservation.setObservations(titleReservationDetails.getObservations());
                existingReservation.setTitle(titleReservationDetails.getTitle());
                existingReservation.setLineOfResearch(titleReservationDetails.getLineOfResearch());
                LOG.info("Valor de projectSimilarity recibido: {}", titleReservationDetails.getProjectSimilarity());
                existingReservation.setProjectSimilarity(
                        titleReservationDetails.getProjectSimilarity() != null
                                ? new BigDecimal(titleReservationDetails.getProjectSimilarity().toString())
                                : BigDecimal.ZERO
                );
    
                TitleReservationStepOne updatedReservation = titleReservationStepOneRepository.save(existingReservation);
    
                if (updatedReservation.isMeetsRequirements()) {
                    ProjectApprovalStepTwo projectApproval = new ProjectApprovalStepTwo();
                    projectApproval.setTitleReservationStepOne(updatedReservation);
                    projectApproval.setAdviser(null);
                    projectApproval.setCoadviser(null);
                    projectApproval.setIsDisable(false);
                    projectApproval.setMeetRequirements(false);
                    projectApproval.setObservations(null);
    
                    projectApprovalStepTwoRepository.save(projectApproval);

                    PassageExpansion passageExpansion = new PassageExpansion();
                    passageExpansion.setTitleReservationStepOne(updatedReservation);
                    passageExpansion.setObservations(null);
                    passageExpansion.setMeetsRequirements(false);
                    passageExpansion.setDisable(false);
                    passageExpansionRepository.save(passageExpansion);

                    ChangeAdvisor changeAdvisor = new ChangeAdvisor();
                    changeAdvisor.setTitleReservationStepOne(updatedReservation);
                    changeAdvisor.setObservations(null);
                    changeAdvisor.setMeetsRequirements(false);
                    changeAdvisor.setDisable(false);
                    changeAdvisorRepository.save(changeAdvisor);
                }
    
                return updatedReservation;
            }).orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));
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