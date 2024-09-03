package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.ThesisApprovalStepThree;
import pe.edu.unamba.academic.services.steps.ThesisApprovalStepThreeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aprobacion_tesis")
public class ThesisApprovalStepThreeController {

    @Autowired
    private ThesisApprovalStepThreeService thesisApprovalStepThreeService;

    @GetMapping("/")
    public List<ThesisApprovalStepThree> getAllThesisApprovals() {
        return thesisApprovalStepThreeService.getAllThesisApprovals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThesisApprovalStepThree> getThesisApprovalById(@PathVariable Long id) {
        return thesisApprovalStepThreeService.getThesisApprovalById(id)
                .map(thesisApproval -> ResponseEntity.ok().body(thesisApproval))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<ThesisApprovalStepThree> saveThesisApproval(@RequestBody ThesisApprovalStepThree thesisApproval) {
        try {
            return ResponseEntity.ok().body(thesisApprovalStepThreeService.saveThesisApproval(thesisApproval));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThesisApprovalStepThree> updateThesisApproval(@PathVariable Long id, @RequestBody ThesisApprovalStepThree thesisApprovalDetails) {
        try {
            return ResponseEntity.ok().body(thesisApprovalStepThreeService.updateThesisApproval(id, thesisApprovalDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThesisApproval(@PathVariable Long id) {
        try {
            thesisApprovalStepThreeService.deleteThesisApproval(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
