package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.LastRevisionStepSeven;
import pe.edu.unamba.academic.services.steps.LastRevisionStepSevenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ultima_revision")
public class LastRevisionStepSevenController {

    @Autowired
    private LastRevisionStepSevenService lastRevisionStepSevenService;

    @GetMapping("/")
    public List<LastRevisionStepSeven> getAllLastRevisions() {
        return lastRevisionStepSevenService.getAllLastRevisions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LastRevisionStepSeven> getLastRevisionById(@PathVariable Long id) {
        return lastRevisionStepSevenService.getLastRevisionById(id)
                .map(lastRevision -> ResponseEntity.ok().body(lastRevision))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<LastRevisionStepSeven> saveLastRevision(@RequestBody LastRevisionStepSeven lastRevision) {
        try {
            return ResponseEntity.ok().body(lastRevisionStepSevenService.saveLastRevision(lastRevision));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LastRevisionStepSeven> updateLastRevision(@PathVariable Long id, @RequestBody LastRevisionStepSeven lastRevisionDetails) {
        try {
            return ResponseEntity.ok().body(lastRevisionStepSevenService.updateLastRevision(id, lastRevisionDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLastRevision(@PathVariable Long id) {
        try {
            lastRevisionStepSevenService.deleteLastRevision(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
