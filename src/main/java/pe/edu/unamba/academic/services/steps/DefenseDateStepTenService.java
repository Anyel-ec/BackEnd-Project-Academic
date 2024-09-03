package pe.edu.unamba.academic.services.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.DefenseDateStepTen;
import pe.edu.unamba.academic.repositories.steps.DefenseDateStepTenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DefenseDateStepTenService {
    @Autowired
    private DefenseDateStepTenRepository defenseDateStepTenRepository;

    public List<DefenseDateStepTen> getAllDates() {
        return defenseDateStepTenRepository.findAll();
    }

    public Optional<DefenseDateStepTen> getDateById(Long id) {
        return defenseDateStepTenRepository.findById(id);
    }

    public DefenseDateStepTen saveDate(DefenseDateStepTen date) {
        return defenseDateStepTenRepository.save(date);
    }

    public void deleteDate(Long id) {
        defenseDateStepTenRepository.deleteById(id);
    }

    public DefenseDateStepTen updateDate(Long id, DefenseDateStepTen dateDetails) {
        DefenseDateStepTen date = defenseDateStepTenRepository.findById(id).get();
        date.setDate(dateDetails.getDate());
        date.setObservations(dateDetails.getObservations());
        return defenseDateStepTenRepository.save(date);
    }
}
