package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.JuryRecomposition;
import pe.edu.unamba.academic.repositories.steps.JuryRecompositionRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JuryRecompositionService {
    private final JuryRecompositionRepository juryRecompositionRepository;

    // Read (Get all)
    public List<JuryRecomposition> getAllJuryRecompositions() {
        return juryRecompositionRepository.findAll();
    }

    // Read (Get by ID)
    public Optional<JuryRecomposition> getJuryRecompositionById(Long id) {
        return juryRecompositionRepository.findById(id);
    }

    // Update
    public JuryRecomposition updateJuryRecomposition(Long id, JuryRecomposition updatedJuryRecomposition) {
        return juryRecompositionRepository.findById(id)
                .map(juryRecomposition -> {
                    juryRecomposition.setObservations(updatedJuryRecomposition.getObservations()); // Ejemplo: Actualiza nombre
                    juryRecomposition.setMeetsRequirements(updatedJuryRecomposition.getMeetsRequirements()); // Otro campo
                    return juryRecompositionRepository.save(juryRecomposition);
                })
                .orElseThrow(() -> new IllegalArgumentException("Jury Recomposition with ID " + id + " not found"));
    }

}
