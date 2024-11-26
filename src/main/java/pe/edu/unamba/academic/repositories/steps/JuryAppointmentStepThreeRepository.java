package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;

import java.util.Optional;
@Repository
public interface JuryAppointmentStepThreeRepository extends JpaRepository<JuryAppointmentStepThree, Long> {

    // Busca por studentCode del estudiante principal
    // Optional<JuryAppointmentStepThree> findByProjectApprovalStepTwo_TitleReservationStepOne_Student_StudentCode(String studentCode);

    // Consulta personalizada para buscar por cualquier studentCode (principal o secundario)
    @Query("SELECT j FROM JuryAppointmentStepThree j " +
            "WHERE j.projectApprovalStepTwo.titleReservationStepOne.student.studentCode = :studentCode " +
            "OR j.projectApprovalStepTwo.titleReservationStepOne.studentTwo.studentCode = :studentCode")
    Optional<JuryAppointmentStepThree> findByAnyStudentCode(@Param("studentCode") String studentCode);

    // Consulta para obtener relaciones asociadas al jurado
    @Query("SELECT j FROM JuryAppointmentStepThree j " +
            "LEFT JOIN FETCH j.president p " +
            "LEFT JOIN FETCH j.firstMember fm " +
            "LEFT JOIN FETCH j.secondMember sm " +
            "LEFT JOIN FETCH j.accessory a " +
            "WHERE j.id = :id")
    Optional<JuryAppointmentStepThree> findByIdWithRelations(@Param("id") Long id);

    // Busca por projectApprovalStepTwo
    Optional<? extends JuryAppointmentStepThree> findByProjectApprovalStepTwo(ProjectApprovalStepTwo s2);
}
