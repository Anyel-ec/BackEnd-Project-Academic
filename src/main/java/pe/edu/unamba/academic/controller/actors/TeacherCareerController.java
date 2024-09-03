package pe.edu.unamba.academic.controller.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.actors.TeacherCareer;
import pe.edu.unamba.academic.services.actors.TeacherCareerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/docente_carrera")
public class TeacherCareerController {

    @Autowired
    private TeacherCareerService teacherCareerService;

    @GetMapping("/")
    public List<TeacherCareer> getAllTeacherCareers() {
        return teacherCareerService.getAllTeacherCareers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherCareer> getTeacherCareerById(@PathVariable Long id) {
        return teacherCareerService.getTeacherCareerById(id)
                .map(teacherCareer -> ResponseEntity.ok().body(teacherCareer))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<TeacherCareer> saveTeacherCareer(@RequestBody TeacherCareer teacherCareer) {
        try {
            return ResponseEntity.ok().body(teacherCareerService.saveTeacherCareer(teacherCareer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherCareer> updateTeacherCareer(@PathVariable Long id, @RequestBody TeacherCareer teacherCareerDetails) {
        try {
            return ResponseEntity.ok().body(teacherCareerService.updateTeacherCareer(id, teacherCareerDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacherCareer(@PathVariable Long id) {
        try {
            teacherCareerService.deleteTeacherCareer(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}