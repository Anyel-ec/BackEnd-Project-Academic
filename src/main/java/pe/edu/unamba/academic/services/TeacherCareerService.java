package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.TeacherCareer;
import pe.edu.unamba.academic.repositories.TeacherCareerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherCareerService {
    @Autowired
    private TeacherCareerRepository teacherCareerRepository;

    public List<TeacherCareer> getAllTeacherCareers() {
        return teacherCareerRepository.findAll();
    }

    public Optional<TeacherCareer> getTeacherCareerById(Long id) {
        return teacherCareerRepository.findById(id);
    }

    public TeacherCareer saveTeacherCareer(TeacherCareer teacherCareer) {
        return teacherCareerRepository.save(teacherCareer);
    }

    public void deleteTeacherCareer(Long id) {
        teacherCareerRepository.deleteById(id);
    }

    public TeacherCareer updateTeacherCareer(Long id, TeacherCareer teacherCareerDetails) {
        TeacherCareer teacherCareer = teacherCareerRepository.findById(id).get();
        teacherCareer.setTeacher(teacherCareerDetails.getTeacher());
        teacherCareer.setCareer(teacherCareerDetails.getCareer());
        return teacherCareerRepository.save(teacherCareer);
    }
}