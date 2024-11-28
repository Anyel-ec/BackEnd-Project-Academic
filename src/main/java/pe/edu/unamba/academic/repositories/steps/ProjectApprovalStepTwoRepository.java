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

    @Query("SELECT p FROM ProjectApprovalStepTwo p WHERE p.titleReservationStepOne.id = :titleReservationId")
    Optional<ProjectApprovalStepTwo> findByTitleReservationStepOne(@Param("titleReservationId") Long titleReservationId);

    @Query(value = "SELECT p.* " +
            "FROM p2_aprobacion_proyecto p " +
            "LEFT JOIN p1_reservacion_titulo t ON p.reservacion_titulo = t.id_reservacion_titulo " +
            "LEFT JOIN alumnos s1 ON t.id_student = s1.id " +
            "LEFT JOIN alumnos s2 ON t.id_student_two = s2.id " +
            "WHERE s1.codigo_alumno = :studentCode " +
            "   OR (s2.id IS NOT NULL AND s2.codigo_alumno = :studentCode)",
            nativeQuery = true)
    Optional<ProjectApprovalStepTwo> findByAnyStudentCodeNative(@Param("studentCode") String studentCode);

}