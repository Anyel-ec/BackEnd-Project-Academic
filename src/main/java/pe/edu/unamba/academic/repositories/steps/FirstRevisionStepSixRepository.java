package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.FirstRevisionStepSix;

@Repository
public interface FirstRevisionStepSixRepository extends JpaRepository<FirstRevisionStepSix, Long> {
}
