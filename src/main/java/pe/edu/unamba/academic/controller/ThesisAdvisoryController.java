package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.ThesisAdvisory;
import pe.edu.unamba.academic.services.ThesisAdvisoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/thesis-advisories")
public class ThesisAdvisoryController {

    @Autowired
    private ThesisAdvisoryService thesisAdvisoryService;

    @GetMapping("/")
    public List<ThesisAdvisory> getAllAdvisories() {
        return thesisAdvisoryService.getAllAdvisories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThesisAdvisory> getAdvisoryById(@PathVariable Long id) {
        Optional<ThesisAdvisory> advisory = thesisAdvisoryService.getAdvisoryById(id);
        return advisory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<ThesisAdvisory> saveAdvisory(@RequestBody ThesisAdvisory advisory) {
        advisory.setReservationTitle(null);
        advisory.setFilterCertificate(null);
        advisory.setThesisApproval(null);
        advisory.setJuryDesignation(null);
        advisory.setJuryRecomposition(null);
        advisory.setFirstReview(null);
        advisory.setLastReview(null);
        advisory.setExtensionPeriod(null);
        advisory.setPresentationCertificate(null);
        advisory.setPresentationDate(null);
        advisory.setJuryNotification(null);
        advisory.setPresentationApproval(null);
        advisory.setBindingDeliveryCertificate(null);

        ThesisAdvisory savedAdvisory = thesisAdvisoryService.saveAdvisory(advisory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdvisory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvisory(@PathVariable Long id) {
        thesisAdvisoryService.deleteAdvisory(id);
        return ResponseEntity.noContent().build();
    }
}
