package pe.edu.unamba.academic.services.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.actors.Teacher;
import pe.edu.unamba.academic.repositories.actors.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id).get();
        teacher.setDni(teacherDetails.getDni());
        teacher.setFirstNames(teacherDetails.getFirstNames());
        teacher.setLastName(teacherDetails.getLastName());
        teacher.setMiddleName(teacherDetails.getMiddleName());
        teacher.setBirthDate(teacherDetails.getBirthDate());
        teacher.setInstitutionalEmail(teacherDetails.getInstitutionalEmail());
        teacher.setPhone(teacherDetails.getPhone());
        teacher.setAddress(teacherDetails.getAddress());
        return teacherRepository.save(teacher);
    }
}