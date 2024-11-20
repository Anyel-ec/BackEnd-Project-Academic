package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.exceptions.ResourceNotFoundException;
import pe.edu.unamba.academic.models.steps.JuryNotificationsStepSix;
import pe.edu.unamba.academic.repositories.steps.JuryNotificationsStepSixRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JuryNotificationsStepSixService {

    private final JuryNotificationsStepSixRepository stepSixRepository;

    public List<JuryNotificationsStepSix> getJuryNotifications() {
        return stepSixRepository.findAll();
    }

    public Optional<JuryNotificationsStepSix> getJuryNotification(Long id) {
        return Optional.ofNullable(
                stepSixRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Jury Notification not found with id: {0}", id))
        );
    }

    public void saveOrUpdateJuryNotification(JuryNotificationsStepSix juryNotification) {
        if (juryNotification.getId() != null && !stepSixRepository.existsById(juryNotification.getId())) {
            throw new ResourceNotFoundException("Cannot update: Jury Notification not found with id: {0}", juryNotification.getId());
        }
        stepSixRepository.save(juryNotification);
    }

    public void deleteJuryNotification(Long id) {
        if (!stepSixRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Jury Notification not found with id: {0}", id);
        }
        stepSixRepository.deleteById(id);
    }
}
