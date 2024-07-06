package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.ThesisApprovalStepThree;
import pe.edu.unamba.academic.repositories.ThesisApprovalStepThreeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ThesisApprovalStepThreeService {
    @Autowired
    private ThesisApprovalStepThreeRepository thesisApprovalStepThreeRepository;

    public List<ThesisApprovalStepThree> getAllThesisApprovals() {
        return thesisApprovalStepThreeRepository.findAll();
    }

    public Optional<ThesisApprovalStepThree> getThesisApprovalById(Long id) {
        return thesisApprovalStepThreeRepository.findById(id);
    }

    public ThesisApprovalStepThree saveThesisApproval(ThesisApprovalStepThree thesisApproval) {
        return thesisApprovalStepThreeRepository.save(thesisApproval);
    }

    public void deleteThesisApproval(Long id) {
        thesisApprovalStepThreeRepository.deleteById(id);
    }

    public ThesisApprovalStepThree updateThesisApproval(Long id, ThesisApprovalStepThree thesisApprovalDetails) {
        ThesisApprovalStepThree thesisApproval = thesisApprovalStepThreeRepository.findById(id).get();
        thesisApproval.setMeetsRequirements(thesisApprovalDetails.isMeetsRequirements());
        thesisApproval.setObservations(thesisApprovalDetails.getObservations());
        return thesisApprovalStepThreeRepository.save(thesisApproval);
    }
}
