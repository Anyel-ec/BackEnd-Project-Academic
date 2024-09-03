package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.FilterCertificateNumber;
import pe.edu.unamba.academic.services.FilterCertificateNumberService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/constancias_filtro_numeros")
public class FilterCertificateNumberController {

    @Autowired
    private FilterCertificateNumberService filterCertificateNumberService;

    @GetMapping("/")
    public List<FilterCertificateNumber> getAllFilterCertificateNumbers() {
        return filterCertificateNumberService.getAllFilterCertificateNumbers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilterCertificateNumber> getFilterCertificateNumberById(@PathVariable Long id) {
        return filterCertificateNumberService.getFilterCertificateNumberById(id)
                .map(filterCertificateNumber -> ResponseEntity.ok().body(filterCertificateNumber))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<FilterCertificateNumber> saveFilterCertificateNumber(@RequestBody FilterCertificateNumber filterCertificateNumber) {
        try {
            return ResponseEntity.ok().body(filterCertificateNumberService.saveFilterCertificateNumber(filterCertificateNumber));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilterCertificateNumber> updateFilterCertificateNumber(@PathVariable Long id, @RequestBody FilterCertificateNumber filterCertificateNumberDetails) {
        try {
            return ResponseEntity.ok().body(filterCertificateNumberService.updateFilterCertificateNumber(id, filterCertificateNumberDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilterCertificateNumber(@PathVariable Long id) {
        try {
            filterCertificateNumberService.deleteFilterCertificateNumber(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
