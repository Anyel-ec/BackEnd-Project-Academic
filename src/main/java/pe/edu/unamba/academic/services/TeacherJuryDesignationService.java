package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.TeacherJuryDesignation;
import pe.edu.unamba.academic.repositories.TeacherJuryDesignationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherJuryDesignationService {
    @Autowired
    private TeacherJuryDesignationRepository teacherJuryDesignationRepository;

    public List<TeacherJuryDesignation> getAllTeacherJuryDesignations() {
        return teacherJuryDesignationRepository.findAll();
    }

    public Optional<TeacherJuryDesignation> getTeacherJuryDesignationById(Long id) {
        return teacherJuryDesignationRepository.findById(id);
    }

    public TeacherJuryDesignation saveTeacherJuryDesignation(TeacherJuryDesignation teacherJuryDesignation) {
        return teacherJuryDesignationRepository.save(teacherJuryDesignation);
    }

    public void deleteTeacherJuryDesignation(Long id) {
        teacherJuryDesignationRepository.deleteById(id);
    }
}
