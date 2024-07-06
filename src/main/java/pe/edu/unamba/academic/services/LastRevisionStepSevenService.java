package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.LastRevisionStepSeven;
import pe.edu.unamba.academic.repositories.LastRevisionStepSevenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LastRevisionStepSevenService {
    @Autowired
    private LastRevisionStepSevenRepository lastRevisionStepSevenRepository;

    public List<LastRevisionStepSeven> getAllLastRevisions() {
        return lastRevisionStepSevenRepository.findAll();
    }

    public Optional<LastRevisionStepSeven> getLastRevisionById(Long id) {
        return lastRevisionStepSevenRepository.findById(id);
    }

    public LastRevisionStepSeven saveLastRevision(LastRevisionStepSeven lastRevision) {
        return lastRevisionStepSevenRepository.save(lastRevision);
    }

    public void deleteLastRevision(Long id) {
        lastRevisionStepSevenRepository.deleteById(id);
    }

    public LastRevisionStepSeven updateLastRevision(Long id, LastRevisionStepSeven lastRevisionDetails) {
        LastRevisionStepSeven lastRevision = lastRevisionStepSevenRepository.findById(id).get();
        lastRevision.setMeetsRequirements(lastRevisionDetails.isMeetsRequirements());
        lastRevision.setObservations(lastRevisionDetails.getObservations());
        return lastRevisionStepSevenRepository.save(lastRevision);
    }
}
