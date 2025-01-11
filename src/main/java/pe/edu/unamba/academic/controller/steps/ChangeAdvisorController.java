package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.ChangeAdvisor;
import pe.edu.unamba.academic.services.steps.ChangeAdvisorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/change_advisors")
@RequiredArgsConstructor
public class ChangeAdvisorController {

    private final ChangeAdvisorService changeAdvisorService;

    // Get all ChangeAdvisors
    @GetMapping
    public ResponseEntity<List<ChangeAdvisor>> getAllChangeAdvisors() {
        List<ChangeAdvisor> changeAdvisors = changeAdvisorService.getAllJuryRecompositions();
        return ResponseEntity.ok(changeAdvisors);
    }

    // Get ChangeAdvisor by ID
    @GetMapping("/{id}")
    public ResponseEntity<ChangeAdvisor> getChangeAdvisorById(@PathVariable Long id) {
        return changeAdvisorService.getJuryRecompositionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update ChangeAdvisor
    @PutMapping("/{id}")
    public ResponseEntity<ChangeAdvisor> updateChangeAdvisor(
            @PathVariable Long id,
            @RequestBody ChangeAdvisor updatedChangeAdvisor) {
        try {
            ChangeAdvisor changeAdvisor = changeAdvisorService.updateChangeAdvisor(id, updatedChangeAdvisor);
            return ResponseEntity.ok(changeAdvisor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}