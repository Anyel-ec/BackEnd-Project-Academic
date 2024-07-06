package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.Career;
import pe.edu.unamba.academic.repositories.CareerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CareerService {
    @Autowired
    private CareerRepository careerRepository;

    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    public Optional<Career> getCareerById(Long id) {
        return careerRepository.findById(id);
    }

    public Career saveCareer(Career career) {
        return careerRepository.save(career);
    }

    public void deleteCareer(Long id) {
        careerRepository.deleteById(id);
    }

    public Career updateCareer(Long id, Career careerDetails) {
        Career career = careerRepository.findById(id).get();
        career.setName(careerDetails.getName());
        career.setFaculty(careerDetails.getFaculty());
        return careerRepository.save(career);
    }
}