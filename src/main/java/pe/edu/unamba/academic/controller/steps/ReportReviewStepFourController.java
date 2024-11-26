package pe.edu.unamba.academic.controller.steps;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;
import pe.edu.unamba.academic.models.steps.ReportReviewStepFour;
import pe.edu.unamba.academic.repositories.steps.ReportReviewStepFourRepository;
import pe.edu.unamba.academic.services.steps.ReportReviewStepFourService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/revision_reporte")
public class ReportReviewStepFourController {
    private final ReportReviewStepFourService reportReviewStepFourService;
    private final ReportReviewStepFourRepository reportReviewStepFourRepository;

    @GetMapping
    public ResponseEntity<List<ReportReviewStepFour>> getAllReportReview() {
        List<ReportReviewStepFour> reports = reportReviewStepFourService.getAllReportReview();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportReviewStepFour> getReportReviewById(@PathVariable Long id) {
        Optional<ReportReviewStepFour> report = reportReviewStepFourService.getReportReviewById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReportReviewStepFour> updateReportReview(@PathVariable Long id, @Valid @RequestBody ReportReviewStepFour updatedReportReview) {
        Optional<ReportReviewStepFour> savedReport = reportReviewStepFourService.updateReportReview(id, updatedReportReview);
        return savedReport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/student/{studentCode}")
    public ResponseEntity<?> getReportReviewByStudentCode(@PathVariable String studentCode) {
        Optional<ReportReviewStepFour> reportReview = reportReviewStepFourService.getReportReviewByAnyStudentCode(studentCode);

        if (reportReview.isPresent()) {
            return ResponseEntity.ok(reportReview.get());
        } else {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);}
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportReviewStepFourById(@PathVariable Long id) {
        if (reportReviewStepFourService.deleteReportReview(id)){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}