package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.PastingApprovalStepEight;
import pe.edu.unamba.academic.models.steps.ThesisApprovalStepSeven;

import java.util.Optional;

@Repository
public interface PastingApprovalStepEightRepository extends JpaRepository<PastingApprovalStepEight, Long> {
    Optional<PastingApprovalStepEight> findByThesisApprovalStepSeven(ThesisApprovalStepSeven stepSeven);


    @Query(value = "SELECT e.* " +
            "FROM p8_aprobacion_empastados e " +
            "LEFT JOIN p7_aprobacion_tesis t ON e.id_aprobacion_tesis = t.id_aprobacion_tesis " +
            "LEFT JOIN p6_notificacion_jurados n ON t.notificacion_jurados = n.id_notificacion_jurados " +
            "LEFT JOIN p5_constancia_tesis c ON n.id_constancia_tesis = c.id_constancia_tesis " +
            "LEFT JOIN p4_revision_reporte r ON c.revision_reporte = r.id_revision_reporte " +
            "LEFT JOIN p3_designacion_jurado j ON r.designacion_jurado = j.id_designacion_jurado " +
            "LEFT JOIN p2_aprobacion_proyecto p ON j.aprobacion_proyecto = p.id_aprobacion_proyecto " +
            "LEFT JOIN p1_reservacion_titulo t1 ON p.reservacion_titulo = t1.id_reservacion_titulo " +
            "LEFT JOIN alumnos s1 ON t1.id_student = s1.id " +
            "LEFT JOIN alumnos s2 ON t1.id_student_two = s2.id " +
            "WHERE s1.codigo_alumno = :studentCode " +
            "   OR (s2.id IS NOT NULL AND s2.codigo_alumno = :studentCode)",
            nativeQuery = true)
    Optional<PastingApprovalStepEight> findByAnyStudentCodeNative(@Param("studentCode") String studentCode);
}
