package pe.edu.unamba.academic.controller.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.actors.Career;
import pe.edu.unamba.academic.services.actors.CareerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carreras")
public class CareerController {

    @Autowired
    private CareerService careerService;

    @GetMapping("/")
    public List<Career> getAllCareers() {
        return careerService.getAllCareers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Career> getCareerById(@PathVariable Long id) {
        return careerService.getCareerById(id)
                .map(career -> ResponseEntity.ok().body(career))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Career> saveCareer(@RequestBody Career career) {
        try {
            return ResponseEntity.ok().body(careerService.saveCareer(career));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Career> updateCareer(@PathVariable Long id, @RequestBody Career careerDetails) {
        try {
            return ResponseEntity.ok().body(careerService.updateCareer(id, careerDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        try {
            careerService.deleteCareer(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}