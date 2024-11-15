package pe.edu.unamba.academic.controller.steps;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.ConstancyThesisStepFive;
import pe.edu.unamba.academic.services.steps.ConstancyThesisStepFiveService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/constancia_tesis")
public class ConstancyThesisStepFiveController {
    private final ConstancyThesisStepFiveService constancyThesisStepFiveService;

    @GetMapping
    public ResponseEntity<List<ConstancyThesisStepFive>> getAllConstancyThesis() {
        List<ConstancyThesisStepFive> thesis = constancyThesisStepFiveService.getAllConstancyThesis();
        return ResponseEntity.ok(thesis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConstancyThesisStepFive> getConstancyThesisById(@PathVariable Long id) {
        Optional<ConstancyThesisStepFive> thesis = constancyThesisStepFiveService.getConstancyThesisById(id);
        return thesis.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConstancyThesisStepFive> createConstancyThesis(
            @Valid @RequestBody ConstancyThesisStepFive constancyThesisStepFive) {
        ConstancyThesisStepFive savedThesis = constancyThesisStepFiveService.createConstancyThesis(constancyThesisStepFive);
        return ResponseEntity.ok(savedThesis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConstancyThesisStepFive> updateConstancyThesis(
            @PathVariable Long id,
            @Valid @RequestBody ConstancyThesisStepFive constancyThesisStepFive) {
        Optional<ConstancyThesisStepFive> saveThesis =
                constancyThesisStepFiveService.updateConstancyThesis(id, constancyThesisStepFive);
        return saveThesis.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConstancyThesisById(@PathVariable Long id) {
        boolean deleted = constancyThesisStepFiveService.deleteConstancyThesisById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
