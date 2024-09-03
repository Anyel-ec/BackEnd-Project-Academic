package pe.edu.unamba.academic.controller.actors;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.actors.Teacher;
import pe.edu.unamba.academic.services.actors.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/docentes")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id)
                .map(teacher -> ResponseEntity.ok().body(teacher))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Teacher> saveTeacher(@Valid @RequestBody Teacher teacher) {
        try {
            return ResponseEntity.ok().body(teacherService.saveTeacher(teacher));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher( @Valid @PathVariable Long id, @RequestBody Teacher teacherDetails) {
        try {
            return ResponseEntity.ok().body(teacherService.updateTeacher(id, teacherDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
