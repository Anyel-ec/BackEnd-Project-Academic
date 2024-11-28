package pe.edu.unamba.academic.repositories.actors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.StudentProgress;
import pe.edu.unamba.academic.models.actors.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStudentCode(String studentCode);

    @Query("SELECT s.studentCode FROM Student s")
    List<String> findAllStudentCodes();

    @Query(value = "SELECT " +
            "t1.actualizado_en AS titleReservationUpdated, " +
            "p2.actualizado_en AS projectApprovalUpdated, " +
            "j3.actualizado_en AS juryAppointmentUpdated, " +
            "r4.actualizado_en AS reportReviewUpdated, " +
            "c5.actualizado_en AS constancyThesisUpdated, " +
            "n6.actualizado_en AS juryNotificationsUpdated, " +
            "a7.actualizado_en AS thesisApprovalUpdated, " +
            "e8.actualizado_en AS pastingApprovalUpdated " +
            "FROM alumnos s " +
            "LEFT JOIN p1_reservacion_titulo t1 ON s.id = t1.id_student OR s.id = t1.id_student_two " +
            "LEFT JOIN p2_aprobacion_proyecto p2 ON t1.id = p2.reservacion_titulo " +
            "LEFT JOIN p3_designacion_jurado j3 ON p2.id = j3.aprobacion_proyecto " +
            "LEFT JOIN p4_revision_reporte r4 ON j3.id = r4.designacion_jurado " +
            "LEFT JOIN p5_constancia_tesis c5 ON r4.id = c5.revision_reporte " +
            "LEFT JOIN p6_notificacion_jurados n6 ON c5.id = n6.constancia_tesis " +
            "LEFT JOIN p7_aprobacion_tesis a7 ON n6.id = a7.notificacion_jurados " +
            "LEFT JOIN p8_aprobacion_empastados e8 ON a7.id = e8.aprobacion_tesis " +
            "WHERE s.codigo_alumno = :studentCode", nativeQuery = true)
    StudentProgress findUpdatesByStudentCode(@Param("studentCode") String studentCode);
}