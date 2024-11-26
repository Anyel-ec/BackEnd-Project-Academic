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

    @Query(value = "SELECT j.* " +
            "FROM p3_designacion_jurado j " +
            "LEFT JOIN p2_aprobacion_proyecto p ON j.aprobacion_proyecto = p.id_aprobacion_proyecto " +
            "LEFT JOIN p1_reservacion_titulo t ON p.reservacion_titulo = t.id_reservacion_titulo " +
            "LEFT JOIN alumnos s1 ON t.id_student = s1.id " +
            "LEFT JOIN alumnos s2 ON t.id_student_two = s2.id " +
            "WHERE s1.codigo_alumno = :studentCode " +
            "   OR (s2.id IS NOT NULL AND s2.codigo_alumno = :studentCode)",
            nativeQuery = true)
    Optional<JuryAppointmentStepThree> findByAnyStudentCodeNative(@Param("studentCode") String studentCode);

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
