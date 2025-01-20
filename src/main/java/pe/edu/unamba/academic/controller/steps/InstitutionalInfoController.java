package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.InstitutionalInfo;
import pe.edu.unamba.academic.services.steps.InstitutionalInfoService;

@RestController
@RequestMapping("/api/v1/informacion_institucional")
@RequiredArgsConstructor
@Slf4j
public class InstitutionalInfoController {

    private final InstitutionalInfoService institutionalInfoService;

    @GetMapping
    public ResponseEntity<InstitutionalInfo> getInstitutionalInfo() {
        InstitutionalInfo info = institutionalInfoService.getInstitutionalInfo();
        if (info != null) {
            return ResponseEntity.ok(info);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<InstitutionalInfo> createInstitutionalInfo(@RequestBody InstitutionalInfo info) {
        log.info("Creating institutional info: {}", info);
        if (institutionalInfoService.getInstitutionalInfo() != null) {
            return ResponseEntity.badRequest().body(null); // Ya existe, no se puede crear otro
        }
        info.setId(1L); // Forzar el ID único
        InstitutionalInfo createdInfo = institutionalInfoService.saveInstitutionalInfo(info);
        return ResponseEntity.ok(createdInfo);
    }

    @PutMapping
    public ResponseEntity<InstitutionalInfo> updateInstitutionalInfo(@RequestBody InstitutionalInfo info) {
        if (info.getId() == null || !info.getId().equals(1L)) {
            return ResponseEntity.badRequest().build();
        }
        InstitutionalInfo updatedInfo = institutionalInfoService.saveInstitutionalInfo(info);
        return ResponseEntity.ok(updatedInfo);
    }


    /**
     * Eliminar la información institucional única.
     *
     * @return Respuesta vacía en caso de éxito.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteInstitutionalInfo() {
        log.info("Deleting institutional info");
        InstitutionalInfo existingInfo = institutionalInfoService.getInstitutionalInfo();
        if (existingInfo != null) {
            institutionalInfoService.deleteInstitutionalInfo();
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
