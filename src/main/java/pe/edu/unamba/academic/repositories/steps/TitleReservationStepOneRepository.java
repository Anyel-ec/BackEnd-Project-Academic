package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;

import java.util.Optional;

@Repository
public interface TitleReservationStepOneRepository extends JpaRepository<TitleReservationStepOne, Long> {
    Optional<TitleReservationStepOne> findByStudent_StudentCode(String studentCode);
    Optional<TitleReservationStepOne> findByPdfDocument_Id(Long pdfDocumentId);
    // Añadir este método para buscar una reservación por título
    Optional<TitleReservationStepOne> findByTitle(String title);
    boolean existsByTitle(String title); // Método que verifica si el título ya existe
}
