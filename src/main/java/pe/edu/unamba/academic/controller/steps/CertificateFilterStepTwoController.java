package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.CertificateFilterStepTwo;
import pe.edu.unamba.academic.services.steps.CertificateFilterStepTwoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/constancias_filtro")
public class CertificateFilterStepTwoController {

    @Autowired
    private CertificateFilterStepTwoService certificateFilterStepTwoService;

    @GetMapping("/")
    public List<CertificateFilterStepTwo> getAllCertificateFilters() {
        return certificateFilterStepTwoService.getAllCertificateFilters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateFilterStepTwo> getCertificateFilterById(@PathVariable Long id) {
        return certificateFilterStepTwoService.getCertificateFilterById(id)
                .map(certificateFilter -> ResponseEntity.ok().body(certificateFilter))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<CertificateFilterStepTwo> saveCertificateFilter(@RequestBody CertificateFilterStepTwo certificateFilter) {
        try {
            return ResponseEntity.ok().body(certificateFilterStepTwoService.saveCertificateFilter(certificateFilter));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificateFilterStepTwo> updateCertificateFilter(@PathVariable Long id, @RequestBody CertificateFilterStepTwo certificateFilterDetails) {
        try {
            return ResponseEntity.ok().body(certificateFilterStepTwoService.updateCertificateFilter(id, certificateFilterDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificateFilter(@PathVariable Long id) {
        try {
            certificateFilterStepTwoService.deleteCertificateFilter(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
