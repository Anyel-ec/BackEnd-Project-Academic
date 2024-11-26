package pe.edu.unamba.academic.repositories.steps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
import java.util.List;
import java.util.Optional;

@Repository
public interface TitleReservationStepOneRepository extends JpaRepository<TitleReservationStepOne, Long> {

    // Find steps where the title contains the provided string
    @Query("SELECT t FROM TitleReservationStepOne t WHERE t.title LIKE %:title%")
    List<TitleReservationStepOne> findByTitleContaining(@Param("title") String title);

    // Find a TitleReservationStepOne by student code or studentTwo code, handling null values
    @Query("SELECT t FROM TitleReservationStepOne t " +
            "WHERE (t.student IS NOT NULL AND t.student.studentCode = :studentCode) " +
            "OR (t.studentTwo IS NOT NULL AND t.studentTwo.studentCode = :studentCode)")
    Optional<TitleReservationStepOne> findByAnyStudentCode(@Param("studentCode") String studentCode);
    // Find a TitleReservationStepOne by exact title
    Optional<TitleReservationStepOne> findByTitle(String title);

    // Check if a title already exists
    boolean existsByTitle(String title);
}


