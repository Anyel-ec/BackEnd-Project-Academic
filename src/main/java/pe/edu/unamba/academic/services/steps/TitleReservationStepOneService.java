package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.actors.Student;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
import pe.edu.unamba.academic.repositories.actors.StudentRepository;
import pe.edu.unamba.academic.repositories.steps.TitleReservationStepOneRepository;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class TitleReservationStepOneService {

    private final TitleReservationStepOneRepository titleReservationStepOneRepository;
    private final StudentRepository studentRepository;

    private static final Logger LOG = LoggerFactory.getLogger(TitleReservationStepOneService.class);


    public List<TitleReservationStepOne> getAllTitleReservations() {
        return titleReservationStepOneRepository.findAll();
    }

    public Optional<TitleReservationStepOne> getTitleReservationById(Long id) {
        return titleReservationStepOneRepository.findById(id);
    }

    public TitleReservationStepOne saveTitleReservation(TitleReservationStepOne titleReservation) {
        try {
            if (titleReservation.getStudent() != null && titleReservation.getStudent().getId() != null) {
                // Buscar el estudiante en la base de datos
                Optional<Student> existingStudentOpt = studentRepository.findById(titleReservation.getStudent().getId());
                if (existingStudentOpt.isPresent()) {
                    // Si el estudiante existe, usarlo
                    titleReservation.setStudent(existingStudentOpt.get());
                } else {
                    LOG.error("NO HAY ESTUDIANTES ");
                    return null;
                }
            }
        }catch (Exception e){
            LOG.error("El error es {}", e.getMessage());
        }
        return titleReservationStepOneRepository.save(titleReservation);
    }

    public void deleteTitleReservation(Long id) {
        titleReservationStepOneRepository.deleteById(id);
    }

    public TitleReservationStepOne updateTitleReservation(Long id, TitleReservationStepOne titleReservationDetails) {
        TitleReservationStepOne titleReservation = titleReservationStepOneRepository.findById(id).get();
        titleReservation.setMeetsRequirements(titleReservationDetails.isMeetsRequirements());
        titleReservation.setObservations(titleReservationDetails.getObservations());
        return titleReservationStepOneRepository.save(titleReservation);
    }
}
