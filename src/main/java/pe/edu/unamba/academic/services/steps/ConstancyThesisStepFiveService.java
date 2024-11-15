package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ConstancyThesisStepFive;
import pe.edu.unamba.academic.repositories.steps.ConstancyThesisStepFiveRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConstancyThesisStepFiveService {
    private final ConstancyThesisStepFiveRepository constancyThesisStepFiveRepository;

    public List<ConstancyThesisStepFive> getAllConstancyThesis() {
        return constancyThesisStepFiveRepository.findAll();
    }
    public Optional<ConstancyThesisStepFive> getConstancyThesisById(Long id) {
        return constancyThesisStepFiveRepository.findById(id);
    }
    public ConstancyThesisStepFive createConstancyThesis(ConstancyThesisStepFive constancyThesisStepFive) {
        return constancyThesisStepFiveRepository.save(constancyThesisStepFive);
    }
    public Optional<ConstancyThesisStepFive> updateConstancyThesis(Long id, ConstancyThesisStepFive updatedConstancyThesis) {
        return constancyThesisStepFiveRepository.findById(id).map(thesis->{
            thesis.setReportReviewStepFour(updatedConstancyThesis.getReportReviewStepFour());
            thesis.setObservations(updatedConstancyThesis.getObservations());
            thesis.setMeetsRequirements(updatedConstancyThesis.isMeetsRequirements());
            return constancyThesisStepFiveRepository.save(thesis);
        });
    }
    public boolean deleteConstancyThesisById(Long id) {
        if(constancyThesisStepFiveRepository.existsById(id)) {
            constancyThesisStepFiveRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
