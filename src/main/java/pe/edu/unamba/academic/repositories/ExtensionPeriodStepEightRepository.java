package pe.edu.unamba.academic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.ExtensionPeriodStepEight;

@Repository
public interface ExtensionPeriodStepEightRepository extends JpaRepository<ExtensionPeriodStepEight, Long> {
}
