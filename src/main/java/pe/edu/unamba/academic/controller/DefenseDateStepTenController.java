package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.DefenseDateStepTen;
import pe.edu.unamba.academic.services.DefenseDateStepTenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fecha_sustentacion")
public class DefenseDateStepTenController {

    @Autowired
    private DefenseDateStepTenService defenseDateStepTenService;

    @GetMapping("/")
    public List<DefenseDateStepTen> getAllDates() {
        return defenseDateStepTenService.getAllDates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefenseDateStepTen> getDateById(@PathVariable Long id) {
        return defenseDateStepTenService.getDateById(id)
                .map(date -> ResponseEntity.ok().body(date))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<DefenseDateStepTen> saveDate(@RequestBody DefenseDateStepTen date) {
        try {
            return ResponseEntity.ok().body(defenseDateStepTenService.saveDate(date));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefenseDateStepTen> updateDate(@PathVariable Long id, @RequestBody DefenseDateStepTen dateDetails) {
        try {
            return ResponseEntity.ok().body(defenseDateStepTenService.updateDate(id, dateDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDate(@PathVariable Long id) {
        try {
            defenseDateStepTenService.deleteDate(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
