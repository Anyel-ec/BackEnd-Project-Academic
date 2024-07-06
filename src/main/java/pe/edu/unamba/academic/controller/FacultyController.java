package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.Faculty;
import pe.edu.unamba.academic.services.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facultades")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/")
    public List<Faculty> getAllFacultades() {
        return facultyService.getAllFacultades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultadById(@PathVariable Long id) {
        return facultyService.getFacultadById(id)
                .map(facultad -> ResponseEntity.ok().body(facultad))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Faculty> saveFacultad(@RequestBody Faculty facultad) {
        try {
            return ResponseEntity.ok().body(facultyService.saveFacultad(facultad));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFacultad(@PathVariable Long id, @RequestBody Faculty facultadDetails) {
        try {
            return ResponseEntity.ok().body(facultyService.updateFacultad(id, facultadDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacultad(@PathVariable Long id) {
        try {
            facultyService.deleteFacultad(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}