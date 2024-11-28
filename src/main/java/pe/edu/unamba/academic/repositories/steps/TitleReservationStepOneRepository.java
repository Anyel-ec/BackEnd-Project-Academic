package pe.edu.unamba.academic.repositories.steps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TitleReservationStepOneRepository extends JpaRepository<TitleReservationStepOne, Long> {

    // Find steps where the title contains the provided string
    @Query("SELECT t FROM TitleReservationStepOne t WHERE t.title LIKE %:title%")
    List<TitleReservationStepOne> findByTitleContaining(@Param("title") String title);
    
    @Query(value = "SELECT DISTINCT s1.codigo_alumno " +
            "FROM p1_reservacion_titulo t " +
            "LEFT JOIN alumnos s1 ON t.id_student = s1.id " +
            "LEFT JOIN alumnos s2 ON t.id_student_two = s2.id " +
            "WHERE s1.id IS NOT NULL OR s2.id IS NOT NULL",
            nativeQuery = true)
    List<String> findStudentCodesWithProgress();

    // Find a TitleReservationStepOne by student code or studentTwo code, handling null values
    @Query(value = "SELECT t.* " +
            "FROM p1_reservacion_titulo t " +
            "LEFT JOIN alumnos s1 ON t.id_student = s1.id " +
            "LEFT JOIN alumnos s2 ON t.id_student_two = s2.id " +
            "WHERE s1.codigo_alumno = :studentCode OR (s2.id IS NOT NULL AND s2.codigo_alumno = :studentCode)",
            nativeQuery = true)
    Optional<TitleReservationStepOne> findByAnyStudentCodeNative(@Param("studentCode") String studentCode);

    // Find a TitleReservationStepOne by exact title
    Optional<TitleReservationStepOne> findByTitle(String title);
    @Query("SELECT t.updatedAt FROM TitleReservationStepOne t WHERE t.student.studentCode = :studentCode OR t.studentTwo.studentCode = :studentCode")
    Date findUpdatedAtByStudentCode(@Param("studentCode") String studentCode);
    // Check if a title already exists
    boolean existsByTitle(String title);
}


