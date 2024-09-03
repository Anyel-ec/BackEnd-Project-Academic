package pe.edu.unamba.academic.repositories.actors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.actors.TeacherCareer;

@Repository
public interface TeacherCareerRepository extends JpaRepository<TeacherCareer, Long> {
}