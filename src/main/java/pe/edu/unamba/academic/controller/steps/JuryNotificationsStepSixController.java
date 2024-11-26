package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.JuryNotificationsStepSix;
import pe.edu.unamba.academic.services.steps.JuryNotificationsStepSixService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notificacion_jurados")
public class JuryNotificationsStepSixController {

    private final JuryNotificationsStepSixService juryNotificationsService;

    // Obtener todas las notificaciones de jurado
    @GetMapping("/")
    public ResponseEntity<List<JuryNotificationsStepSix>> getAll() {
        List<JuryNotificationsStepSix> notifications = juryNotificationsService.getJuryNotifications();
        return ResponseEntity.ok(notifications);
    }

    // Obtener una notificación de jurado por ID
    @GetMapping("/{id}")
    public ResponseEntity<JuryNotificationsStepSix> getById(@PathVariable Long id) {
        return juryNotificationsService.getJuryNotification(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva notificación de jurado
    @PostMapping("/")
    public ResponseEntity<Void> save(@RequestBody JuryNotificationsStepSix juryNotificationsStepSix) {
        juryNotificationsService.saveJuryNotification(juryNotificationsStepSix);
        return ResponseEntity.ok().build();
    }

    // Actualizar una notificación de jurado existente
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody JuryNotificationsStepSix juryNotificationsStepSix) {
        juryNotificationsStepSix.setId(id); // Asegurarse de que el ID coincida
        juryNotificationsService.updateJuryNotification(juryNotificationsStepSix);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/student/{studentCode}")
    public ResponseEntity<?> getNotificationsByStudentCode(@PathVariable String studentCode) {
        Optional<JuryNotificationsStepSix> notifications = juryNotificationsService.getNotificationsByStudentCode(studentCode);
        if (notifications.isPresent()) {
            return ResponseEntity.ok(notifications.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron notificaciones al jurado para el código de estudiante proporcionado.");
        }
    }
    // Eliminar una notificación de jurado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        juryNotificationsService.deleteJuryNotification(id);
        return ResponseEntity.noContent().build();
    }
}
