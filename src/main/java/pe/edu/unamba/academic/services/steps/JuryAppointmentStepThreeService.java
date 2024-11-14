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

    public Optional<JuryAppointmentStepThree> getJuryAppointmentById(Long id) {
        return juryAppointmentStepThreeRepository.findById(id);
    }
    public JuryAppointmentStepThree createJuryAppointment(JuryAppointmentStepThree juryAppointmentStepThree) {
        return juryAppointmentStepThreeRepository.save(juryAppointmentStepThree);
    }

    public Optional<JuryAppointmentStepThree> updateJuryAppointment(Long id, JuryAppointmentStepThree updatedJuryAppointment) {
        return juryAppointmentStepThreeRepository.findById(id).map(jury -> {
            // Actualizar solo si los campos no son nulos
            if (updatedJuryAppointment.getProjectApprovalStepTwo() != null) {
                jury.setProjectApprovalStepTwo(updatedJuryAppointment.getProjectApprovalStepTwo());
            }

            if (updatedJuryAppointment.getPresident() != null) {
                jury.setPresident(updatedJuryAppointment.getPresident());
                log.info("Presidente asignado: {}", updatedJuryAppointment.getPresident().getFirstNames());
            } else {
                log.warn("Presidente no asignado");
            }

            if (updatedJuryAppointment.getFirstMember() != null) {
                jury.setFirstMember(updatedJuryAppointment.getFirstMember());
                log.info("Primer miembro asignado: {}", updatedJuryAppointment.getFirstMember().getFirstNames());
            } else {
                log.warn("Primer miembro no asignado");
            }

            if (updatedJuryAppointment.getSecondMember() != null) {
                jury.setSecondMember(updatedJuryAppointment.getSecondMember());
                log.info("Segundo miembro asignado: {}", updatedJuryAppointment.getSecondMember().getFirstNames());
            } else {
                log.warn("Segundo miembro no asignado");
            }

            if (updatedJuryAppointment.getAccessory() != null) {
                jury.setAccessory(updatedJuryAppointment.getAccessory());
                log.info("Accesitario asignado: {}", updatedJuryAppointment.getAccessory().getFirstNames());
            } else {
                log.warn("Accesitario no asignado");
            }

            if (updatedJuryAppointment.getIsDisable() != null) {
                jury.setIsDisable(updatedJuryAppointment.getIsDisable());
            }
            jury.setMeetRequirements(updatedJuryAppointment.isMeetRequirements());

            if (updatedJuryAppointment.getObservations() != null) {
                jury.setObservations(updatedJuryAppointment.getObservations());
            }

            // Guardar cambios
            juryAppointmentStepThreeRepository.save(jury);

            // Forzar la inicialización de las relaciones para asegurarse de que los datos están disponibles
            Hibernate.initialize(jury.getPresident());
            Hibernate.initialize(jury.getFirstMember());
            Hibernate.initialize(jury.getSecondMember());
            Hibernate.initialize(jury.getAccessory());

            // Verificación y creación del paso 4
            if (jury.isMeetRequirements()) {
                Optional<ReportReviewStepFour> existingStepFour = reportReviewStepFourRepository.findByJuryAppointmentStepThree(jury);
                if (existingStepFour.isEmpty()) {
                    ReportReviewStepFour reportReviewStepFour = new ReportReviewStepFour();
                    reportReviewStepFour.setJuryAppointmentStepThree(jury);
                    reportReviewStepFour.setMeetRequirements(false);
                    reportReviewStepFourRepository.save(reportReviewStepFour);
                    log.info("Paso 4 creado para JuryAppointmentStepThree con id: {}", jury.getId());
                }

                // Enviar correo si los datos de los jurados están completos
                if (jury.getPresident() != null && jury.getFirstMember() != null && jury.getSecondMember() != null && jury.getAccessory() != null) {
                    String studentEmail = jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudent().getEmail();
                    String studentName = jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudent().getFirstNames();
                    String username = jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudent().getLastName();

                    emailService.sendJurys(
                            studentEmail,
                            "Información de Jurados Asignados",
                            studentName,
                            username,
                            jury.getPresident(),
                            jury.getFirstMember(),
                            jury.getSecondMember(),
                            jury.getAccessory()
                    );

                    // Enviar correo al segundo estudiante si existe
                    if (jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudentTwo() != null) {
                        String studentTwoEmail = jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudentTwo().getEmail();
                        String studentTwoName = jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudentTwo().getFirstNames();
                        String usernameTwo = jury.getProjectApprovalStepTwo().getTitleReservationStepOne().getStudentTwo().getLastName();

                        emailService.sendJurys(
                                studentTwoEmail,
                                "Información de Jurados Asignados",
                                studentTwoName,
                                usernameTwo,
                                jury.getPresident(),
                                jury.getFirstMember(),
                                jury.getSecondMember(),
                                jury.getAccessory()
                        );
                    }
                } else {
                    log.warn("No se enviará el correo porque los datos de los jurados están incompletos.");
                }
            }

            return Optional.of(jury);
        }).orElse(Optional.empty());
    }



    public boolean deleteJuryAppointment(Long id) {
        if(juryAppointmentStepThreeRepository.existsById(id)) {
            juryAppointmentStepThreeRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
