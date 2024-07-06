package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.ProgressReport;
import pe.edu.unamba.academic.repositories.ProgressReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressReportService {

    @Autowired
    private ProgressReportRepository progressReportRepository;

    public List<ProgressReport> getAllReports() {
        return progressReportRepository.findAll();
    }

    public Optional<ProgressReport> getReportById(Long id) {
        return progressReportRepository.findById(id);
    }

    public ProgressReport saveReport(ProgressReport report) {
        return progressReportRepository.save(report);
    }

    public void deleteReport(Long id) {
        progressReportRepository.deleteById(id);
    }
}
