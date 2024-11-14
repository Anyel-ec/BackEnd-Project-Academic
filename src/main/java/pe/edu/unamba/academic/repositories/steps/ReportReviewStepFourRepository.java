package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;
import pe.edu.unamba.academic.models.steps.ReportReviewStepFour;

import java.util.Optional;

@Repository
public interface ReportReviewStepFourRepository extends JpaRepository<ReportReviewStepFour, Long> {
    Optional<ReportReviewStepFour> findByJuryAppointmentStepThree(JuryAppointmentStepThree jury);
}
