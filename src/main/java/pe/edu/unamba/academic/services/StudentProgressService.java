package pe.edu.unamba.academic.services;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.StudentProgress;
import pe.edu.unamba.academic.models.steps.*;
import pe.edu.unamba.academic.repositories.*;
import pe.edu.unamba.academic.repositories.actors.StudentRepository;
import pe.edu.unamba.academic.repositories.steps.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class StudentProgressService {

    private static final Logger log = (Logger) LoggerFactory.getLogger(StudentProgressService.class);
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
        log.info("Obteniendo el progreso del estudiante con código: {}", studentCode);
        List<StudentProgress> progressList = new ArrayList<>();

        // Paso 1: Reservación de Título
        Optional<TitleReservationStepOne> stepOne = stepOneRepository.findByAnyStudentCodeNative(studentCode);
        processTitleReservationStep(progressList, stepOne);

        // Paso 2: Aprobación de Proyecto
        Optional<ProjectApprovalStepTwo> stepTwo = stepTwoRepository.findByAnyStudentCodeNative(studentCode);
        processGenericStep(progressList, stepTwo, "2");

        // Paso 3: Citación del Jurado
        Optional<JuryAppointmentStepThree> stepThree = stepThreeRepository.findByProjectApprovalStepTwo(stepTwo.orElse(null));
        processGenericStep(progressList, stepThree, "3");

        // Paso 4: Revisión del Informe
        Optional<ReportReviewStepFour> stepFour = stepFourRepository.findByJuryAppointmentStepThree(stepThree.orElse(null));
        processGenericStep(progressList, stepFour, "4");

        // Paso 5: Constancia de Tesis
        Optional<ConstancyThesisStepFive> stepFive = stepFiveRepository.findByReportReviewStepFour(stepFour.orElse(null));
        processConstancyThesisStep(progressList, stepFive);

        // Paso 6: Notificaciones del Jurado
        Optional<JuryNotificationsStepSix> stepSix = stepSixRepository.findByConstancyThesisStepFive(stepFive.orElse(null));
        processGenericStep(progressList, stepSix, "6");

        // Paso 7: Aprobación de Tesis
        Optional<ThesisApprovalStepSeven> stepSeven = stepSevenRepository.findByJuryNotificationsStepSix(stepSix.orElse(null));
        processGenericStep(progressList, stepSeven, "7");

        // Paso 8: Aprobación de Pegado
        Optional<PastingApprovalStepEight> stepEight = stepEightRepository.findByThesisApprovalStepSeven(stepSeven.orElse(null));
        processGenericStep(progressList, stepEight, "8");

        return progressList;
    }

    public List<StudentProgress> getProgressForAllStudents() {
        log.info("Obteniendo el progreso de todos los estudiantes");
        List<StudentProgress> allProgress = new ArrayList<>();

        List<String> studentCodesWithProgress = stepOneRepository.findStudentCodesWithProgress();
        for (String studentCode : studentCodesWithProgress) {
            List<StudentProgress> studentProgress = getProgressByStudent(studentCode);
            List<StudentProgress> cleanedProgress = cleanStudentProgress(studentProgress);
            allProgress.addAll(cleanedProgress);
        }

        return allProgress;
    }

    private List<StudentProgress> cleanStudentProgress(List<StudentProgress> studentProgress) {
        List<StudentProgress> cleanedProgress = new ArrayList<>();
        log.info("Limpiando los datos de progreso de los estudiantes");

        for (StudentProgress progress : studentProgress) {
            log.info("Procesando progreso: {}", progress);

            Map<String, Object> stepObject = new HashMap<>();
            if (progress.getStepObject() != null) {
                Object step = progress.getStepObject();
                if (step instanceof TitleReservationStepOne) {
                    TitleReservationStepOne stepData = (TitleReservationStepOne) step;
                    Map<String, Object> studentMap = stepData.getStudent() != null
                            ? Map.of(
                            "studentCode", Optional.ofNullable(stepData.getStudent().getStudentCode()).orElse("N/A"),
                            "fullName", Optional.ofNullable(stepData.getStudent().getFullName()).orElse("N/A")
                    )
                            : Map.of("studentCode", "N/A", "fullName", "N/A");

                    Map<String, Object> studentTwoMap = stepData.getStudentTwo() != null
                            ? Map.of(
                            "studentCode", Optional.ofNullable(stepData.getStudentTwo().getStudentCode()).orElse("N/A"),
                            "fullName", Optional.ofNullable(stepData.getStudentTwo().getFullName()).orElse("N/A")
                    )
                            : null;

                    stepObject.put("id", Optional.ofNullable(stepData.getId()).orElse((long) -1));
                    stepObject.put("student", studentMap);
                    stepObject.put("studentTwo", studentTwoMap);
                    stepObject.put("updatedAt", stepData.getUpdatedAt() != null ? stepData.getUpdatedAt() : null);
                }
                // Más lógica para otros pasos si es necesario
            }

            log.info("stepObject después de la transformación: {}", stepObject);
            cleanedProgress.add(new StudentProgress(
                    progress.getStepNumber(),
                    progress.isCompleted(),
                    progress.getCompletionPercentage(),
                    stepObject
            ));
        }

        log.info("Progreso limpio final: {}", cleanedProgress);
        return cleanedProgress;
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

            // Ajuste para los pasos específicos
            if (stepData instanceof ProjectApprovalStepTwo ||
                    stepData instanceof JuryAppointmentStepThree ||
                    stepData instanceof ReportReviewStepFour ||
                    stepData instanceof JuryNotificationsStepSix ||
                    stepData instanceof ThesisApprovalStepSeven ||
                    stepData instanceof PastingApprovalStepEight) {

                // Determinar si el paso cumple con los requisitos
                if (stepData instanceof ProjectApprovalStepTwo) {
                    log.info("Paso {} encontrado. Fecha de actualización: {}", stepNumber, ((ProjectApprovalStepTwo) stepData).getUpdatedAt());

                    stepCompleted = ((ProjectApprovalStepTwo) stepData).getMeetRequirements();
                } else if (stepData instanceof JuryAppointmentStepThree) {
                    stepCompleted = ((JuryAppointmentStepThree) stepData).isMeetRequirements();
                } else if (stepData instanceof ReportReviewStepFour) {
                    stepCompleted = ((ReportReviewStepFour) stepData).isMeetRequirements();
                } else if (stepData instanceof JuryNotificationsStepSix) {
                    stepCompleted = ((JuryNotificationsStepSix) stepData).isMeetRequirements();
                } else if (stepData instanceof ThesisApprovalStepSeven) {
                    stepCompleted = ((ThesisApprovalStepSeven) stepData).isMeetRequirements();
                } else {
                    stepCompleted = ((PastingApprovalStepEight) stepData).isMeetRequirements();
                }

                // Aplicar la lógica específica
                stepCompletion = stepCompleted ? 100.0 : 50.0;
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