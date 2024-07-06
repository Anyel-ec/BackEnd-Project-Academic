package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.StudentCareer;
import pe.edu.unamba.academic.repositories.StudentCareerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCareerService {
    @Autowired
    private StudentCareerRepository studentCareerRepository;

    public List<StudentCareer> getAllStudentCareers() {
        return studentCareerRepository.findAll();
    }

    public Optional<StudentCareer> getStudentCareerById(Long id) {
        return studentCareerRepository.findById(id);
    }

    public StudentCareer saveStudentCareer(StudentCareer studentCareer) {
        return studentCareerRepository.save(studentCareer);
    }

    public void deleteStudentCareer(Long id) {
        studentCareerRepository.deleteById(id);
    }

    public StudentCareer updateStudentCareer(Long id, StudentCareer studentCareerDetails) {
        StudentCareer studentCareer = studentCareerRepository.findById(id).get();
        studentCareer.setStudent(studentCareerDetails.getStudent());
        studentCareer.setCareer(studentCareerDetails.getCareer());
        return studentCareerRepository.save(studentCareer);
    }
}
