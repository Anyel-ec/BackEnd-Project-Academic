package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.ReservationStepStatus;
import pe.edu.unamba.academic.services.steps.ReservationStepStatusService;

import java.util.List;

@RestController
@RequestMapping("/api/steps")
public class ReservationStepStatusController {

    @Autowired
    private ReservationStepStatusService reservationStepStatusService;

    @PostMapping("/update")
    public ResponseEntity<ReservationStepStatus> updateStepStatus(@RequestBody ReservationStepStatus status) {
        ReservationStepStatus updatedStatus = reservationStepStatusService.saveOrUpdateStepStatus(
                status.getStudentCode(), status.getStepNumber(), status.getIsCompleted());
        return ResponseEntity.ok(updatedStatus);
    }

    @GetMapping("/status")
    public ResponseEntity<ReservationStepStatus> getStepStatus(@RequestParam String studentCode, @RequestParam Integer stepNumber) {
        return reservationStepStatusService.getStepStatus(studentCode, stepNumber)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/progress/{studentCode}")
    public ResponseEntity<List<ReservationStepStatus>> getAllStepStatusByStudent(@PathVariable String studentCode) {
        List<ReservationStepStatus> statusList = reservationStepStatusService.getAllStepStatusByStudent(studentCode);
        if (statusList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(statusList);
    }
}
