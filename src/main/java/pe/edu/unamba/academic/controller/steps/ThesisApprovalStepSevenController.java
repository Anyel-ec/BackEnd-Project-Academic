package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.ThesisApprovalStepSeven;
import pe.edu.unamba.academic.services.steps.ThesisApprovalStepSevenService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aprobacion_tesis")
public class ThesisApprovalStepSevenController {

    private final ThesisApprovalStepSevenService thesisApprovalStepSevenService;

    // Obtener todos los registros
    @GetMapping
    public ResponseEntity<List<ThesisApprovalStepSeven>> getAll() {
        List<ThesisApprovalStepSeven> approvals = thesisApprovalStepSevenService.getThesisApprovals();
        return ResponseEntity.ok(approvals);
    }

    // Obtener un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<ThesisApprovalStepSeven> getById(@PathVariable Long id) {
        return thesisApprovalStepSevenService.getThesisApproval(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo registro
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ThesisApprovalStepSeven thesisApprovalStepSeven) {
        thesisApprovalStepSevenService.saveApproval(thesisApprovalStepSeven);
        return ResponseEntity.ok().build();
    }

    // Actualizar un registro existente
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ThesisApprovalStepSeven thesisApprovalStepSeven) {
        thesisApprovalStepSevenService.updateApproval(thesisApprovalStepSeven);
        return ResponseEntity.ok().build();
    }

    // Eliminar un registro por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        thesisApprovalStepSevenService.deleteThesisApproval(id);
        return ResponseEntity.noContent().build();
    }
}

