package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;

import java.util.Optional;

@Repository
public interface JuryAppointmentStepThreeRepository extends JpaRepository<JuryAppointmentStepThree, Long>{
    Optional<JuryAppointmentStepThree> findByProjectApprovalStepTwo_TitleReservationStepOne_Student_StudentCode(String studentCode);

    @Query("SELECT j FROM JuryAppointmentStepThree j " +
            "LEFT JOIN FETCH j.president p " +
            "LEFT JOIN FETCH j.firstMember fm " +
            "LEFT JOIN FETCH j.secondMember sm " +
            "LEFT JOIN FETCH j.accessory a " +
            "WHERE j.id = :id")
    Optional<JuryAppointmentStepThree> findByIdWithRelations(@Param("id") Long id);

    Optional<? extends JuryAppointmentStepThree> findByProjectApprovalStepTwo(ProjectApprovalStepTwo s2);
}
