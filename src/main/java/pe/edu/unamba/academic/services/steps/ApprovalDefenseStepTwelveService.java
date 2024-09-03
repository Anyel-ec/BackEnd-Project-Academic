package pe.edu.unamba.academic.services.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ApprovalDefenseStepTwelve;
import pe.edu.unamba.academic.repositories.steps.ApprovalDefenseStepTwelveRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalDefenseStepTwelveService {
    @Autowired
    private ApprovalDefenseStepTwelveRepository approvalDefenseStepTwelveRepository;

    public List<ApprovalDefenseStepTwelve> getAllApprovals() {
        return approvalDefenseStepTwelveRepository.findAll();
    }

    public Optional<ApprovalDefenseStepTwelve> getApprovalById(Long id) {
        return approvalDefenseStepTwelveRepository.findById(id);
    }

    public ApprovalDefenseStepTwelve saveApproval(ApprovalDefenseStepTwelve approval) {
        return approvalDefenseStepTwelveRepository.save(approval);
    }

    public void deleteApproval(Long id) {
        approvalDefenseStepTwelveRepository.deleteById(id);
    }

    public ApprovalDefenseStepTwelve updateApproval(Long id, ApprovalDefenseStepTwelve approvalDetails) {
        ApprovalDefenseStepTwelve approval = approvalDefenseStepTwelveRepository.findById(id).get();
        approval.setMeetsRequirements(approvalDetails.isMeetsRequirements());
        approval.setObservations(approvalDetails.getObservations());
        return approvalDefenseStepTwelveRepository.save(approval);
    }
}
