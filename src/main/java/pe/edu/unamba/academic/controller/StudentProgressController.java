package pe.edu.unamba.academic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.StudentProgress;
import pe.edu.unamba.academic.services.StudentProgressService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/progreso_estudiante")
public class StudentProgressController {

    private final StudentProgressService studentProgressService;

    @GetMapping("/{studentCode}")
    public ResponseEntity<List<StudentProgress>> getProgressByStudent(@PathVariable String studentCode) {
        List<StudentProgress> progress = studentProgressService.getProgressByStudent(studentCode);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Map<String, Object>>> getAllProgressGroupedByStudent() {
        List<Map<String, Object>> progress = studentProgressService.getAllProgressGroupedByStudent();
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/todos/simplificado")
    public ResponseEntity<List<Map<String, Object>>> getSimplifiedProgressGroupedByStudent() {
        List<Map<String, Object>> simplifiedProgress = studentProgressService.getSimplifiedProgressByStudent();
        return ResponseEntity.ok(simplifiedProgress);
    }

}

