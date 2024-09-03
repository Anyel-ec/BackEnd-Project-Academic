package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
import pe.edu.unamba.academic.services.steps.TitleReservationStepOneService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas_titulo")
public class TitleReservationStepOneController {

    @Autowired
    private TitleReservationStepOneService titleReservationStepOneService;

    @GetMapping("/")
    public List<TitleReservationStepOne> getAllTitleReservations() {
        return titleReservationStepOneService.getAllTitleReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TitleReservationStepOne> getTitleReservationById(@PathVariable Long id) {
        return titleReservationStepOneService.getTitleReservationById(id)
                .map(titleReservation -> ResponseEntity.ok().body(titleReservation))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<TitleReservationStepOne> saveTitleReservation(@RequestBody TitleReservationStepOne titleReservation) {
        try {
            return ResponseEntity.ok().body(titleReservationStepOneService.saveTitleReservation(titleReservation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TitleReservationStepOne> updateTitleReservation(@PathVariable Long id, @RequestBody TitleReservationStepOne titleReservationDetails) {
        try {
            return ResponseEntity.ok().body(titleReservationStepOneService.updateTitleReservation(id, titleReservationDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTitleReservation(@PathVariable Long id) {
        try {
            titleReservationStepOneService.deleteTitleReservation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
