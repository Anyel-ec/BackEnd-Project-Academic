package pe.edu.unamba.academic.services.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.actors.LineResearch;
import pe.edu.unamba.academic.repositories.actors.LineResearchRepository;

import java.util.List;

@Service
public class LineResearchService {

    private final LineResearchRepository lineResearchRepository;

    @Autowired
    public LineResearchService(LineResearchRepository lineResearchRepository) {
        this.lineResearchRepository = lineResearchRepository;
    }

    // Método para obtener líneas de investigación por carrera
    public List<LineResearch> getResearchLinesByCareer(Long careerId) {
        return lineResearchRepository.findByCareerId(careerId);
    }
}
