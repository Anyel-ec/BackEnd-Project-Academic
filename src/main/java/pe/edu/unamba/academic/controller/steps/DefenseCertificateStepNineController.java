package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.DefenseCertificateStepNine;
import pe.edu.unamba.academic.services.steps.DefenseCertificateStepNineService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/constancia_sustentacion")
public class DefenseCertificateStepNineController {

    @Autowired
    private DefenseCertificateStepNineService defenseCertificateStepNineService;

    @GetMapping("/")
    public List<DefenseCertificateStepNine> getAllCertificates() {
        return defenseCertificateStepNineService.getAllCertificates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefenseCertificateStepNine> getCertificateById(@PathVariable Long id) {
        return defenseCertificateStepNineService.getCertificateById(id)
                .map(certificate -> ResponseEntity.ok().body(certificate))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<DefenseCertificateStepNine> saveCertificate(@RequestBody DefenseCertificateStepNine certificate) {
        try {
            return ResponseEntity.ok().body(defenseCertificateStepNineService.saveCertificate(certificate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefenseCertificateStepNine> updateCertificate(@PathVariable Long id, @RequestBody DefenseCertificateStepNine certificateDetails) {
        try {
            return ResponseEntity.ok().body(defenseCertificateStepNineService.updateCertificate(id, certificateDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {
        try {
            defenseCertificateStepNineService.deleteCertificate(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
