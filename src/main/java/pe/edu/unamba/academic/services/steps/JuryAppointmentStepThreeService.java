package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.actors.Teacher;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;
import pe.edu.unamba.academic.models.steps.ReportReviewStepFour;
import pe.edu.unamba.academic.repositories.steps.JuryAppointmentStepThreeRepository;
import pe.edu.unamba.academic.repositories.steps.ReportReviewStepFourRepository;
import pe.edu.unamba.academic.services.EmailService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class JuryAppointmentStepThreeService {

    private final JuryAppointmentStepThreeRepository juryAppointmentStepThreeRepository;
    private final ReportReviewStepFourRepository reportReviewStepFourRepository;
    private final EmailService emailService;

    public List<JuryAppointmentStepThree> getAllJuryAppointment() {
        return juryAppointmentStepThreeRepository.findAll();
    }
    public JuryAppointmentStepThree createJuryAppointment(JuryAppointmentStepThree juryAppointmentStepThree) {
        return juryAppointmentStepThreeRepository.save(juryAppointmentStepThree);
    }
    public Optional<JuryAppointmentStepThree> getJuryAppointmentById(Long id) {
        log.info("Consultando el registro con ID: {}", id);

        Optional<JuryAppointmentStepThree> juryOptional = juryAppointmentStepThreeRepository.findByIdWithRelations(id);

        juryOptional.ifPresent(j -> {
            log.info("Registro encontrado con ID: {}", j.getId());
            log.info("Cumple requisitos: {}", j.isMeetRequirements());
            log.info("Observaciones: {}", j.getObservations());

            logRelations(j); // Log de relaciones inicializadas
        });

        if (juryOptional.isEmpty()) {
            log.warn("No se encontró un registro con el ID: {}", id);
        }

        return juryOptional;
    }

    public Optional<JuryAppointmentStepThree> updateJuryAppointment(Long id, JuryAppointmentStepThree updatedJuryAppointment) {
        log.info("Iniciando actualización del registro con ID: {}", id);

        return juryAppointmentStepThreeRepository.findByIdWithRelations(id).map(jury -> {
            log.info("Estado actual del registro antes de actualizar:");
            logRelations(jury); // Logs iniciales

            // Realizar actualizaciones en el objeto cargado
            if (updatedJuryAppointment.getProjectApprovalStepTwo() != null) {
                jury.setProjectApprovalStepTwo(updatedJuryAppointment.getProjectApprovalStepTwo());
            }
            if (updatedJuryAppointment.getPresident() != null) {
                jury.setPresident(updatedJuryAppointment.getPresident());
            }
            if (updatedJuryAppointment.getFirstMember() != null) {
                jury.setFirstMember(updatedJuryAppointment.getFirstMember());
            }
            if (updatedJuryAppointment.getSecondMember() != null) {
                jury.setSecondMember(updatedJuryAppointment.getSecondMember());
            }
            if (updatedJuryAppointment.getAccessory() != null) {
                jury.setAccessory(updatedJuryAppointment.getAccessory());
            }
            if (updatedJuryAppointment.getIsDisable() != null) {
                jury.setIsDisable(updatedJuryAppointment.getIsDisable());
            }
            if (updatedJuryAppointment.isMeetRequirements() != jury.isMeetRequirements()) {
                jury.setMeetRequirements(updatedJuryAppointment.isMeetRequirements());
            }
            if (updatedJuryAppointment.getObservations() != null) {
                jury.setObservations(updatedJuryAppointment.getObservations());
            }

            log.info("Datos después de la actualización:");
            logRelations(jury); // Logs después de la actualización

            // Guardar los cambios
            JuryAppointmentStepThree savedJury = juryAppointmentStepThreeRepository.save(jury);

            // Crear paso 4 y enviar correos si es necesario
            if (savedJury.isMeetRequirements()) {
                createStepFourIfNeeded(savedJury);
                sendJuryEmails(savedJury);
            }

            return Optional.of(savedJury);
        }).orElse(Optional.empty());
    }


    private void sendJuryEmails(JuryAppointmentStepThree jury) {
        log.info("Preparando para enviar correos de jurados...");
        logRelations(jury); // Logs finales antes de enviar correos

        var student = jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudent();
        var studentTwo = jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudentTwo();

        if (student != null) {
            log.info("Enviando correo al estudiante principal: {}", student.getEmail());
            emailService.sendJurys(
                    student.getEmail(),
                    "Información de Jurados Asignados",
                    student.getFirstNames(),
                    student.getLastName(),
                    getSafeName(jury.getPresident()),
                    getSafeName(jury.getFirstMember()),
                    getSafeName(jury.getSecondMember()),
                    getSafeName(jury.getAccessory())
            );
        } else {
            log.warn("No se encontró información del estudiante principal. No se enviará correo.");
        }

        if (studentTwo != null) {
            log.info("Enviando correo al segundo estudiante: {}", studentTwo.getEmail());
            emailService.sendJurys(
                    studentTwo.getEmail(),
                    "Información de Jurados Asignados",
                    studentTwo.getFirstNames(),
                    studentTwo.getLastName(),
                    getSafeName(jury.getPresident()),
                    getSafeName(jury.getFirstMember()),
                    getSafeName(jury.getSecondMember()),
                    getSafeName(jury.getAccessory())
            );
        } else {
            log.warn("No se encontró información del segundo estudiante. No se enviará correo adicional.");
        }
    }


    private String getSafeName(Teacher teacher) {
        return teacher != null ? teacher.getFirstNames() : "No asignado";
    }

    private void createStepFourIfNeeded(JuryAppointmentStepThree jury) {
        if (reportReviewStepFourRepository.findByJuryAppointmentStepThree(jury).isEmpty()) {
            ReportReviewStepFour stepFour = new ReportReviewStepFour();
            stepFour.setJuryAppointmentStepThree(jury);
            stepFour.setMeetRequirements(false);
            reportReviewStepFourRepository.save(stepFour);
            log.info("Paso 4 creado para JuryAppointmentStepThree con id: {}", jury.getId());
        }
    }

    private void logRelations(JuryAppointmentStepThree jury) {
        log.info("Presidente: {}", jury.getPresident() != null ? jury.getPresident().getFirstNames() : "No asignado");
        log.info("Primer Miembro: {}", jury.getFirstMember() != null ? jury.getFirstMember().getFirstNames() : "No asignado");
        log.info("Segundo Miembro: {}", jury.getSecondMember() != null ? jury.getSecondMember().getFirstNames() : "No asignado");
        log.info("Accesitario: {}", jury.getAccessory() != null ? jury.getAccessory().getFirstNames() : "No asignado");

        // Verificar inicialización de relaciones con Hibernate
        log.info("Inicialización - Presidente: {}", Hibernate.isInitialized(jury.getPresident()));
        log.info("Inicialización - Primer Miembro: {}", Hibernate.isInitialized(jury.getFirstMember()));
        log.info("Inicialización - Segundo Miembro: {}", Hibernate.isInitialized(jury.getSecondMember()));
        log.info("Inicialización - Accesitario: {}", Hibernate.isInitialized(jury.getAccessory()));
    }

    public boolean deleteJuryAppointment(Long id) {
        if (juryAppointmentStepThreeRepository.existsById(id)) {
            juryAppointmentStepThreeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
