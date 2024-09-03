package pe.edu.unamba.academic.services.actors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.actors.Student;
import pe.edu.unamba.academic.repositories.actors.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    // crear log

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        System.out.println(student);
        return studentRepository.save(student);
    }

    public Student getStudentByStudentCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode);
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
        student.setGender(studentDetails.isGender());
        student.setCareer(studentDetails.getCareer());
        return studentRepository.save(student);
    }
}
