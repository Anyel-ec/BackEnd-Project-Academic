package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;
import pe.edu.unamba.academic.repositories.steps.JuryAppointmentStepThreeRepository;
import pe.edu.unamba.academic.repositories.steps.ProjectApprovalStepTwoRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectApprovalStepTwoService {
    private final JuryAppointmentStepThreeRepository juryAppointmentStepThreeRepository;
    private final ProjectApprovalStepTwoRepository projectApprovalStepTwoRepository;

    // Obtener todas las aprobaciones de proyectos
    public List<ProjectApprovalStepTwo> getAllProjectApprovals() {
        return projectApprovalStepTwoRepository.findAll();
    }

    // Obtener una aprobación de proyecto por ID
    public Optional<ProjectApprovalStepTwo> getProjectApprovalById(Long id) {
        return projectApprovalStepTwoRepository.findById(id);
    }

    public Optional<ProjectApprovalStepTwo> getApprovalByStudentCode(String studentCode) {
        return projectApprovalStepTwoRepository.findByAnyStudentCodeNative(studentCode);
    }
    // Crear una nueva aprobación de proyecto
    public ProjectApprovalStepTwo createProjectApproval(ProjectApprovalStepTwo projectApprovalStepTwo) {
        return projectApprovalStepTwoRepository.save(projectApprovalStepTwo);
    }

    public Optional<Optional<ProjectApprovalStepTwo>> updateProjectApproval(Long id, ProjectApprovalStepTwo updatedProjectApproval) {
        return projectApprovalStepTwoRepository.findById(id).map(approval -> {
            // Actualizar los campos de la aprobación de proyecto
            approval.setTitleReservationStepOne(updatedProjectApproval.getTitleReservationStepOne());
            approval.setAdviser(updatedProjectApproval.getAdviser());
            approval.setIsDisable(updatedProjectApproval.getIsDisable());
            approval.setCoadviser(updatedProjectApproval.getCoadviser());
            approval.setApprovedProject(updatedProjectApproval.isApprovedProject());
            approval.setObservations(updatedProjectApproval.getObservations());

            // Guardar la aprobación de proyecto actualizada
            ProjectApprovalStepTwo savedApproval = projectApprovalStepTwoRepository.save(approval);

            // Crear el paso de asignación de jurados si el proyecto está aprobado
            if (savedApproval.isApprovedProject()) {
                JuryAppointmentStepThree juryAppointment = new JuryAppointmentStepThree();
                juryAppointment.setProjectApprovalStepTwo(savedApproval);
                juryAppointment.setPresident(null);
                juryAppointment.setFirstMember(null);
                juryAppointment.setSecondMember(null);
                juryAppointment.setMeetRequirements(false);
                juryAppointment.setAccessory(null);
                juryAppointment.setObservations(null);

                // Guardar la asignación de jurados
                juryAppointmentStepThreeRepository.save(juryAppointment);
            }

            return Optional.of(savedApproval);
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
