package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ConstancyThesisStepFive;
import pe.edu.unamba.academic.models.steps.ReportReviewStepFour;
import pe.edu.unamba.academic.repositories.steps.ReportReviewStepFourRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReportReviewStepFourService {
    private final ReportReviewStepFourRepository reportReviewStepFourRepository;
    private final ConstancyThesisStepFiveService constancyThesisStepFiveService;

    public List<ReportReviewStepFour> getAllReportReview() {
        return reportReviewStepFourRepository.findAll();
    }

    public Optional<ReportReviewStepFour> getReportReviewById(Long id) {
        return reportReviewStepFourRepository.findById(id);
    }

    public ReportReviewStepFour createReportReview(ReportReviewStepFour reportReviewStepFour) {
        return reportReviewStepFourRepository.save(reportReviewStepFour);
    }
    public Optional<ReportReviewStepFour> getReportReviewByStudentCode(String studentCode) {
        return reportReviewStepFourRepository.findByAnyStudentCodeNative(studentCode);
    }
    public Optional<ReportReviewStepFour> updateReportReview(Long id, ReportReviewStepFour updatedReportReview) {
        return reportReviewStepFourRepository.findById(id).map(report -> {
            if (updatedReportReview.getJuryAppointmentStepThree() != null) {
                report.setJuryAppointmentStepThree(updatedReportReview.getJuryAppointmentStepThree());
            }
            report.setMeetRequirements(updatedReportReview.isMeetRequirements()); // Este es booleano, por lo que siempre se asigna
            if (updatedReportReview.getObservations() != null) {
                report.setObservations(updatedReportReview.getObservations());
            }
            report.setDisable(updatedReportReview.isDisable());

            if (updatedReportReview.isMeetRequirements()) {
                ConstancyThesisStepFive newConstancyThesis = new ConstancyThesisStepFive();
                newConstancyThesis.setReportReviewStepFour(report); // Asocia este reporte
                newConstancyThesis.setObservations(report.getObservations());
                newConstancyThesis.setMeetsRequirements(false);
                constancyThesisStepFiveService.createConstancyThesis(newConstancyThesis);
            }

            return reportReviewStepFourRepository.save(report);
        });
    }

    public boolean deleteReportReview(Long id) {
        if (reportReviewStepFourRepository.existsById(id)) {
            reportReviewStepFourRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
