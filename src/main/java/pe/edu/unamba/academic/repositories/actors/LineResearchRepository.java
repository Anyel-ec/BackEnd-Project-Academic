package pe.edu.unamba.academic.repositories.actors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.actors.LineResearch;

import java.util.List;

@Repository
public interface LineResearchRepository extends JpaRepository<LineResearch, Long> {
    // Método para obtener las líneas de investigación por id de carrera
    List<LineResearch> findByCareerId(Long careerId);
}
