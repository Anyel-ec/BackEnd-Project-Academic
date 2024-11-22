package pe.edu.unamba.academic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.StudentProgress;
import pe.edu.unamba.academic.services.StudentProgressService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/progreso_estudiante")
public class StudentProgressController {

    private final StudentProgressService studentProgressService;

    @GetMapping("/{studentCode}")
    public ResponseEntity<List<StudentProgress>> getStudentProgress(@PathVariable String studentCode) {
        List<StudentProgress> progress = studentProgressService.getProgressByStudent(studentCode);
        if (progress.isEmpty()) {
            return ResponseEntity.noContent().build();  // Or ResponseEntity.notFound().build() depending on business logic
        }
        return ResponseEntity.ok(progress);
    }
}

