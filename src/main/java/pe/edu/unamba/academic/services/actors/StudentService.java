package pe.edu.unamba.academic.services.actors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.actors.Student;
import pe.edu.unamba.academic.repositories.actors.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    // Obtener todos los estudiantes
    public List<Student> getAllStudents() {
        LOG.info("Obteniendo todos los estudiantes");
        return studentRepository.findAll();
    }

    // Obtener estudiante por ID
    public Optional<Student> getStudentById(Long id) {
        LOG.info("Buscando estudiante por ID: {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            LOG.info("Estudiante encontrado: {}", student.get());
        } else {
            LOG.warn("No se encontró estudiante con ID: {}", id);
        }
        return student;
    }

    // Guardar estudiante
    public Student saveStudent(Student student) {
        LOG.info("Guardando estudiante con código: {}", student.getStudentCode());
        return studentRepository.save(student);
    }

    // Obtener estudiante por código de alumno
    public Student getStudentByStudentCode(String studentCode) {
        LOG.info("Buscando estudiante por código: {}", studentCode);
        Student student = studentRepository.findByStudentCode(studentCode);
        if (student != null) {
            LOG.info("Estudiante encontrado: {}", student);
        } else {
            LOG.warn("No se encontró estudiante con código: {}", studentCode);
        }
        return student;
    }

    // Eliminar estudiante por ID
    public void deleteStudent(Long id) {
        LOG.info("Eliminando estudiante con ID: {}", id);
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            LOG.info("Estudiante con ID {} eliminado exitosamente", id);
        } else {
            LOG.warn("No se puede eliminar, estudiante con ID {} no encontrado", id);
        }
    }

    // Actualizar estudiante
    public Student updateStudent(Long id, Student studentDetails) {
        LOG.info("Actualizando estudiante con ID: {}", id);
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
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
            LOG.info("Estudiante con ID {} actualizado correctamente", id);
            return studentRepository.save(student);
        } else {
            LOG.warn("No se pudo actualizar, estudiante con ID {} no encontrado", id);
            return null; // Aquí puedes lanzar una excepción personalizada si lo prefieres
        }
    }
}
