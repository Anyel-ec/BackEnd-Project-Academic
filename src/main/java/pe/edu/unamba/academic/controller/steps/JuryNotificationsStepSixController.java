package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.JuryNotificationsStepSix;
import pe.edu.unamba.academic.services.steps.JuryNotificationsStepSixService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/notificacion_jurados")
public class JuryNotificationsStepSixController {

    private final JuryNotificationsStepSixService juryNotificationsService;

    // Obtener todas las notificaciones de jurado
    @GetMapping
    public ResponseEntity<List<JuryNotificationsStepSix>> getAll() {
        List<JuryNotificationsStepSix> notifications = juryNotificationsService.getJuryNotifications();
        return ResponseEntity.ok(notifications);
    }

    // Obtener una notificación de jurado por ID
    @GetMapping("/{id}")
    public ResponseEntity<JuryNotificationsStepSix> getById(@PathVariable Long id) {
        return juryNotificationsService.getJuryNotification(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear o actualizar una notificación de jurado
    @PostMapping
    public ResponseEntity<Void> saveUpdate(@RequestBody JuryNotificationsStepSix juryNotificationsStepSix) {
        juryNotificationsService.saveOrUpdateJuryNotification(juryNotificationsStepSix);
        return ResponseEntity.ok().build();
    }

    // Eliminar una notificación de jurado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        juryNotificationsService.deleteJuryNotification(id);
        return ResponseEntity.noContent().build();
    }
}
