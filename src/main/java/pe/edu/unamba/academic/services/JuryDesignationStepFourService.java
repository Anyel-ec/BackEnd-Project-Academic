package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.JuryDesignationStepFour;
import pe.edu.unamba.academic.repositories.JuryDesignationStepFourRepository;

import java.util.List;
import java.util.Optional;

@Service
public class JuryDesignationStepFourService {
    @Autowired
    private JuryDesignationStepFourRepository juryDesignationStepFourRepository;

    public List<JuryDesignationStepFour> getAllJuryDesignations() {
        return juryDesignationStepFourRepository.findAll();
    }

    public Optional<JuryDesignationStepFour> getJuryDesignationById(Long id) {
        return juryDesignationStepFourRepository.findById(id);
    }

    public JuryDesignationStepFour saveJuryDesignation(JuryDesignationStepFour juryDesignation) {
        return juryDesignationStepFourRepository.save(juryDesignation);
    }

    public void deleteJuryDesignation(Long id) {
        juryDesignationStepFourRepository.deleteById(id);
    }

    public JuryDesignationStepFour updateJuryDesignation(Long id, JuryDesignationStepFour juryDesignationDetails) {
        JuryDesignationStepFour juryDesignation = juryDesignationStepFourRepository.findById(id).get();
        juryDesignation.setMeetsRequirements(juryDesignationDetails.isMeetsRequirements());
        juryDesignation.setObservations(juryDesignationDetails.getObservations());
        return juryDesignationStepFourRepository.save(juryDesignation);
    }
}
