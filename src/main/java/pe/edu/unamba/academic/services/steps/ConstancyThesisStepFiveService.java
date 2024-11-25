package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ConstancyThesisStepFive;
import pe.edu.unamba.academic.repositories.steps.ConstancyThesisStepFiveRepository;
import pe.edu.unamba.academic.models.steps.JuryNotificationsStepSix;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConstancyThesisStepFiveService {
    private final ConstancyThesisStepFiveRepository constancyThesisStepFiveRepository;
    private final JuryNotificationsStepSixService juryNotificationsStepSixService;

    public List<ConstancyThesisStepFive> getAllConstancyThesis() {
        return constancyThesisStepFiveRepository.findAll();
    }
    public Optional<ConstancyThesisStepFive> getConstancyThesisById(Long id) {
        return constancyThesisStepFiveRepository.findById(id);
    }
    public ConstancyThesisStepFive createConstancyThesis(ConstancyThesisStepFive constancyThesisStepFive) {
        return constancyThesisStepFiveRepository.save(constancyThesisStepFive);
    }
    public Optional<ConstancyThesisStepFive> updateConstancyThesis(Long id, ConstancyThesisStepFive updatedConstancyThesis) {
        log.info("Actualizando la constancia de tesis con ID: {}", id);
        return constancyThesisStepFiveRepository.findById(id).map(existingThesis -> {
                    if (updatedConstancyThesis.getReportReviewStepFour() != null){
                        existingThesis.setReportReviewStepFour(updatedConstancyThesis.getReportReviewStepFour());
                    }
                    if (updatedConstancyThesis.getObservations() != null){
                        existingThesis.setObservations(updatedConstancyThesis.getObservations());
                    }
                    existingThesis.setMeetsRequirements(updatedConstancyThesis.isMeetsRequirements());
                    existingThesis.setDisable(updatedConstancyThesis.isDisable());
                    if(updatedConstancyThesis.isMeetsRequirements()){
                        JuryNotificationsStepSix newJuryNotifications = new JuryNotificationsStepSix();
                        newJuryNotifications.setConstancyThesisStepFive(existingThesis);
                        newJuryNotifications.setMeetRequirements(false);
                        juryNotificationsStepSixService.saveJuryNotification(newJuryNotifications);
                    }
                    return constancyThesisStepFiveRepository.save(existingThesis);
                });
    }


    public void removePDFDocumentFromConstancy(ConstancyThesisStepFive constancyThesis) {
        constancyThesis.setPdfDocument(null);  // Eliminara la referencia al documento PDF
        constancyThesisStepFiveRepository.save(constancyThesis);  // Guardar la actualización en la base de datos
    }
    public boolean checkIfPDFExists(Long reservationId) {
        return constancyThesisStepFiveRepository.findById(reservationId)
                .map(ConstancyThesisStepFive::getPdfDocument)
                .isPresent();
    }
    public boolean deleteConstancyThesisById(Long id) {
        if(constancyThesisStepFiveRepository.existsById(id)) {
            constancyThesisStepFiveRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
