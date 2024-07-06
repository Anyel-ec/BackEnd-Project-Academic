package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.JuryRecompositionStepFive;
import pe.edu.unamba.academic.repositories.JuryRecompositionStepFiveRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JuryRecompositionStepFiveService {
    @Autowired
    private JuryRecompositionStepFiveRepository juryRecompositionStepFiveRepository;

    public List<JuryRecompositionStepFive> getAllJuryRecompositions() {
        return juryRecompositionStepFiveRepository.findAll();
    }

    public Optional<JuryRecompositionStepFive> getJuryRecompositionById(Long id) {
        return juryRecompositionStepFiveRepository.findById(id);
    }

    public JuryRecompositionStepFive saveJuryRecomposition(JuryRecompositionStepFive juryRecomposition) {
        return juryRecompositionStepFiveRepository.save(juryRecomposition);
    }

    public void deleteJuryRecomposition(Long id) {
        juryRecompositionStepFiveRepository.deleteById(id);
    }

    public JuryRecompositionStepFive updateJuryRecomposition(Long id, JuryRecompositionStepFive juryRecompositionDetails) {
        JuryRecompositionStepFive juryRecomposition = juryRecompositionStepFiveRepository.findById(id).get();
        juryRecomposition.setMeetsRequirements(juryRecompositionDetails.isMeetsRequirements());
        juryRecomposition.setObservations(juryRecompositionDetails.getObservations());
        return juryRecompositionStepFiveRepository.save(juryRecomposition);
    }
}
