package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.PassageExpansion;
import pe.edu.unamba.academic.repositories.steps.PassageExpansionRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PassageExpansionService {
    private final PassageExpansionRepository passageExpansionRepository;

    // Read (Get all)
    public List<PassageExpansion> getAllJuryRecompositions() {
        return passageExpansionRepository.findAll();
    }

    // Read (Get by ID)
    public Optional<PassageExpansion> getJuryRecompositionById(Long id) {
        return passageExpansionRepository.findById(id);
    }

    // Update
    public PassageExpansion updatePassageExpansion(Long id, PassageExpansion updatedPassageExpansion) {
        return passageExpansionRepository.findById(id)
                .map(passageExpansion -> {
                    passageExpansion.setObservations(updatedPassageExpansion.getObservations());
                    passageExpansion.setMeetsRequirements(updatedPassageExpansion.getMeetsRequirements());
                    return passageExpansionRepository.save(passageExpansion);
                })
                .orElseThrow(() -> new IllegalArgumentException("Jury Recomposition with ID " + id + " not found"));
    }
}
