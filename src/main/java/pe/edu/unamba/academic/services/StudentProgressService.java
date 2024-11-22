package pe.edu.unamba.academic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.StudentProgress;
import pe.edu.unamba.academic.models.steps.*;
import pe.edu.unamba.academic.repositories.*; // Asegúrate de importar los repositorios necesarios
import pe.edu.unamba.academic.repositories.actors.StudentRepository;
import pe.edu.unamba.academic.repositories.steps.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentProgressService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TitleReservationStepOneRepository stepOneRepository;
    private final ProjectApprovalStepTwoRepository stepTwoRepository;
    private final JuryAppointmentStepThreeRepository stepThreeRepository;
    private final ReportReviewStepFourRepository stepFourRepository;
    private final ConstancyThesisStepFiveRepository stepFiveRepository;
    private final JuryNotificationsStepSixRepository stepSixRepository;
    private final ThesisApprovalStepSevenRepository stepSevenRepository;
    private final PastingApprovalStepEightRepository stepEightRepository;

    public List<StudentProgress> getProgressByStudent(String studentCode) {
        List<StudentProgress> progressList = new ArrayList<>();

        // Step 1: Title Reservation
        Optional<TitleReservationStepOne> stepOne = stepOneRepository.findByStudent_StudentCode(studentCode);
        double stepOneCompletion = 0.0;
        boolean stepOneCompleted = false;

        if (stepOne.isPresent()) {
            TitleReservationStepOne stepOneData = stepOne.get();
            stepOneCompleted = stepOneData.isMeetsRequirements();
            if (stepOneData.isMeetsRequirements()) {
                stepOneCompletion = 100.0;
            } else if (stepOneData.getPdfDocument() != null) {
                stepOneCompletion = 75.0;
            } else {
                stepOneCompletion = 30.0;
            }
        }

        progressList.add(new StudentProgress(
                "Title Reservation",
                stepOneCompleted,
                stepOneCompletion,
                stepOne.orElse(null)
        ));

        // Additional steps...

        return progressList;
    }
}
