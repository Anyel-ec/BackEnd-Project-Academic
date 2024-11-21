package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.StepStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface StepStatusRepository extends JpaRepository<StepStatus, Long> {
    List<StepStatus> findByStudentCode(String studentCode);

    Optional<StepStatus> findByStudentCodeAndStepNumber(String studentCode, int stepNumber);
}
