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
        processTitleReservationStep(progressList, stepOne);

        // Step 2: Project Approval
        Optional<ProjectApprovalStepTwo> stepTwo = stepTwoRepository.findByTitleReservationStepOne(stepOne.orElse(null));
        processProjectApprovalStep(progressList, stepTwo);

        // Additional steps can be added here following the same pattern

        return progressList;
    }

    private void processTitleReservationStep(List<StudentProgress> progressList, Optional<TitleReservationStepOne> stepOne) {
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
                "1",
                stepOneCompleted,
                stepOneCompletion,
                stepOne.orElse(null)
        ));
    }

    private void processProjectApprovalStep(List<StudentProgress> progressList, Optional<ProjectApprovalStepTwo> stepTwo) {
        double stepTwoCompletion = 0.0;
        boolean stepTwoCompleted = false;

        if (stepTwo.isPresent()) {
            ProjectApprovalStepTwo stepTwoData = stepTwo.get();
            stepTwoCompleted = stepTwoData.isApprovedProject();
            if (stepTwoData.isApprovedProject()) {
                stepTwoCompletion = 100.0;
            } else if (stepTwoData.getObservations() != null && !stepTwoData.getObservations().isEmpty()) {
                stepTwoCompletion = 50.0; // Assuming having observations means partial progress
            } else {
                stepTwoCompletion = 10.0; // Minimal progress if the step is created but not yet approved
            }
        }

        progressList.add(new StudentProgress(
                "2",
                stepTwoCompleted,
                stepTwoCompletion,
                stepTwo.orElse(null)
        ));
    }
}