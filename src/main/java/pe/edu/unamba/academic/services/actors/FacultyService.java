package pe.edu.unamba.academic.services.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.actors.Faculty;
import pe.edu.unamba.academic.repositories.actors.FacultyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    public List<Faculty> getAllFacultades() {
        return facultyRepository.findAll();
    }

    public Optional<Faculty> getFacultadById(Long id) {
        return facultyRepository.findById(id);
    }

    public Faculty saveFacultad(Faculty facultad) {
        return facultyRepository.save(facultad);
    }

    public void deleteFacultad(Long id) {
        facultyRepository.deleteById(id);
    }

    public Faculty updateFacultad(Long id, Faculty facultadDetails) {
        Faculty facultad = facultyRepository.findById(id).get();
        facultad.setNameFaculty(facultadDetails.getNameFaculty());
        return facultyRepository.save(facultad);
    }
}
