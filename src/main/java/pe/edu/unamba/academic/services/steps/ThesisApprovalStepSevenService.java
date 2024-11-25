package pe.edu.unamba.academic.services.steps;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.exceptions.ResourceNotFoundException;
import pe.edu.unamba.academic.models.steps.PastingApprovalStepEight;
import pe.edu.unamba.academic.models.steps.ThesisApprovalStepSeven;
import pe.edu.unamba.academic.repositories.steps.ThesisApprovalStepSevenRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ThesisApprovalStepSevenService {

    private final ThesisApprovalStepSevenRepository repository;
    private final PastingApprovalStepEightService pastingApprovalStepEightService;
    // Obtener todos los registros
    public List<ThesisApprovalStepSeven> getThesisApprovals() {
        return repository.findAll();
    }

    // Obtener un registro por ID
    public Optional<ThesisApprovalStepSeven> getThesisApproval(Long id) {
        return Optional.ofNullable(
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Thesis Approval not found with ID: " + id
                        ))
        );
    }

    // Validar si un registro existe
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    // Guardar un nuevo registro
    public void saveApproval(ThesisApprovalStepSeven thesisApprovalStepSeven) {
        if (thesisApprovalStepSeven.getId() != null) {
            throw new IllegalArgumentException("New Thesis Approval must not have an ID");
        }
        repository.save(thesisApprovalStepSeven);
    }

    @Transactional
    public void updateApproval(ThesisApprovalStepSeven thesisApprovalStepSeven) {
        // Verifica si el registro existe
        ThesisApprovalStepSeven existingApproval = repository.findById(thesisApprovalStepSeven.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageFormat.format("Cannot update: Approval not found with id: {0}", thesisApprovalStepSeven.getId())
                ));

        existingApproval.setMeetRequirements(thesisApprovalStepSeven.isMeetRequirements());

        if (thesisApprovalStepSeven.getJuryNotificationsStepSix() != null) {
            existingApproval.setJuryNotificationsStepSix(thesisApprovalStepSeven.getJuryNotificationsStepSix());
        }

        if (thesisApprovalStepSeven.getObservations() != null) {
            existingApproval.setObservations(thesisApprovalStepSeven.getObservations());
        }
        if(existingApproval.isMeetRequirements()){
            PastingApprovalStepEight newPastingApprovalStepEight = new PastingApprovalStepEight();
            newPastingApprovalStepEight.setThesisApprovalStepSeven(existingApproval);
            newPastingApprovalStepEight.setMeetRequirements(false);
            pastingApprovalStepEightService.save(newPastingApprovalStepEight);
        }
        // Guarda el objeto actualizado en la base de datos
        repository.save(existingApproval);

    }


    // Eliminar un registro por ID
    public void deleteThesisApproval(Long id) {
        if (!existsById(id)) {
            throw new ResourceNotFoundException(
                    "Cannot delete: Thesis Approval not found with ID: " + id
            );
        }
        repository.deleteById(id);
    }
}
