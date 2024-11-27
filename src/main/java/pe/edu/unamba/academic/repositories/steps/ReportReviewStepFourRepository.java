package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;
import pe.edu.unamba.academic.models.steps.ReportReviewStepFour;

import java.util.Optional;

@Repository
public interface ReportReviewStepFourRepository extends JpaRepository<ReportReviewStepFour, Long> {
    Optional<ReportReviewStepFour> findByJuryAppointmentStepThree(JuryAppointmentStepThree jury);
    @Query(value = "SELECT r.* " +
            "FROM p4_revision_reporte r " +
            "LEFT JOIN p3_designacion_jurado j ON r.designacion_jurado = j.id_designacion_jurado " +
            "LEFT JOIN p2_aprobacion_proyecto p ON j.aprobacion_proyecto = p.id_aprobacion_proyecto " +
            "LEFT JOIN p1_reservacion_titulo t ON p.reservacion_titulo = t.id_reservacion_titulo " +
            "LEFT JOIN alumnos s1 ON t.id_student = s1.id " +
            "LEFT JOIN alumnos s2 ON t.id_student_two = s2.id " +
            "WHERE s1.codigo_alumno = :studentCode " +
            "   OR (s2.id IS NOT NULL AND s2.codigo_alumno = :studentCode)",
            nativeQuery = true)
    Optional<ReportReviewStepFour> findByAnyStudentCodeNative(@Param("studentCode") String studentCode);

}
