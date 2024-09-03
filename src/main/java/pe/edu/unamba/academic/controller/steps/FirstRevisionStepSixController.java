package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.FirstRevisionStepSix;
import pe.edu.unamba.academic.services.steps.FirstRevisionStepSixService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/primera_revision")
public class FirstRevisionStepSixController {

    @Autowired
    private FirstRevisionStepSixService firstRevisionStepSixService;

    @GetMapping("/")
    public List<FirstRevisionStepSix> getAllFirstRevisions() {
        return firstRevisionStepSixService.getAllFirstRevisions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FirstRevisionStepSix> getFirstRevisionById(@PathVariable Long id) {
        return firstRevisionStepSixService.getFirstRevisionById(id)
                .map(firstRevision -> ResponseEntity.ok().body(firstRevision))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<FirstRevisionStepSix> saveFirstRevision(@RequestBody FirstRevisionStepSix firstRevision) {
        try {
            return ResponseEntity.ok().body(firstRevisionStepSixService.saveFirstRevision(firstRevision));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FirstRevisionStepSix> updateFirstRevision(@PathVariable Long id, @RequestBody FirstRevisionStepSix firstRevisionDetails) {
        try {
            return ResponseEntity.ok().body(firstRevisionStepSixService.updateFirstRevision(id, firstRevisionDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFirstRevision(@PathVariable Long id) {
        try {
            firstRevisionStepSixService.deleteFirstRevision(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
