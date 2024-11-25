package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.exceptions.ResourceNotFoundException;
import pe.edu.unamba.academic.models.steps.JuryNotificationsStepSix;
import pe.edu.unamba.academic.models.steps.ThesisApprovalStepSeven;
import pe.edu.unamba.academic.repositories.steps.JuryNotificationsStepSixRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JuryNotificationsStepSixService {

    private final JuryNotificationsStepSixRepository stepSixRepository;
    private final ThesisApprovalStepSevenService thesisApprovalStepSevenService;
    public List<JuryNotificationsStepSix> getJuryNotifications() {
        return stepSixRepository.findAll();
    }

    public Optional<JuryNotificationsStepSix> getJuryNotification(Long id) {
        return Optional.ofNullable(
                stepSixRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Jury Notification not found with id: {0}", id))
        );
    }

    public void saveJuryNotification(JuryNotificationsStepSix juryNotification) {
        if (juryNotification.getId() != null) {
            throw new IllegalArgumentException("New Jury Notification should not have an ID. Use the update method instead.");
        }
        stepSixRepository.save(juryNotification);
    }

    public void updateJuryNotification(JuryNotificationsStepSix juryNotification) {
        // Verifica si el registro existe
        JuryNotificationsStepSix existingNotification = stepSixRepository.findById(juryNotification.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update: Jury Notification not found with id: {0}", juryNotification.getId()));

            existingNotification.setMeetRequirements(juryNotification.isMeetRequirements());

        if (juryNotification.getObservations() != null) {
            existingNotification.setObservations(juryNotification.getObservations());
        }
        if (juryNotification.getThesisDate() != null) {
            existingNotification.setThesisDate(juryNotification.getThesisDate());
        }

        if (juryNotification.isMeetRequirements()){
            ThesisApprovalStepSeven newThesisApproval = new ThesisApprovalStepSeven();
            newThesisApproval.setJuryNotificationsStepSix(existingNotification);
            newThesisApproval.setMeetRequirements(false);
            thesisApprovalStepSevenService.saveApproval(newThesisApproval);
        }

        // Guarda el objeto combinado
        stepSixRepository.save(existingNotification);
    }


    public void deleteJuryNotification(Long id) {
        if (!stepSixRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Jury Notification not found with id: {0}", id);
        }
        stepSixRepository.deleteById(id);
    }
}
