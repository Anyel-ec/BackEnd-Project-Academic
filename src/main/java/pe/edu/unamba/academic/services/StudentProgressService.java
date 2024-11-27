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
        Optional<TitleReservationStepOne> stepOne = stepOneRepository.findByAnyStudentCodeNative(studentCode);
        processTitleReservationStep(progressList, stepOne);

// Step 2: Project Approval
        Optional<ProjectApprovalStepTwo> stepTwo = stepTwoRepository.findByTitleReservationStepOne(stepOne.orElse(null));
        processGenericStep(progressList, stepTwo, "2");

// Step 3: Jury Appointment
        Optional<JuryAppointmentStepThree> stepThree = stepThreeRepository.findByProjectApprovalStepTwo(stepTwo.orElse(null));
        processGenericStep(progressList, stepThree, "3");

// Step 4: Report Review
        Optional<ReportReviewStepFour> stepFour = stepFourRepository.findByJuryAppointmentStepThree(stepThree.orElse(null));
        processGenericStep(progressList, stepFour, "4");

// Step 5: Constancy Thesis
        Optional<ConstancyThesisStepFive> stepFive = stepFiveRepository.findByReportReviewStepFour(stepFour.orElse(null));
        processConstancyThesisStep(progressList, stepFive);

// Step 6: Jury Notifications
        Optional<JuryNotificationsStepSix> stepSix = stepSixRepository.findByConstancyThesisStepFive(stepFive.orElse(null));
        processGenericStep(progressList, stepSix, "6");

// Step 7: Thesis Approval
        Optional<ThesisApprovalStepSeven> stepSeven = stepSevenRepository.findByJuryNotificationsStepSix(stepSix.orElse(null));
        processGenericStep(progressList, stepSeven, "7");

// Step 8: Pasting Approval
        Optional<PastingApprovalStepEight> stepEight = stepEightRepository.findByThesisApprovalStepSeven(stepSeven.orElse(null));
        processGenericStep(progressList, stepEight, "8");


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

    private void processGenericStep(List<StudentProgress> progressList, Optional<?> step, String stepNumber) {
        double stepCompletion = 0.0;
        boolean stepCompleted = false;

        if (step.isPresent()) {
            Object stepData = step.get();

            if (stepData instanceof ProjectApprovalStepTwo) {
                stepCompleted = ((ProjectApprovalStepTwo) stepData).isApprovedProject();
                stepCompletion = stepCompleted ? 100.0 :
                        (((ProjectApprovalStepTwo) stepData).getObservations() != null && !((ProjectApprovalStepTwo) stepData).getObservations().isEmpty()) ? 50.0 : 10.0;
            } else if (stepData instanceof JuryAppointmentStepThree) {
                stepCompleted = ((JuryAppointmentStepThree) stepData).isMeetRequirements();
                stepCompletion = stepCompleted ? 100.0 :
                        (((JuryAppointmentStepThree) stepData).getObservations() != null && !((JuryAppointmentStepThree) stepData).getObservations().isEmpty()) ? 50.0 : 10.0;
            } else if (stepData instanceof ReportReviewStepFour) {
                stepCompleted = ((ReportReviewStepFour) stepData).isMeetRequirements();
                stepCompletion = stepCompleted ? 100.0 :
                        (((ReportReviewStepFour) stepData).getObservations() != null && !((ReportReviewStepFour) stepData).getObservations().isEmpty()) ? 50.0 : 10.0;
            } else if (stepData instanceof JuryNotificationsStepSix) {
                stepCompleted = ((JuryNotificationsStepSix) stepData).isMeetRequirements();
                stepCompletion = stepCompleted ? 100.0 :
                        (((JuryNotificationsStepSix) stepData).getObservations() != null && !((JuryNotificationsStepSix) stepData).getObservations().isEmpty()) ? 50.0 : 10.0;
            } else if (stepData instanceof ThesisApprovalStepSeven) {
                stepCompleted = ((ThesisApprovalStepSeven) stepData).isMeetRequirements();
                stepCompletion = stepCompleted ? 100.0 :
                        (((ThesisApprovalStepSeven) stepData).getObservations() != null && !((ThesisApprovalStepSeven) stepData).getObservations().isEmpty()) ? 50.0 : 10.0;
            } else if (stepData instanceof PastingApprovalStepEight) {
                stepCompleted = ((PastingApprovalStepEight) stepData).isMeetRequirements();
                stepCompletion = stepCompleted ? 100.0 :
                        (((PastingApprovalStepEight) stepData).getObservations() != null && !((PastingApprovalStepEight) stepData).getObservations().isEmpty()) ? 50.0 : 10.0;
            }
        }

        progressList.add(new StudentProgress(
                stepNumber,
                stepCompleted,
                stepCompletion,
                step.orElse(null)
        ));
    }

    private void processConstancyThesisStep(List<StudentProgress> progressList, Optional<ConstancyThesisStepFive> stepFive) {
        double stepFiveCompletion = 0.0;
        boolean stepFiveCompleted = false;

        if (stepFive.isPresent()) {
            ConstancyThesisStepFive stepFiveData = stepFive.get();
            stepFiveCompleted = stepFiveData.isMeetsRequirements();
            if (stepFiveData.isMeetsRequirements()) {
                stepFiveCompletion = 100.0;
            } else if (stepFiveData.getPdfDocument() != null) {
                stepFiveCompletion = 75.0;
            } else {
                stepFiveCompletion = 30.0;
            }
        }

        progressList.add(new StudentProgress(
                "5",
                stepFiveCompleted,
                stepFiveCompletion,
                stepFive.orElse(null)
        ));
    }
}
