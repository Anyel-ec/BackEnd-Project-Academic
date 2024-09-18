package pe.edu.unamba.academic.services.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.actors.Rol;
import pe.edu.unamba.academic.repositories.actors.RolRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // Obtener todos los roles
    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    // Obtener un rol por ID
    public Optional<Rol> getRolById(Integer id) {
        return rolRepository.findById(id);
    }

    // Guardar un nuevo rol
    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }

    // Eliminar un rol por ID
    public void deleteRol(Integer id) {
        Optional<Rol> rol = rolRepository.findById(id);
        if (rol.isPresent()) {
            rolRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("El rol con ID " + id + " no existe.");
        }
    }

    // Actualizar un rol existente
    public Rol updateRol(int id, Rol rolDetails) {
        return rolRepository.findById(id)
                .map(rol -> {
                    rol.setName(rolDetails.getName()); // Actualizar solo el nombre, puedes ajustar según tus necesidades
                    return rolRepository.save(rol);
                })
                .orElseThrow(() -> new IllegalArgumentException("El rol con ID " + id + " no se encuentra."));
    }
}

