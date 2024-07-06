package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.JuryDesignationStepFour;
import pe.edu.unamba.academic.services.JuryDesignationStepFourService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/designacion_jurados")
public class JuryDesignationStepFourController {

    @Autowired
    private JuryDesignationStepFourService juryDesignationStepFourService;

    @GetMapping("/")
    public List<JuryDesignationStepFour> getAllJuryDesignations() {
        return juryDesignationStepFourService.getAllJuryDesignations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuryDesignationStepFour> getJuryDesignationById(@PathVariable Long id) {
        return juryDesignationStepFourService.getJuryDesignationById(id)
                .map(juryDesignation -> ResponseEntity.ok().body(juryDesignation))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<JuryDesignationStepFour> saveJuryDesignation(@RequestBody JuryDesignationStepFour juryDesignation) {
        try {
            return ResponseEntity.ok().body(juryDesignationStepFourService.saveJuryDesignation(juryDesignation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuryDesignationStepFour> updateJuryDesignation(@PathVariable Long id, @RequestBody JuryDesignationStepFour juryDesignationDetails) {
        try {
            return ResponseEntity.ok().body(juryDesignationStepFourService.updateJuryDesignation(id, juryDesignationDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJuryDesignation(@PathVariable Long id) {
        try {
            juryDesignationStepFourService.deleteJuryDesignation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
