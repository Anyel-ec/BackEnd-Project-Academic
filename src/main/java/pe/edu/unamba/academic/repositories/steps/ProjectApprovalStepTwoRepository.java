package pe.edu.unamba.academic.repositories.steps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;

@Repository
public interface ProjectApprovalStepTwoRepository extends JpaRepository<ProjectApprovalStepTwo, Long> {
    // Puedes agregar métodos personalizados si los necesitas
}
