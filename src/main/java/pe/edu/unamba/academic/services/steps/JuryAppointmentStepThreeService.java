package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
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

    public Optional<JuryAppointmentStepThree> getJuryAppointmentById(Long id) {
        return juryAppointmentStepThreeRepository.findById(id);
    }

    public JuryAppointmentStepThree createJuryAppointment(JuryAppointmentStepThree juryAppointmentStepThree) {
        return juryAppointmentStepThreeRepository.save(juryAppointmentStepThree);
    }
    public Optional<JuryAppointmentStepThree> getJuryByAnyStudentCode(String studentCode) {
        return juryAppointmentStepThreeRepository.findByAnyStudentCode(studentCode);
    }
    public void createStepFourIfNeeded(JuryAppointmentStepThree jury) {
        if (reportReviewStepFourRepository.findByJuryAppointmentStepThree(jury).isEmpty()) {
            ReportReviewStepFour stepFour = new ReportReviewStepFour();
            stepFour.setJuryAppointmentStepThree(jury);
            stepFour.setMeetRequirements(false);
            reportReviewStepFourRepository.save(stepFour);
            log.info("Paso 4 creado para JuryAppointmentStepThree con id: {}", jury.getId());
        }
    }

    public void sendJuryEmails(JuryAppointmentStepThree jury) {
        try {
            // Inicializar relaciones para asegurarnos de que estén disponibles
            Hibernate.initialize(jury.getPresident());
            Hibernate.initialize(jury.getFirstMember());
            Hibernate.initialize(jury.getSecondMember());
            Hibernate.initialize(jury.getAccessory());
            Hibernate.initialize(jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudent());
            Hibernate.initialize(jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudentTwo());

            // Verificar si las relaciones están inicializadas correctamente
            log.info("Inicialización - Presidente: {}", Hibernate.isInitialized(jury.getPresident()));
            log.info("Inicialización - Primer Miembro: {}", Hibernate.isInitialized(jury.getFirstMember()));
            log.info("Inicialización - Segundo Miembro: {}", Hibernate.isInitialized(jury.getSecondMember()));
            log.info("Inicialización - Accesitario: {}", Hibernate.isInitialized(jury.getAccessory()));

            var student = jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudent();
            var studentTwo = jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudentTwo();

            if (student != null) {
                log.info("Enviando correo al estudiante: {}", student.getEmail());
                emailService.sendJurys(
                        student.getEmail(),
                        "Información de Jurados Asignados",
                        student.getFirstNames(),
                        student.getLastName(),
                        jury.getPresident().getFirstNames(),
                        jury.getFirstMember().getFirstNames(),
                        jury.getSecondMember().getFirstNames(),
                        jury.getAccessory().getFirstNames()
                );
            } else {
                log.warn("El estudiante principal no está disponible, no se enviará correo.");
            }

            if (studentTwo != null) {
                log.info("Enviando correo al segundo estudiante: {}", studentTwo.getEmail());
                emailService.sendJurys(
                        studentTwo.getEmail(),
                        "Información de Jurados Asignados",
                        studentTwo.getFirstNames(),
                        studentTwo.getLastName(),
                        jury.getPresident().getFirstNames(),
                        jury.getFirstMember().getFirstNames(),
                        jury.getSecondMember().getFirstNames(),
                        jury.getAccessory().getFirstNames()
                );
            } else {
                log.warn("No hay segundo estudiante, no se enviará correo adicional.");
            }

        } catch (Exception e) {
            log.error("Error al enviar correos de jurados: {}", e.getMessage(), e);
        }
    }

    public boolean deleteJuryAppointment(Long id) {
        if (juryAppointmentStepThreeRepository.existsById(id)) {
            juryAppointmentStepThreeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}