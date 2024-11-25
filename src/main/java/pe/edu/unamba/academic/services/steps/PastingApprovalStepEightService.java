package pe.edu.unamba.academic.services.steps;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.exceptions.ResourceNotFoundException;
import pe.edu.unamba.academic.models.steps.PastingApprovalStepEight;
import pe.edu.unamba.academic.repositories.steps.PastingApprovalStepEightRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PastingApprovalStepEightService {

    private final PastingApprovalStepEightRepository repository;

    public List<PastingApprovalStepEight> getAll() {
        return repository.findAll();
    }

    public Optional<PastingApprovalStepEight> getById(Long id) {
        return Optional.ofNullable(
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Pasting Approval not found with id: {0}", id))
        );
    }

    public void save(PastingApprovalStepEight pastingApprovalStepEight) {
        if (pastingApprovalStepEight.getId() != null) {
            throw new IllegalArgumentException("New records cannot have an ID");
        }
        repository.save(pastingApprovalStepEight);
    }


    @Transactional
    public void update(PastingApprovalStepEight pastingApprovalStepEight) {
        // Verifica si el registro existe
        PastingApprovalStepEight existingApproval = repository.findById(pastingApprovalStepEight.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageFormat.format("Cannot update: Pasting Approval not found with id: {0}", pastingApprovalStepEight.getId())
                ));

        // Actualiza los campos necesarios
        existingApproval.setMeetRequirements(pastingApprovalStepEight.isMeetRequirements());

        if (pastingApprovalStepEight.getObservations() != null) {
            existingApproval.setObservations(pastingApprovalStepEight.getObservations());
        }

        if (pastingApprovalStepEight.getThesisApprovalStepSeven() != null) {
            existingApproval.setThesisApprovalStepSeven(pastingApprovalStepEight.getThesisApprovalStepSeven());
        }

        // Si cumple con los requisitos, crea un nuevo registro en la siguiente etapa


        // Guarda el objeto actualizado en la base de datos
        repository.save(existingApproval);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Pasting Approval not found with id: {0}", id);
        }
        repository.deleteById(id);
    }
}
