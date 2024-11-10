package pe.edu.unamba.academic.controller.steps;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;
import pe.edu.unamba.academic.services.steps.JuryAppointmentStepThreeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asignacion_jurados")
public class JuryAppointmentStepThreeController {

    private final JuryAppointmentStepThreeService juryAppointmentStepThreeService;

    @GetMapping
    public ResponseEntity<List<JuryAppointmentStepThree>>getAllJuryAppointmentStepThrees() {
        List<JuryAppointmentStepThree> jurys = juryAppointmentStepThreeService.getAllJuryAppointment();
        return ResponseEntity.ok(jurys);
    }
    @GetMapping("/{id}")
    public ResponseEntity<JuryAppointmentStepThree> getJuryAppointmentById(@PathVariable Long id) {
        Optional<JuryAppointmentStepThree> jury = juryAppointmentStepThreeService.getJuryAppointmentById(id);
        return jury.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

    }
    @PostMapping
    public ResponseEntity<JuryAppointmentStepThree> createJuryAppointment(@Valid @RequestBody JuryAppointmentStepThree juryAppointmentStepThree) {
        JuryAppointmentStepThree savedJury = juryAppointmentStepThreeService.createJuryAppointment(juryAppointmentStepThree);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJury);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuryAppointmentStepThree> updateJuryAppointment (@PathVariable Long id, @Valid @RequestBody JuryAppointmentStepThree updateJuryAppointment) {
        Optional<JuryAppointmentStepThree> saveJury = juryAppointmentStepThreeService.updateJuryAppointment(id, updateJuryAppointment);
        return saveJury.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJuryAppointment(@PathVariable Long id) {
        if (juryAppointmentStepThreeService.deleteJuryAppointment(id)) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paso 3 con ID " + id + " no encontrado.");        }
    }
}

