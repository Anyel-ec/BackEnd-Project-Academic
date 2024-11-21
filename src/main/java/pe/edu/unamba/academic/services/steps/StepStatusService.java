package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.StepStatus;
import pe.edu.unamba.academic.repositories.steps.StepStatusRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StepStatusService {
    private final StepStatusRepository stepStatusRepository;

    public List<StepStatus> getStepsForStudent(String studentCode) {
        return stepStatusRepository.findByStudentCode(studentCode);
    }

    public StepStatus completeStep(String studentCode, int stepNumber) {
        StepStatus stepStatus = stepStatusRepository
                .findByStudentCodeAndStepNumber(studentCode, stepNumber)
                .orElse(new StepStatus());

        stepStatus.setStudentCode(studentCode);
        stepStatus.setStepNumber(stepNumber);
        stepStatus.setCompleted(true);

        return stepStatusRepository.save(stepStatus);
    }


}
