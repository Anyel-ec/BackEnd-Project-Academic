package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.ResearchUnit;
import pe.edu.unamba.academic.services.ResearchUnitService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/unidad_investigacion")
public class ResearchUnitController {

    @Autowired
    private ResearchUnitService researchUnitService;

    @GetMapping("/")
    public List<ResearchUnit> getAllResearchUnits() {
        return researchUnitService.getAllResearchUnits();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResearchUnit> getResearchUnitById(@PathVariable Long id) {
        return researchUnitService.getResearchUnitById(id)
                .map(researchUnit -> ResponseEntity.ok().body(researchUnit))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<ResearchUnit> saveResearchUnit(@RequestBody ResearchUnit researchUnit)
    {
        try {
            return ResponseEntity.ok().body(researchUnitService.saveResearchUnit(researchUnit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResearchUnit> updateResearchUnit(@PathVariable Long id, @RequestBody ResearchUnit researchUnitDetails) {
        try {
            return ResponseEntity.ok().body(researchUnitService.updateResearchUnit(id, researchUnitDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResearchUnit(@PathVariable Long id) {
        try {
            researchUnitService.deleteResearchUnit(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

