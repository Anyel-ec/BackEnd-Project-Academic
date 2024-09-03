package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.ExtensionPeriodStepEight;

@Repository
public interface ExtensionPeriodStepEightRepository extends JpaRepository<ExtensionPeriodStepEight, Long> {
}
