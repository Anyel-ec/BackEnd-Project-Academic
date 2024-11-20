package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.PastingApprovalStepEight;
import pe.edu.unamba.academic.services.steps.PastingApprovalStepEightService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aprobacion_empastado")
public class PastingApprovalStepEightController {

    private final PastingApprovalStepEightService pastingApprovalService;

    // Obtener todos los registros
    @GetMapping
    public ResponseEntity<List<PastingApprovalStepEight>> getAll() {
        List<PastingApprovalStepEight> list = pastingApprovalService.getAll();
        return ResponseEntity.ok(list);
    }

    // Obtener un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<PastingApprovalStepEight> getById(@PathVariable Long id) {
        return pastingApprovalService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Guardar o actualizar un registro
    @PostMapping
    public ResponseEntity<Void> saveUpdate( @RequestBody PastingApprovalStepEight pastingApprovalStepEight) {
        pastingApprovalService.saveOrUpdate(pastingApprovalStepEight);
        return ResponseEntity.ok().build();
    }

    // Eliminar un registro por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        pastingApprovalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
