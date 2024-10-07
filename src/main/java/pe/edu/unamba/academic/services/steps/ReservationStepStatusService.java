package pe.edu.unamba.academic.services.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ReservationStepStatus;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
import pe.edu.unamba.academic.repositories.steps.ReservationStepStatusRepository;
import pe.edu.unamba.academic.repositories.steps.TitleReservationStepOneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationStepStatusService {

    @Autowired
    private ReservationStepStatusRepository reservationStepStatusRepository;

    @Autowired
    private TitleReservationStepOneRepository titleReservationStepOneRepository;

    private static final int TOTAL_STEPS = 5; // Número total de pasos que existen

    public List<ReservationStepStatus> getAllStepStatusByStudent(String studentCode) {
        // Obtener los pasos existentes del repositorio
        List<ReservationStepStatus> stepStatusList = reservationStepStatusRepository.findByStudentCode(studentCode);

        // Crear una lista para todos los pasos, incluidos aquellos que no tienen progreso
        List<ReservationStepStatus> allSteps = new ArrayList<>();

        for (int stepNumber = 1; stepNumber <= TOTAL_STEPS; stepNumber++) {
            int finalStepNumber = stepNumber;
            Optional<ReservationStepStatus> existingStep = stepStatusList.stream()
                    .filter(step -> step.getStepNumber() == finalStepNumber)
                    .findFirst();

            // Si el paso uno, necesitamos calcular el porcentaje de progreso basado en la reservación
            if (stepNumber == 1) {
                ReservationStepStatus stepOne = existingStep.orElse(new ReservationStepStatus());
                stepOne.setStudentCode(studentCode);
                stepOne.setStepNumber(1);

                // Buscar la reservación de título del estudiante
                Optional<TitleReservationStepOne> reservationOpt = titleReservationStepOneRepository.findByStudent_StudentCode(studentCode);

                if (reservationOpt.isPresent()) {
                    TitleReservationStepOne reservation = reservationOpt.get();
                    // Si el estudiante está en la reservación, el progreso es 50%
                    int percentage = 50;

                    // Si el estudiante cumple los requisitos, el progreso es 100%
                    if (reservation.isMeetsRequirements()) {
                        percentage = 100;
                    }

                    stepOne.setPercentage(percentage);
                    stepOne.setIsCompleted(percentage == 100);
                } else {
                    // Si no tiene reservación, el progreso es 0%
                    stepOne.setPercentage(0);
                    stepOne.setIsCompleted(false);
                }

                allSteps.add(stepOne);
            } else {
                // Para otros pasos, agregar la lógica existente
                if (existingStep.isPresent()) {
                    ReservationStepStatus step = existingStep.get();
                    step.setPercentage(Boolean.TRUE.equals(step.getIsCompleted()) ? 100 : 0);
                    allSteps.add(step);
                } else {
                    // Crear un nuevo paso con 0% si no existe
                    ReservationStepStatus emptyStep = new ReservationStepStatus();
                    emptyStep.setStudentCode(studentCode);
                    emptyStep.setStepNumber(stepNumber);
                    emptyStep.setIsCompleted(false); // No está completado
                    emptyStep.setPercentage(0); // Progreso en 0%
                    allSteps.add(emptyStep);
                }
            }
        }

        return allSteps;
    }

    public ReservationStepStatus saveOrUpdateStepStatus(String studentCode, Integer stepNumber, Boolean isCompleted) {
        // Buscar si ya existe un registro para el estudiante y el número de paso
        Optional<ReservationStepStatus> existingStatus = reservationStepStatusRepository
                .findByStudentCodeAndStepNumber(studentCode, stepNumber);

        // Si ya existe, actualizar el estado. Si no, crear uno nuevo.
        ReservationStepStatus status;
        if (existingStatus.isPresent()) {
            status = existingStatus.get(); // Obtener el estado existente para actualizarlo
        } else {
            status = new ReservationStepStatus(); // Crear uno nuevo si no existe
            status.setStudentCode(studentCode);
            status.setStepNumber(stepNumber);
        }

        // Establecer si el paso está completado o no
        status.setIsCompleted(isCompleted);

        // Guardar el estado en la base de datos
        return reservationStepStatusRepository.save(status);
    }


    public Optional<ReservationStepStatus> getStepStatus(String studentCode, Integer stepNumber) {
        // Buscar si ya existe un registro para el estudiante y el número de paso
        return reservationStepStatusRepository.findByStudentCodeAndStepNumber(studentCode, stepNumber);
    }

}


