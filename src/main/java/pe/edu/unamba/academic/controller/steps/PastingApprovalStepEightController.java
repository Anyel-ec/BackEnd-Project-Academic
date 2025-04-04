package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.PastingApprovalStepEight;
import pe.edu.unamba.academic.services.steps.PastingApprovalStepEightService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aprobacion_empastado")
public class PastingApprovalStepEightController {

    private final PastingApprovalStepEightService pastingApprovalService;

    // Obtener todos los registros
    @GetMapping("/")
    public ResponseEntity<List<PastingApprovalStepEight>> getAll() {
        List<PastingApprovalStepEight> list = pastingApprovalService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/student/{studentCode}")
    public ResponseEntity<?> getPastingApprovalByStudentCode(@PathVariable String studentCode) {
        Optional<PastingApprovalStepEight> pastingApproval = pastingApprovalService.getPastingApprovalByStudentCode(studentCode);
        if (pastingApproval.isPresent()) {
            return ResponseEntity.ok(pastingApproval.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una aprobación de empastados para el código de estudiante proporcionado.");
        }
    }
    // Obtener un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<PastingApprovalStepEight> getById(@PathVariable Long id) {
        return pastingApprovalService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Guardar un nuevo registro
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody PastingApprovalStepEight pastingApprovalStepEight) {
        pastingApprovalService.save(pastingApprovalStepEight);
        return ResponseEntity.ok().build();
    }

    // Actualizar un registro existente
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PastingApprovalStepEight pastingApprovalStepEight) {
        pastingApprovalStepEight.setId(id); // Asegura que el ID de la URL se use
        pastingApprovalService.update(pastingApprovalStepEight);
        return ResponseEntity.ok().build();
    }

    // Eliminar un registro por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        pastingApprovalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
