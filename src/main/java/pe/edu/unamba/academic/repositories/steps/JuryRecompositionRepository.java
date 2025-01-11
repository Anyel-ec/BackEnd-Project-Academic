package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.JuryRecomposition;

import java.util.Optional;

@Repository
public interface JuryRecompositionRepository extends JpaRepository<JuryRecomposition, Long> {
    Optional<JuryRecomposition> findByProjectApprovalStepTwoId(Long projectApprovalStepTwoId);

}
