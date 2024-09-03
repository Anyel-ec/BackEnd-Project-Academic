package pe.edu.unamba.academic.controller.actors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.actors.Rol;
import pe.edu.unamba.academic.services.actors.RolService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {

    @Autowired
    private RolService rolService;


    @GetMapping("/")
    public List<Rol> getAllRoles() {
        return rolService.getAllRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Integer id) {
        return rolService.getRolById(id)
                .map(rol -> ResponseEntity.ok().body(rol))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Rol> saveRol(@RequestBody Rol rol) {
        try{
            return ResponseEntity.ok().body(rolService.saveRol(rol));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Integer id, @RequestBody Rol rolDetails) {
        try {
            return ResponseEntity.ok().body(rolService.updateRol(id, rolDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Integer id) {
        try {
            rolService.deleteRol(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
