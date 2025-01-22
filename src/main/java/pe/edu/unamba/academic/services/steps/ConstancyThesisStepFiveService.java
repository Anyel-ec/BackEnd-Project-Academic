package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ConstancyThesisStepFive;
import pe.edu.unamba.academic.repositories.steps.ChangeAdvisorRepository;
import pe.edu.unamba.academic.repositories.steps.ConstancyThesisStepFiveRepository;
import pe.edu.unamba.academic.repositories.steps.JuryRecompositionRepository;
import pe.edu.unamba.academic.models.steps.JuryNotificationsStepSix;
import pe.edu.unamba.academic.repositories.steps.PassageExpansionRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.repository.util.ClassUtils.ifPresent;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConstancyThesisStepFiveService {
    private final ConstancyThesisStepFiveRepository constancyThesisStepFiveRepository;
    private final JuryNotificationsStepSixService juryNotificationsStepSixService;
    private final JuryRecompositionRepository juryRecompositionRepository;
    private final ChangeAdvisorRepository changeAdvisorRepository;
    private final PassageExpansionRepository passageExpansionRepository;
    private final TitleReservationStepOneService titleReservationStepOneService;

    public List<ConstancyThesisStepFive> getAllConstancyThesis() {
        return constancyThesisStepFiveRepository.findAll();
    }

    public Optional<ConstancyThesisStepFive> getConstancyThesisById(Long id) {
        return constancyThesisStepFiveRepository.findById(id);
    }

    public ConstancyThesisStepFive createConstancyThesis(ConstancyThesisStepFive constancyThesisStepFive) {
        return constancyThesisStepFiveRepository.save(constancyThesisStepFive);
    }

    public Optional<ConstancyThesisStepFive> getConstancyByStudentCode(String studentCode) {
        return constancyThesisStepFiveRepository.findByAnyStudentCodeNative(studentCode);
    }

    public Optional<ConstancyThesisStepFive> updateConstancyThesis(Long id, ConstancyThesisStepFive updatedConstancyThesis) {
        log.info("Actualizando la constancia de tesis con ID: {}", id);
        return constancyThesisStepFiveRepository.findById(id).map(existingThesis -> {
            if (updatedConstancyThesis.getReportReviewStepFour() != null) {
                existingThesis.setReportReviewStepFour(updatedConstancyThesis.getReportReviewStepFour());
            }
            if (updatedConstancyThesis.getObservations() != null) {
                existingThesis.setObservations(updatedConstancyThesis.getObservations());
            }
            existingThesis.setAplicationNumber(updatedConstancyThesis.getAplicationNumber());
            existingThesis.setRegistrationNumber(updatedConstancyThesis.getRegistrationNumber());
            existingThesis.setMeetsRequirements(updatedConstancyThesis.isMeetsRequirements());
            existingThesis.setDisable(updatedConstancyThesis.isDisable());
            if (updatedConstancyThesis.isMeetsRequirements()) {
                JuryNotificationsStepSix newJuryNotifications = new JuryNotificationsStepSix();
                newJuryNotifications.setConstancyThesisStepFive(existingThesis);
                newJuryNotifications.setMeetRequirements(false);
                juryNotificationsStepSixService.saveJuryNotification(newJuryNotifications);
            }

            juryRecompositionRepository.findByProjectApprovalStepTwoId(existingThesis.getReportReviewStepFour().getJuryAppointmentStepThree().getProjectApprovalStepTwo().getId()).ifPresent(juryRecomposition -> {
                log.info("Eliminando JuryRecomposition con ID: {}", juryRecomposition.getId());
                juryRecompositionRepository.delete(juryRecomposition);
            });
            changeAdvisorRepository.findByTitleReservationStepOneId(existingThesis.getReportReviewStepFour().getJuryAppointmentStepThree().getProjectApprovalStepTwo().getTitleReservationStepOne().getId()).ifPresent(changeAdvisor -> {
                log.info("Eliminando ChangeAdvisor con ID: {}", changeAdvisor.getId());
                changeAdvisorRepository.delete(changeAdvisor);
            });
            passageExpansionRepository.findByTitleReservationStepOneId(existingThesis.getReportReviewStepFour().getJuryAppointmentStepThree().getProjectApprovalStepTwo().getTitleReservationStepOne().getId()).ifPresent(passageExpansion -> {
                log.info("Eliminando PassageExpansion con ID: {}", passageExpansion.getId());
                passageExpansionRepository.delete(passageExpansion);
            });
            return constancyThesisStepFiveRepository.save(existingThesis);
        });
    }


    public void removePDFDocumentFromConstancy(ConstancyThesisStepFive constancyThesis) {
        constancyThesis.setPdfDocument(null);  // Eliminara la referencia al documento PDF
        constancyThesisStepFiveRepository.save(constancyThesis);  // Guardar la actualización en la base de datos
    }

    public boolean checkIfPDFExists(Long reservationId) {
        return constancyThesisStepFiveRepository.findById(reservationId).map(ConstancyThesisStepFive::getPdfDocument).isPresent();
    }

    public boolean deleteConstancyThesisById(Long id) {
        if (constancyThesisStepFiveRepository.existsById(id)) {
            constancyThesisStepFiveRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
