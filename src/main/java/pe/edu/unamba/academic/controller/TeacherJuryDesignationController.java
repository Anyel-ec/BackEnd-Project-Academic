package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.TeacherJuryDesignation;
import pe.edu.unamba.academic.services.TeacherJuryDesignationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/docente_jurado")
public class TeacherJuryDesignationController {

    @Autowired
    private TeacherJuryDesignationService teacherJuryDesignationService;

    @GetMapping("/")
    public List<TeacherJuryDesignation> getAllTeacherJuryDesignations() {
        return teacherJuryDesignationService.getAllTeacherJuryDesignations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherJuryDesignation> getTeacherJuryDesignationById(@PathVariable Long id) {
        return teacherJuryDesignationService.getTeacherJuryDesignationById(id)
                .map(teacherJuryDesignation -> ResponseEntity.ok().body(teacherJuryDesignation))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<TeacherJuryDesignation> saveTeacherJuryDesignation(@RequestBody TeacherJuryDesignation teacherJuryDesignation) {
        try {
            return ResponseEntity.ok().body(teacherJuryDesignationService.saveTeacherJuryDesignation(teacherJuryDesignation));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacherJuryDesignation(@PathVariable Long id) {
        try {
            teacherJuryDesignationService.deleteTeacherJuryDesignation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
