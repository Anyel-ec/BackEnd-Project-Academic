package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;

import java.util.Optional;

@Repository
public interface JuryAppointmentStepThreeRepository extends JpaRepository<JuryAppointmentStepThree, Long>{
    @Query("SELECT j FROM JuryAppointmentStepThree j " +
            "LEFT JOIN FETCH j.president " +
            "LEFT JOIN FETCH j.firstMember " +
            "LEFT JOIN FETCH j.secondMember " +
            "LEFT JOIN FETCH j.accessory " +
            "WHERE j.id = :id")
    Optional<JuryAppointmentStepThree> findByIdWithRelations(@Param("id") Long id);
}
