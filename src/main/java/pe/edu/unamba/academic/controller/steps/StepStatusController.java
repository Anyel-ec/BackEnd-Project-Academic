package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.StepStatus;
import pe.edu.unamba.academic.services.steps.StepStatusService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/steps")
public class StepStatusController {

    private final StepStatusService stepStatusService;

    @GetMapping("/{studentCode}")
    public ResponseEntity<List<StepStatus>> getSteps(@PathVariable String studentCode) {
        List<StepStatus> steps = stepStatusService.getStepsForStudent(studentCode);
        return ResponseEntity.ok(steps);
    }


    @PostMapping("/{stepNumber}/{studentCode}")
    public ResponseEntity<StepStatus> completeStep(
            @PathVariable int stepNumber,
            @PathVariable String studentCode
    ) {
        StepStatus stepStatus = stepStatusService.completeStep(studentCode, stepNumber);
        return ResponseEntity.ok(stepStatus);
    }
}
