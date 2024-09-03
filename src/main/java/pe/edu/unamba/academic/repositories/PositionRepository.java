package pe.edu.unamba.academic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
}
