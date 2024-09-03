package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.ProgressReport;
import pe.edu.unamba.academic.services.ProgressReportService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/progress-reports")
public class ProgressReportController {

    @Autowired
    private ProgressReportService progressReportService;

    @GetMapping("/")
    public List<ProgressReport> getAllReports() {
        return progressReportService.getAllReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgressReport> getReportById(@PathVariable Long id) {
        Optional<ProgressReport> report = progressReportService.getReportById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<ProgressReport> saveReport(@RequestBody ProgressReport report) {
        ProgressReport savedReport = progressReportService.saveReport(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        progressReportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
