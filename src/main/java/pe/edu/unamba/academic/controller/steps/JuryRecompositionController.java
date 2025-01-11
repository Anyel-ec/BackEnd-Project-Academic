package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.JuryRecomposition;
import pe.edu.unamba.academic.services.steps.JuryRecompositionService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/jury_recompositions")
public class JuryRecompositionController {

    private final JuryRecompositionService juryRecompositionService;

    // Obtener todas las recomposiciones de jurados
    @GetMapping
    public ResponseEntity<List<JuryRecomposition>> getAllJuryRecompositions() {
        List<JuryRecomposition> juryRecompositions = juryRecompositionService.getAllJuryRecompositions();
        return ResponseEntity.ok(juryRecompositions);
    }

    // Obtener una recomposición de jurado por ID
    @GetMapping("/{id}")
    public ResponseEntity<JuryRecomposition> getJuryRecompositionById(@PathVariable Long id) {
        Optional<JuryRecomposition> juryRecomposition = juryRecompositionService.getJuryRecompositionById(id);
        return juryRecomposition.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar una recomposición de jurado por ID
    @PutMapping("/{id}")
    public ResponseEntity<JuryRecomposition> updateJuryRecomposition(@PathVariable Long id, @RequestBody JuryRecomposition updatedJuryRecomposition) {
        try {
            JuryRecomposition updated = juryRecompositionService.updateJuryRecomposition(id, updatedJuryRecomposition);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
