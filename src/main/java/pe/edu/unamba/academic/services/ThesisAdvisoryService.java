package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.ThesisAdvisory;
import pe.edu.unamba.academic.repositories.ThesisAdvisoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ThesisAdvisoryService {

    @Autowired
    private ThesisAdvisoryRepository thesisAdvisoryRepository;

    public List<ThesisAdvisory> getAllAdvisories() {
        return thesisAdvisoryRepository.findAll();
    }

    public Optional<ThesisAdvisory> getAdvisoryById(Long id) {
        return thesisAdvisoryRepository.findById(id);
    }

    public ThesisAdvisory saveAdvisory(ThesisAdvisory advisory) {
        return thesisAdvisoryRepository.save(advisory);
    }

    public void deleteAdvisory(Long id) {
        thesisAdvisoryRepository.deleteById(id);
    }
}
