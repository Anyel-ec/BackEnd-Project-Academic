package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;
import pe.edu.unamba.academic.repositories.steps.ProjectApprovalStepTwoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/aprobacion_proyecto")
public class ProjectApprovalStepTwoController {

    @Autowired
    private ProjectApprovalStepTwoRepository projectApprovalStepTwoRepository;

    // Obtener todas las aprobaciones de proyectos
    @GetMapping
    public ResponseEntity<List<ProjectApprovalStepTwo>> getAllProjectApprovals() {
        List<ProjectApprovalStepTwo> approvals = projectApprovalStepTwoRepository.findAll();
        return ResponseEntity.ok(approvals);
    }

    // Obtener una aprobación de proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectApprovalStepTwo> getProjectApprovalById(@PathVariable Long id) {
        Optional<ProjectApprovalStepTwo> approval = projectApprovalStepTwoRepository.findById(id);
        return approval.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva aprobación de proyecto
    @PostMapping
    public ResponseEntity<ProjectApprovalStepTwo> createProjectApproval(@RequestBody ProjectApprovalStepTwo projectApprovalStepTwo) {
        ProjectApprovalStepTwo savedApproval = projectApprovalStepTwoRepository.save(projectApprovalStepTwo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApproval);
    }

    // Actualizar una aprobación de proyecto existente
    @PutMapping("/{id}")
    public ResponseEntity<ProjectApprovalStepTwo> updateProjectApproval(@PathVariable Long id, @RequestBody ProjectApprovalStepTwo updatedProjectApproval) {
        Optional<ProjectApprovalStepTwo> existingApproval = projectApprovalStepTwoRepository.findById(id);

        if (existingApproval.isPresent()) {
            ProjectApprovalStepTwo approval = existingApproval.get();
            approval.setTitleReservationStepOne(updatedProjectApproval.getTitleReservationStepOne());
            approval.setAdviser(updatedProjectApproval.getAdviser());
            approval.setDisable(updatedProjectApproval.isDisable());
            approval.setObservations(updatedProjectApproval.getObservations());

            ProjectApprovalStepTwo savedApproval = projectApprovalStepTwoRepository.save(approval);
            return ResponseEntity.ok(savedApproval);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una aprobación de proyecto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectApproval(@PathVariable Long id) {
        if (projectApprovalStepTwoRepository.existsById(id)) {
            projectApprovalStepTwoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
