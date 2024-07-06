package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.Student;
import pe.edu.unamba.academic.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id).get();
        student.setStudentCode(studentDetails.getStudentCode());
        student.setDni(studentDetails.getDni());
        student.setFirstNames(studentDetails.getFirstNames());
        student.setLastName(studentDetails.getLastName());
        student.setMiddleName(studentDetails.getMiddleName());
        student.setBirthDate(studentDetails.getBirthDate());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());
        student.setAddress(studentDetails.getAddress());
        return studentRepository.save(student);
    }
}
