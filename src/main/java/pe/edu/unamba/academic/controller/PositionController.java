package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.Position;
import pe.edu.unamba.academic.services.PositionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cargos")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("/")
    public List<Position> getAllCargos() {
        return positionService.getAllCargos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getCargoById(@PathVariable Long id) {
        return positionService.getPositionById(id)
                .map(cargo -> ResponseEntity.ok().body(cargo))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Position> saveCargo(@RequestBody Position cargo) {
        try {
            return ResponseEntity.ok().body(positionService.savePosition(cargo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Position> updateCargo(@PathVariable Long id, @RequestBody Position cargoDetails) {
        try {
            return ResponseEntity.ok().body(positionService.updatePosition(id, cargoDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        try {
            positionService.deleteCPosition(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}