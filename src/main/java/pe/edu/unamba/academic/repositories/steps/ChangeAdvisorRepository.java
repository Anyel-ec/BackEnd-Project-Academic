package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.ChangeAdvisor;

import java.util.Optional;

@Repository
public interface ChangeAdvisorRepository extends JpaRepository<ChangeAdvisor, Long> {
    Optional<ChangeAdvisor> findByTitleReservationStepOneId(Long titleReservationStepOneId);
}
