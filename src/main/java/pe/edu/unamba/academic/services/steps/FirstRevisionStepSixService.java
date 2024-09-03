package pe.edu.unamba.academic.services.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.FirstRevisionStepSix;
import pe.edu.unamba.academic.repositories.steps.FirstRevisionStepSixRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FirstRevisionStepSixService {
    @Autowired
    private FirstRevisionStepSixRepository firstRevisionStepSixRepository;

    public List<FirstRevisionStepSix> getAllFirstRevisions() {
        return firstRevisionStepSixRepository.findAll();
    }

    public Optional<FirstRevisionStepSix> getFirstRevisionById(Long id) {
        return firstRevisionStepSixRepository.findById(id);
    }

    public FirstRevisionStepSix saveFirstRevision(FirstRevisionStepSix firstRevision) {
        return firstRevisionStepSixRepository.save(firstRevision);
    }

    public void deleteFirstRevision(Long id) {
        firstRevisionStepSixRepository.deleteById(id);
    }

    public FirstRevisionStepSix updateFirstRevision(Long id, FirstRevisionStepSix firstRevisionDetails) {
        FirstRevisionStepSix firstRevision = firstRevisionStepSixRepository.findById(id).get();
        firstRevision.setMeetsRequirements(firstRevisionDetails.isMeetsRequirements());
        firstRevision.setObservations(firstRevisionDetails.getObservations());
        return firstRevisionStepSixRepository.save(firstRevision);
    }
}
