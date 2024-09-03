package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.NotificationJurorsStepEleven;
import pe.edu.unamba.academic.services.steps.NotificationJurorsStepElevenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificacion_jurados")
public class NotificationJurorsStepElevenController {

    @Autowired
    private NotificationJurorsStepElevenService notificationJurorsStepElevenService;

    @GetMapping("/")
    public List<NotificationJurorsStepEleven> getAllNotifications() {
        return notificationJurorsStepElevenService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationJurorsStepEleven> getNotificationById(@PathVariable Long id) {
        return notificationJurorsStepElevenService.getNotificationById(id)
                .map(notification -> ResponseEntity.ok().body(notification))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<NotificationJurorsStepEleven> saveNotification(@RequestBody NotificationJurorsStepEleven notification) {
        try {
            return ResponseEntity.ok().body(notificationJurorsStepElevenService.saveNotification(notification));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationJurorsStepEleven> updateNotification(@PathVariable Long id, @RequestBody NotificationJurorsStepEleven notificationDetails) {
        try {
            return ResponseEntity.ok().body(notificationJurorsStepElevenService.updateNotification(id, notificationDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        try {
            notificationJurorsStepElevenService.deleteNotification(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
