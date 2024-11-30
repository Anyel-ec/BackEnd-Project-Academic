package pe.edu.unamba.academic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.HasUpdatedAt;
import pe.edu.unamba.academic.models.StudentProgress;
import pe.edu.unamba.academic.models.actors.Student;
import pe.edu.unamba.academic.models.steps.*;
import pe.edu.unamba.academic.repositories.actors.StudentRepository;
import pe.edu.unamba.academic.repositories.steps.*;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentProgressService {

    private final StudentRepository studentRepository;
    private final TitleReservationStepOneRepository stepOneRepository;
    private final ProjectApprovalStepTwoRepository stepTwoRepository;
    private final JuryAppointmentStepThreeRepository stepThreeRepository;
    private final ReportReviewStepFourRepository stepFourRepository;
    private final ConstancyThesisStepFiveRepository stepFiveRepository;
    private final JuryNotificationsStepSixRepository stepSixRepository;
    private final ThesisApprovalStepSevenRepository stepSevenRepository;
    private final PastingApprovalStepEightRepository stepEightRepository;

    // Obtener el progreso de un estudiante específico
    public List<StudentProgress> getProgressByStudent(String studentCode) {
        List<StudentProgress> progressList = new ArrayList<>();

        // Step 1: Title Reservation
        Optional<TitleReservationStepOne> stepOne = stepOneRepository.findByAnyStudentCodeNative(studentCode);
        processTitleReservationStep(progressList, stepOne);

        if (stepOne.isPresent()) {
            Long stepOneId = stepOne.get().getId();

            // Step 2: Project Approval
            Optional<ProjectApprovalStepTwo> stepTwo = stepTwoRepository.findByTitleReservationStepOne(stepOneId);
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
        }

        return progressList;
    }

    // Obtener el progreso de todos los estudiantes
    public List<Map<String, Object>> getAllProgressGroupedByStudent() {
        List<Student> allStudents = studentRepository.findAll(); // Obtén todos los estudiantes
        List<Map<String, Object>> groupedProgress = new ArrayList<>();

        for (Student student : allStudents) {
            Map<String, Object> studentProgress = new HashMap<>();
            studentProgress.put("studentCode", student.getStudentCode());
            studentProgress.put("studentName", student.getFullName());
            studentProgress.put("progress", getProgressByStudent(student.getStudentCode())); // Usa el método existente

            groupedProgress.add(studentProgress);
        }

        return groupedProgress;
    }
    public List<Map<String, Object>> getSimplifiedProgressByStudent() {
        List<Student> allStudents = studentRepository.findAll(); // Obtén todos los estudiantes
        List<Map<String, Object>> groupedProgress = new ArrayList<>();

        for (Student student : allStudents) {
            Map<String, Object> studentProgress = new HashMap<>();
            studentProgress.put("studentCode", student.getStudentCode());
            studentProgress.put("studentName", student.getFullName());

            List<Map<String, Object>> simplifiedProgress = getProgressByStudent(student.getStudentCode())
                    .stream()
                    .map(step -> {
                        Map<String, Object> stepData = new HashMap<>();
                        stepData.put("stepNumber", step.getStepNumber());
                        stepData.put("completed", step.isCompleted());
                        stepData.put("completionPercentage", step.getCompletionPercentage()); // Agrega el porcentaje de progreso

                        HasUpdatedAt stepObject = step.getStepObject();
                        if (stepObject != null) {
                            stepData.put("updatedAt", stepObject.getUpdatedAt()); // Agrega la fecha actualizada
                        } else {
                            stepData.put("updatedAt", null);
                        }

                        return stepData;
                    })
                    .collect(Collectors.toList());

            studentProgress.put("progress", simplifiedProgress);
            groupedProgress.add(studentProgress);
        }

        return groupedProgress;
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
                stepCompleted = ((ProjectApprovalStepTwo) stepData).getMeetRequirements();
            } else if (stepData instanceof JuryAppointmentStepThree) {
                stepCompleted = ((JuryAppointmentStepThree) stepData).isMeetRequirements();
            } else if (stepData instanceof ReportReviewStepFour) {
                stepCompleted = ((ReportReviewStepFour) stepData).isMeetRequirements();
            } else if (stepData instanceof JuryNotificationsStepSix) {
                stepCompleted = ((JuryNotificationsStepSix) stepData).isMeetRequirements();
            } else if (stepData instanceof ThesisApprovalStepSeven) {
                stepCompleted = ((ThesisApprovalStepSeven) stepData).isMeetRequirements();
            } else if (stepData instanceof PastingApprovalStepEight) {
                stepCompleted = ((PastingApprovalStepEight) stepData).isMeetRequirements();
            }

            stepCompletion = stepCompleted ? 100.0 : 50.0;
        }

        progressList.add(new StudentProgress(
                stepNumber,
                stepCompleted,
                stepCompletion,
                (HasUpdatedAt) step.orElse(null)
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
                (HasUpdatedAt) stepFive.orElse(null)
        ));
    }
}
