package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;

import java.util.Optional;

@Repository
public interface ProjectApprovalStepTwoRepository extends JpaRepository<ProjectApprovalStepTwo, Long> {
    Optional<ProjectApprovalStepTwo> findByTitleReservationStepOne(TitleReservationStepOne titleReservationStepOne);
    @Query("SELECT p FROM ProjectApprovalStepTwo p " +
            "WHERE p.titleReservationStepOne.student.studentCode = :studentCode " +
            "OR p.titleReservationStepOne.studentTwo.studentCode = :studentCode")
    Optional<ProjectApprovalStepTwo> findByAnyStudentCode(@Param("studentCode") String studentCode);

}