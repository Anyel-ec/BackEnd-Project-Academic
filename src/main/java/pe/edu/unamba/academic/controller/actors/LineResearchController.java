package pe.edu.unamba.academic.controller.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.actors.LineResearch;
import pe.edu.unamba.academic.services.actors.LineResearchService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lineas-investigacion")
@CrossOrigin(origins = "*")
public class LineResearchController {

    private final LineResearchService lineResearchService;

    @Autowired
    public LineResearchController(LineResearchService lineResearchService) {
        this.lineResearchService = lineResearchService;
    }

    // Endpoint para obtener líneas de investigación filtradas por carrera
    @GetMapping
    public ResponseEntity<List<LineResearch>> getResearchLinesByCareer(@RequestParam Long careerId) {
        List<LineResearch> researchLines = lineResearchService.getResearchLinesByCareer(careerId);
        return ResponseEntity.ok(researchLines);
    }
}
