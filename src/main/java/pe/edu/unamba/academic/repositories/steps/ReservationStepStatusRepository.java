package pe.edu.unamba.academic.repositories.steps;

import pe.edu.unamba.academic.models.steps.ReservationStepStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationStepStatusRepository extends JpaRepository<ReservationStepStatus, Long> {
    Optional<ReservationStepStatus> findByStudentCodeAndStepNumber(String studentCode, Integer stepNumber);
    List<ReservationStepStatus> findByStudentCode(String studentCode);
}
