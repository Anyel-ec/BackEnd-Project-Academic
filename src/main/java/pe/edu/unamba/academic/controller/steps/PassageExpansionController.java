package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.PassageExpansion;
import pe.edu.unamba.academic.services.steps.PassageExpansionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expansion_tesis")
@RequiredArgsConstructor
public class PassageExpansionController {

    private final PassageExpansionService passageExpansionService;

    // Get all PassageExpansions
    @GetMapping
    public ResponseEntity<List<PassageExpansion>> getAllPassageExpansions() {
        List<PassageExpansion> passageExpansions = passageExpansionService.getAllJuryRecompositions();
        return ResponseEntity.ok(passageExpansions);
    }

    // Get PassageExpansion by ID
    @GetMapping("/{id}")
    public ResponseEntity<PassageExpansion> getPassageExpansionById(@PathVariable Long id) {
        return passageExpansionService.getJuryRecompositionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update PassageExpansion
    @PutMapping("/{id}")
    public ResponseEntity<PassageExpansion> updatePassageExpansion(@PathVariable Long id, @RequestBody PassageExpansion updatedPassageExpansion) {
        try {
            PassageExpansion updated = passageExpansionService.updatePassageExpansion(id, updatedPassageExpansion);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
