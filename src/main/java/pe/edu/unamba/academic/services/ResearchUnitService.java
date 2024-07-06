package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.ResearchUnit;
import pe.edu.unamba.academic.repositories.ResearchUnitRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ResearchUnitService {
    @Autowired
    private ResearchUnitRepository researchUnitRepository;

    public List<ResearchUnit> getAllResearchUnits() {
        return researchUnitRepository.findAll();
    }

    public Optional<ResearchUnit> getResearchUnitById(Long id) {
        return researchUnitRepository.findById(id);
    }

    public ResearchUnit saveResearchUnit(ResearchUnit researchUnit) {
        return researchUnitRepository.save(researchUnit);
    }

    public void deleteResearchUnit(Long id) {
        researchUnitRepository.deleteById(id);
    }

    public ResearchUnit updateResearchUnit(Long id, ResearchUnit researchUnitDetails) {
        ResearchUnit researchUnit = researchUnitRepository.findById(id).get();
        researchUnit.setName(researchUnitDetails.getName());
        researchUnit.setFaculty(researchUnitDetails.getFaculty());
        return researchUnitRepository.save(researchUnit);
    }
}
