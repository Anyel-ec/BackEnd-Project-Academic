package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.ExtensionPeriodStepEight;
import pe.edu.unamba.academic.services.steps.ExtensionPeriodStepEightService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ampliacion_plazo")
public class ExtensionPeriodStepEightController {

    @Autowired
    private ExtensionPeriodStepEightService extensionPeriodStepEightService;

    @GetMapping("/")
    public List<ExtensionPeriodStepEight> getAllExtensions() {
        return extensionPeriodStepEightService.getAllExtensions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtensionPeriodStepEight> getExtensionById(@PathVariable Long id) {
        return extensionPeriodStepEightService.getExtensionById(id)
                .map(extension -> ResponseEntity.ok().body(extension))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<ExtensionPeriodStepEight> saveExtension(@RequestBody ExtensionPeriodStepEight extension) {
        try {
            return ResponseEntity.ok().body(extensionPeriodStepEightService.saveExtension(extension));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtensionPeriodStepEight> updateExtension(@PathVariable Long id, @RequestBody ExtensionPeriodStepEight extensionDetails) {
        try {
            return ResponseEntity.ok().body(extensionPeriodStepEightService.updateExtension(id, extensionDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExtension(@PathVariable Long id) {
        try {
            extensionPeriodStepEightService.deleteExtension(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
