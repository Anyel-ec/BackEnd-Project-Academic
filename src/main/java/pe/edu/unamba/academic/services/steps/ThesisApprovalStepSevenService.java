package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.exceptions.ResourceNotFoundException;
import pe.edu.unamba.academic.models.steps.ThesisApprovalStepSeven;
import pe.edu.unamba.academic.repositories.steps.ThesisApprovalStepSevenRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ThesisApprovalStepSevenService {

    private final ThesisApprovalStepSevenRepository repository;

    public List<ThesisApprovalStepSeven> getThesisApprovals() {
        return repository.findAll();
    }

    public Optional<ThesisApprovalStepSeven> getThesisApproval(Long id) {
        return Optional.ofNullable(
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Thesis Approval not found with id: {0}", id))
        );
    }

    public void saveOrUpdateApproval(ThesisApprovalStepSeven thesisApprovalStepSeven) {
        if (thesisApprovalStepSeven.getId() != null && !repository.existsById(thesisApprovalStepSeven.getId())) {
            throw new ResourceNotFoundException("Cannot update: Thesis Approval not found with id: {0}", thesisApprovalStepSeven.getId());
        }
        repository.save(thesisApprovalStepSeven);
    }

    public void deleteThesisApproval(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Thesis Approval not found with id: {0}", id);
        }
        repository.deleteById(id);
    }
}
