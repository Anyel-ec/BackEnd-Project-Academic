package pe.edu.unamba.academic.controller.steps;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;
import pe.edu.unamba.academic.repositories.steps.ProjectApprovalStepTwoRepository;
import pe.edu.unamba.academic.services.steps.ProjectApprovalStepTwoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
    @RequestMapping("/api/v1/aprobacion_proyecto")
public class ProjectApprovalStepTwoController {

    private final ProjectApprovalStepTwoService projectApprovalStepTwoService;
    private final ProjectApprovalStepTwoRepository projectApprovalStepTwoRepository;

    // Obtener todas las aprobaciones de proyectos
    @GetMapping
    public ResponseEntity<List<ProjectApprovalStepTwo>> getAllProjectApprovals() {
        List<ProjectApprovalStepTwo> approvals = projectApprovalStepTwoService.getAllProjectApprovals();
        return ResponseEntity.ok(approvals);
    }

    // Obtener una aprobación de proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectApprovalStepTwo> getProjectApprovalById(@PathVariable Long id) {
        Optional<ProjectApprovalStepTwo> approval = projectApprovalStepTwoService.getProjectApprovalById(id);
        return approval.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Crear una nueva aprobación de proyecto
    @PostMapping
    public ResponseEntity<ProjectApprovalStepTwo> createProjectApproval(@Valid @RequestBody ProjectApprovalStepTwo projectApprovalStepTwo) {
        ProjectApprovalStepTwo savedApproval = projectApprovalStepTwoService.createProjectApproval(projectApprovalStepTwo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApproval);
    }

    @GetMapping("/student/{studentCode}")
    public ResponseEntity<ProjectApprovalStepTwo> getByStudentCode(@PathVariable String studentCode) {
        Optional<ProjectApprovalStepTwo> step = projectApprovalStepTwoRepository.findByTitleReservationStepOne_Student_StudentCode(studentCode);
        return step.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Actualizar una aprobación de proyecto existente
    @PutMapping("/{id}")
    public ResponseEntity<Optional<ProjectApprovalStepTwo>> updateProjectApproval(@PathVariable Long id, @Valid @RequestBody ProjectApprovalStepTwo updatedProjectApproval) {
        Optional<Optional<ProjectApprovalStepTwo>> savedApproval = projectApprovalStepTwoService.updateProjectApproval(id, updatedProjectApproval);
        return savedApproval.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una aprobación de proyecto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectApproval(@PathVariable Long id) {
        if (projectApprovalStepTwoService.deleteProjectApproval(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
