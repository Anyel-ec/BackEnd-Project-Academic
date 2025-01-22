package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;
import pe.edu.unamba.academic.models.steps.JuryRecomposition;
import pe.edu.unamba.academic.repositories.steps.JuryAppointmentStepThreeRepository;
import pe.edu.unamba.academic.repositories.steps.ProjectApprovalStepTwoRepository;
import pe.edu.unamba.academic.repositories.steps.JuryRecompositionRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectApprovalStepTwoService {
    private final JuryAppointmentStepThreeRepository juryAppointmentStepThreeRepository;
    private final ProjectApprovalStepTwoRepository projectApprovalStepTwoRepository;
    private final JuryRecompositionRepository juryRecompositionRepository;

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
            approval.setMeetRequirements(updatedProjectApproval.getMeetRequirements());
            approval.setObservations(updatedProjectApproval.getObservations());
            approval.setRegistrationNumber(updatedProjectApproval.getRegistrationNumber());
            approval.setReferenceDate(updatedProjectApproval.getReferenceDate());

            // Guardar la aprobación de proyecto actualizada
            ProjectApprovalStepTwo savedApproval = projectApprovalStepTwoRepository.save(approval);

            // Crear el paso de asignación de jurados si el proyecto cumple con los requisitos
            if (savedApproval.getMeetRequirements()) {
                // Crear y guardar la asignación de jurados
                JuryAppointmentStepThree juryAppointment = new JuryAppointmentStepThree();
                juryAppointment.setProjectApprovalStepTwo(savedApproval);
                juryAppointment.setPresident(null);
                juryAppointment.setFirstMember(null);
                juryAppointment.setSecondMember(null);
                juryAppointment.setMeetRequirements(false);
                juryAppointment.setAccessory(null);
                juryAppointment.setObservations(null);
                juryAppointment.setRegistrationNumber(null);
                juryAppointmentStepThreeRepository.save(juryAppointment);

                // Crear y guardar la recomposición de jurados
                JuryRecomposition juryRecomposition = new JuryRecomposition();
                juryRecomposition.setProjectApprovalStepTwo(savedApproval);
                juryRecomposition.setObservations(null);
                juryRecomposition.setDisable(false);
                juryRecomposition.setMeetsRequirements(false);

                juryRecompositionRepository.save(juryRecomposition);
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
