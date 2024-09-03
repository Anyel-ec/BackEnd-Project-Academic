package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.JuryRecompositionStepFive;
import pe.edu.unamba.academic.services.steps.JuryRecompositionStepFiveService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recomposicion_jurados")
public class JuryRecompositionStepFiveController {

    @Autowired
    private JuryRecompositionStepFiveService juryRecompositionStepFiveService;

    @GetMapping("/")
    public List<JuryRecompositionStepFive> getAllJuryRecompositions() {
        return juryRecompositionStepFiveService.getAllJuryRecompositions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuryRecompositionStepFive> getJuryRecompositionById(@PathVariable Long id) {
        return juryRecompositionStepFiveService.getJuryRecompositionById(id)
                .map(juryRecomposition -> ResponseEntity.ok().body(juryRecomposition))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<JuryRecompositionStepFive> saveJuryRecomposition(@RequestBody JuryRecompositionStepFive juryRecomposition) {
        try {
            return ResponseEntity.ok().body(juryRecompositionStepFiveService.saveJuryRecomposition(juryRecomposition));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuryRecompositionStepFive> updateJuryRecomposition(@PathVariable Long id, @RequestBody JuryRecompositionStepFive juryRecompositionDetails) {
        try {
            return ResponseEntity.ok().body(juryRecompositionStepFiveService.updateJuryRecomposition(id, juryRecompositionDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJuryRecomposition(@PathVariable Long id) {
        try {
            juryRecompositionStepFiveService.deleteJuryRecomposition(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
