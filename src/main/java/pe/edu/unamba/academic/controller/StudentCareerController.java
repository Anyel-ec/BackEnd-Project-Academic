package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.StudentCareer;
import pe.edu.unamba.academic.services.StudentCareerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alumno_carrera")
public class StudentCareerController {

    @Autowired
    private StudentCareerService studentCareerService;

    @GetMapping("/")
    public List<StudentCareer> getAllStudentCareers() {
        return studentCareerService.getAllStudentCareers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentCareer> getStudentCareerById(@PathVariable Long id) {
        return studentCareerService.getStudentCareerById(id)
                .map(studentCareer -> ResponseEntity.ok().body(studentCareer))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<StudentCareer> saveStudentCareer(@RequestBody StudentCareer studentCareer) {
        try {
            return ResponseEntity.ok().body(studentCareerService.saveStudentCareer(studentCareer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentCareer> updateStudentCareer(@PathVariable Long id, @RequestBody StudentCareer studentCareerDetails) {
        try {
            return ResponseEntity.ok().body(studentCareerService.updateStudentCareer(id, studentCareerDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentCareer(@PathVariable Long id) {
        try {
            studentCareerService.deleteStudentCareer(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
