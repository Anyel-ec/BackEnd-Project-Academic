package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.ApprovalDefenseStepTwelve;
import pe.edu.unamba.academic.services.ApprovalDefenseStepTwelveService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aprobacion_sustentacion")
public class ApprovalDefenseStepTwelveController {

    @Autowired
    private ApprovalDefenseStepTwelveService approvalDefenseStepTwelveService;

    @GetMapping("/")
    public List<ApprovalDefenseStepTwelve> getAllApprovals() {
        return approvalDefenseStepTwelveService.getAllApprovals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApprovalDefenseStepTwelve> getApprovalById(@PathVariable Long id) {
        return approvalDefenseStepTwelveService.getApprovalById(id)
                .map(approval -> ResponseEntity.ok().body(approval))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<ApprovalDefenseStepTwelve> saveApproval(@RequestBody ApprovalDefenseStepTwelve approval) {
        try {
            return ResponseEntity.ok().body(approvalDefenseStepTwelveService.saveApproval(approval));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApprovalDefenseStepTwelve> updateApproval(@PathVariable Long id, @RequestBody ApprovalDefenseStepTwelve approvalDetails) {
        try {
            return ResponseEntity.ok().body(approvalDefenseStepTwelveService.updateApproval(id, approvalDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApproval(@PathVariable Long id) {
        try {
            approvalDefenseStepTwelveService.deleteApproval(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
