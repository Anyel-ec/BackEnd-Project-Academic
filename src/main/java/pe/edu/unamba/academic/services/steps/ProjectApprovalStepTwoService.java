package pe.edu.unamba.academic.services.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;
import pe.edu.unamba.academic.repositories.steps.ProjectApprovalStepTwoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectApprovalStepTwoService {

    @Autowired
    private ProjectApprovalStepTwoRepository projectApprovalStepTwoRepository;

    // Obtener todas las aprobaciones de proyectos
    public List<ProjectApprovalStepTwo> getAllProjectApprovals() {
        return projectApprovalStepTwoRepository.findAll();
    }

    // Obtener una aprobación de proyecto por ID
    public Optional<ProjectApprovalStepTwo> getProjectApprovalById(Long id) {
        return projectApprovalStepTwoRepository.findById(id);
    }

    // Crear una nueva aprobación de proyecto
    public ProjectApprovalStepTwo createProjectApproval(ProjectApprovalStepTwo projectApprovalStepTwo) {
        return projectApprovalStepTwoRepository.save(projectApprovalStepTwo);
    }

    // Actualizar una aprobación de proyecto existente
    public Optional<ProjectApprovalStepTwo> updateProjectApproval(Long id, ProjectApprovalStepTwo updatedProjectApproval) {
        return projectApprovalStepTwoRepository.findById(id).map(approval -> {
            approval.setTitleReservationStepOne(updatedProjectApproval.getTitleReservationStepOne());
            approval.setAdviser(updatedProjectApproval.getAdviser());
            approval.setDisable(updatedProjectApproval.isDisable());
            approval.setObservations(updatedProjectApproval.getObservations());
            return projectApprovalStepTwoRepository.save(approval);
        });
    }

    // Eliminar una aprobación de proyecto por ID
    public boolean deleteProjectApproval(Long id) {
        if (projectApprovalStepTwoRepository.existsById(id)) {
            projectApprovalStepTwoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
