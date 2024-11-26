package pe.edu.unamba.academic.controller.steps;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.ConstancyThesisStepFive;
import pe.edu.unamba.academic.models.steps.PdfDocumentStepFive;
import pe.edu.unamba.academic.models.steps.ProjectApprovalStepTwo;
import pe.edu.unamba.academic.repositories.steps.ConstancyThesisStepFiveRepository;
import pe.edu.unamba.academic.repositories.steps.PdfDocumentStepFiveRepository;
import pe.edu.unamba.academic.services.steps.ConstancyThesisStepFiveService;
import pe.edu.unamba.academic.services.steps.PdfDocumentStepFiveService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/constancia_tesis")
public class ConstancyThesisStepFiveController {
    private final ConstancyThesisStepFiveService constancyThesisStepFiveService;
    private final ConstancyThesisStepFiveRepository constancyThesisStepFiveRepository;
    private final PdfDocumentStepFiveRepository pdfDocumentStepFiveRepository;

    @GetMapping
    public ResponseEntity<List<ConstancyThesisStepFive>> getAllConstancyThesis() {
        List<ConstancyThesisStepFive> thesis = constancyThesisStepFiveService.getAllConstancyThesis();
        return ResponseEntity.ok(thesis);
    }@GetMapping("/student/{studentCode}")
    public ResponseEntity<?> getConstancyByStudentCode(@PathVariable String studentCode) {
        Optional<ConstancyThesisStepFive> constancy = constancyThesisStepFiveService.getConstancyByStudentCode(studentCode);
        if (constancy.isPresent()) {
            return ResponseEntity.ok(constancy.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una constancia de tesis para el código de estudiante proporcionado.");
        }
    }

    @PostMapping("/{id}/uploadPdf")
    public ResponseEntity<Map<String, String>> uploadPdf(@PathVariable Long id, @RequestBody Map<String, String> pdfData) {
        // Busca la tesis por su ID
        Optional<ConstancyThesisStepFive> thesisOpt = constancyThesisStepFiveService.getConstancyThesisById(id);
        if (thesisOpt.isPresent()) {
            ConstancyThesisStepFive thesis = thesisOpt.get();

            // Crea un nuevo documento PDF
            PdfDocumentStepFive pdfDocument = new PdfDocumentStepFive();
            pdfDocument.setPdfData(pdfData.get("pdfData"));
            pdfDocument = pdfDocumentStepFiveRepository.save(pdfDocument);

            // Asocia el documento PDF a la tesis
            thesis.setPdfDocument(pdfDocument);
            constancyThesisStepFiveRepository.save(thesis);

            // Respuesta de éxito
            Map<String, String> response = new HashMap<>();
            response.put("message", "PDF asociado con éxito");
            return ResponseEntity.ok(response);
        } else {
            // Respuesta de error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Tesis no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConstancyThesisStepFive> getConstancyThesisById(@PathVariable Long id) {
        Optional<ConstancyThesisStepFive> thesis = constancyThesisStepFiveService.getConstancyThesisById(id);
        return thesis.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConstancyThesisStepFive> createConstancyThesis(
            @Valid @RequestBody ConstancyThesisStepFive constancyThesisStepFive) {
        ConstancyThesisStepFive savedThesis = constancyThesisStepFiveService.createConstancyThesis(constancyThesisStepFive);
        return ResponseEntity.ok(savedThesis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConstancyThesisStepFive> updateConstancyThesis(
            @PathVariable Long id,
            @Valid @RequestBody ConstancyThesisStepFive constancyThesisStepFive) {
        Optional<ConstancyThesisStepFive> saveThesis =
                constancyThesisStepFiveService.updateConstancyThesis(id, constancyThesisStepFive);
        return saveThesis.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConstancyThesisById(@PathVariable Long id) {
        boolean deleted = constancyThesisStepFiveService.deleteConstancyThesisById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
