package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;

import java.util.Optional;

@Repository
public interface ProjectApprovalStepTwoRepository extends JpaRepository<ProjectApprovalStepTwo, Long> {
    Optional<? extends ProjectApprovalStepTwo> findByTitleReservationStepOne(TitleReservationStepOne s1);
}
