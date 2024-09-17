package pe.edu.unamba.academic.repositories.actors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.actors.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStudentCode(String studentCode);
}