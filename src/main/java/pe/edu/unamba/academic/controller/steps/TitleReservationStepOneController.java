package pe.edu.unamba.academic.controller.steps;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
import pe.edu.unamba.academic.services.steps.TitleReservationStepOneService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservas_titulo")
public class TitleReservationStepOneController {


    private final TitleReservationStepOneService titleReservationStepOneService;

    public TitleReservationStepOneController(TitleReservationStepOneService titleReservationStepOneService) {
        this.titleReservationStepOneService = titleReservationStepOneService;
    }

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
    public ResponseEntity<?> saveTitleReservation(@RequestBody TitleReservationStepOne titleReservation) {
        try {
            if (titleReservationStepOneService.isTitleDuplicate(titleReservation.getTitle())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El título ya existe. Por favor elige otro.");
            }

            TitleReservationStepOne savedReservation = titleReservationStepOneService.saveTitleReservation(titleReservation);
            return ResponseEntity.ok(savedReservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar la reservación: " + e.getMessage());
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
    @GetMapping("/check-title")
    public ResponseEntity<?> checkTitleExists(@RequestParam String title) {
        boolean exists = titleReservationStepOneService.existsByTitle(title);
        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El título ya existe.");
        }
        return ResponseEntity.ok().build();
    }
}
