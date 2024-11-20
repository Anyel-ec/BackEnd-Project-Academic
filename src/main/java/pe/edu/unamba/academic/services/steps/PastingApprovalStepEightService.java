package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.exceptions.ResourceNotFoundException;
import pe.edu.unamba.academic.models.steps.PastingApprovalStepEight;
import pe.edu.unamba.academic.repositories.steps.PastingApprovalStepEightRepository;

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

    public void saveOrUpdate(PastingApprovalStepEight pastingApprovalStepEight) {
        if (pastingApprovalStepEight.getId() != null && !repository.existsById(pastingApprovalStepEight.getId())) {
            throw new ResourceNotFoundException("Cannot update: Pasting Approval not found with id: {0}", pastingApprovalStepEight.getId());
        }
        repository.save(pastingApprovalStepEight);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Pasting Approval not found with id: {0}", id);
        }
        repository.deleteById(id);
    }
}
