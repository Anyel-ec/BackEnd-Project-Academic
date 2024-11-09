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
    @Query("SELECT t FROM TitleReservationStepOne t WHERE t.title LIKE %:title%")
    List<TitleReservationStepOne> findByTitleContaining(@Param("title") String title);

    Optional<TitleReservationStepOne> findByTitle(String title);
    boolean existsByTitle(String title); // Método que verifica si el título ya existe
}
