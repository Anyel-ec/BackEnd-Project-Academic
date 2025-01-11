package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.PassageExpansion;

import java.util.Optional;

@Repository
public interface PassageExpansionRepository extends JpaRepository<PassageExpansion, Long> {
    Optional<PassageExpansion> findByTitleReservationStepOneId(Long titleReservationStepOneId);

}
