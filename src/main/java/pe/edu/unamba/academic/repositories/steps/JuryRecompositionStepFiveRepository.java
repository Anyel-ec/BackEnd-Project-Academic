package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.JuryRecompositionStepFive;

@Repository
public interface JuryRecompositionStepFiveRepository extends JpaRepository<JuryRecompositionStepFive, Long> {
}
