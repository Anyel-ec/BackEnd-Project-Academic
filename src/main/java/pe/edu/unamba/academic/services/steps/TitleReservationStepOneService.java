package pe.edu.unamba.academic.services.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
import pe.edu.unamba.academic.repositories.steps.TitleReservationStepOneRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TitleReservationStepOneService {
    @Autowired
    private TitleReservationStepOneRepository titleReservationStepOneRepository;

    public List<TitleReservationStepOne> getAllTitleReservations() {
        return titleReservationStepOneRepository.findAll();
    }

    public Optional<TitleReservationStepOne> getTitleReservationById(Long id) {
        return titleReservationStepOneRepository.findById(id);
    }

    public TitleReservationStepOne saveTitleReservation(TitleReservationStepOne titleReservation) {
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
