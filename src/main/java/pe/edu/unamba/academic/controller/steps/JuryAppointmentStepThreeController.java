package pe.edu.unamba.academic.controller.steps;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.actors.Teacher;
import pe.edu.unamba.academic.models.steps.JuryAppointmentStepThree;
import pe.edu.unamba.academic.repositories.actors.TeacherRepository;
import pe.edu.unamba.academic.repositories.steps.JuryAppointmentStepThreeRepository;
import pe.edu.unamba.academic.services.steps.JuryAppointmentStepThreeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asignacion_jurados")
public class JuryAppointmentStepThreeController {

    private final JuryAppointmentStepThreeService juryAppointmentStepThreeService;
    private final JuryAppointmentStepThreeRepository juryRepository;
    private final TeacherRepository teacherRepository;

    @GetMapping("/")
    public ResponseEntity<List<JuryAppointmentStepThree>>getAllJuryAppointmentStepThrees() {
        List<JuryAppointmentStepThree> jurys = juryAppointmentStepThreeService.getAllJuryAppointment();
        return ResponseEntity.ok(jurys);
    }
    @GetMapping("/{id}")
    public ResponseEntity<JuryAppointmentStepThree> getJuryAppointmentById(@PathVariable Long id) {
        Optional<JuryAppointmentStepThree> jury = juryAppointmentStepThreeService.getJuryAppointmentById(id);
        return jury.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

    }
    @GetMapping("/student/{studentCode}")
    public ResponseEntity<JuryAppointmentStepThree> getByStudentCode(@PathVariable String studentCode) {
        Optional<JuryAppointmentStepThree> step = juryAppointmentStepThreeService.getJuryByAnyStudentCode(studentCode);
        return step.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<JuryAppointmentStepThree> createJuryAppointment(@Valid @RequestBody JuryAppointmentStepThree juryAppointmentStepThree) {
        JuryAppointmentStepThree savedJury = juryAppointmentStepThreeService.createJuryAppointment(juryAppointmentStepThree);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJury);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJury(@PathVariable Long id, @RequestBody JuryAppointmentStepThree juryData) {
        JuryAppointmentStepThree jury = juryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JuryAppointmentStepThree not found"));

        // Asignar presidente si se proporciona
        if (juryData.getPresident() != null && juryData.getPresident().getId() != null) {
            Teacher president = teacherRepository.findById(juryData.getPresident().getId())
                    .orElseThrow(() -> new RuntimeException("President not found"));
            jury.setPresident(president);
        }

        // Asignar primer miembro si se proporciona
        if (juryData.getFirstMember() != null && juryData.getFirstMember().getId() != null) {
            Teacher firstMember = teacherRepository.findById(juryData.getFirstMember().getId())
                    .orElseThrow(() -> new RuntimeException("First Member not found"));
            jury.setFirstMember(firstMember);
        }

        // Asignar segundo miembro si se proporciona
        if (juryData.getSecondMember() != null && juryData.getSecondMember().getId() != null) {
            Teacher secondMember = teacherRepository.findById(juryData.getSecondMember().getId())
                    .orElseThrow(() -> new RuntimeException("Second Member not found"));
            jury.setSecondMember(secondMember);
        }

        // Asignar accesitario si se proporciona
        if (juryData.getAccessory() != null && juryData.getAccessory().getId() != null) {
            Teacher accessory = teacherRepository.findById(juryData.getAccessory().getId())
                    .orElseThrow(() -> new RuntimeException("Accessory not found"));
            jury.setAccessory(accessory);
        }

        // Actualizar otros campos directamente
        if (juryData.getObservations() != null) {
            jury.setObservations(juryData.getObservations());
        }
        jury.setMeetRequirements(juryData.isMeetRequirements());

        // Guardar cambios en la base de datos
        juryRepository.save(jury);

        // Enviar correo si cumple con los requisitos
        if (jury.isMeetRequirements()) {
            juryAppointmentStepThreeService.sendJuryEmails(jury);
            juryAppointmentStepThreeService.createStepFourIfNeeded(jury);
        }

        return ResponseEntity.ok("Jury updated successfully");
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

