package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.Rol;
import pe.edu.unamba.academic.repositories.RolRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    public Optional<Rol> getRolById(Integer id) {
        return rolRepository.findById(id);
    }

    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public void deleteRol(Integer id) {
        rolRepository.deleteById(id);
    }

    public Rol updateRol (int id, Rol rolDetails) {
        Rol rol = rolRepository.findById(id).get();
        rol.setName(rolDetails.getName());
        return rolRepository.save(rol);
    }


}
