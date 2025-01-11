package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ChangeAdvisor;
import pe.edu.unamba.academic.repositories.steps.ChangeAdvisorRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChangeAdvisorService {

    private final ChangeAdvisorRepository changeAdvisorRepository;

    // Read (Get all)
    public List<ChangeAdvisor> getAllJuryRecompositions() {
        return changeAdvisorRepository.findAll();
    }

    // Read (Get by ID)
    public Optional<ChangeAdvisor> getJuryRecompositionById(Long id) {
        return changeAdvisorRepository.findById(id);
    }

    // Update
    public ChangeAdvisor updateChangeAdvisor(Long id, ChangeAdvisor updatedChangeAdvisor) {
        return changeAdvisorRepository.findById(id)
                .map(changeAdvisor -> {
                    changeAdvisor.setObservations(updatedChangeAdvisor.getObservations());
                    changeAdvisor.setMeetsRequirements(updatedChangeAdvisor.getMeetsRequirements());
                    return changeAdvisorRepository.save(changeAdvisor);
                })
                .orElseThrow(() -> new IllegalArgumentException("Jury Recomposition with ID " + id + " not found"));
    }
}
