package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.PdfDocumentStepFive;
import pe.edu.unamba.academic.models.steps.ConstancyThesisStepFive;
import pe.edu.unamba.academic.repositories.steps.ConstancyThesisStepFiveRepository;
import pe.edu.unamba.academic.services.steps.ConstancyThesisStepFiveService;
import pe.edu.unamba.academic.services.steps.PdfDocumentStepFiveService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pdfDocument/StepFive")
public class PdfDocumentStepFiveController {

    private final PdfDocumentStepFiveService pdfDocumentStepFiveService;
    private final ConstancyThesisStepFiveService constancyThesisStepFiveService;
    private final ConstancyThesisStepFiveRepository constancyThesisStepFiveRepository;


    @PostMapping("/{id}/upload")
    public ResponseEntity<PdfDocumentStepFive> uploadPdfDocument(@PathVariable Long id, @RequestBody Map<String, String> documentData) {
        System.out.println("Received request to upload PDF for thesis ID: " + id);
        try {
            Optional<ConstancyThesisStepFive> thesisOpt = constancyThesisStepFiveService.getConstancyThesisById(id);

            if (!thesisOpt.isPresent()) {
                System.out.println("Thesis with ID " + id + " not found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String base64Pdf = documentData.get("pdfData");
            if (base64Pdf == null || base64Pdf.isEmpty()) {
                System.out.println("Empty or null pdfData provided.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            PdfDocumentStepFive pdfDocument = pdfDocumentStepFiveService.savePdfDocument(base64Pdf);
            System.out.println("PDF Document saved with ID: " + pdfDocument.getId());

            ConstancyThesisStepFive thesis = thesisOpt.get();
            thesis.setPdfDocument(pdfDocument);
            constancyThesisStepFiveRepository.save(thesis);

            return new ResponseEntity<>(pdfDocument, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}/exists")
    public ResponseEntity<?> checkPDFExists(@PathVariable Long id) {
        boolean exists = constancyThesisStepFiveService.checkIfPDFExists(id);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<Map<String, Object>> viewPDFDocument(@PathVariable Long id) {
        try {
            Optional<ConstancyThesisStepFive> thesisOpt = constancyThesisStepFiveService.getConstancyThesisById(id);

            if (thesisOpt.isPresent() && thesisOpt.get().getPdfDocument() != null) {
                PdfDocumentStepFive pdfDocument = thesisOpt.get().getPdfDocument();

                Map<String, Object> response = new HashMap<>();
                response.put("pdfData", pdfDocument.getPdfData());
                response.put("pdfDocumentId", pdfDocument.getId());

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "No PDF document found for the given thesis.");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "An internal error occurred while fetching the PDF document.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePDFDocument(@PathVariable Long id) {
        try {
            Optional<ConstancyThesisStepFive> thesisOpt = constancyThesisStepFiveService.getConstancyThesisById(id);

            if (thesisOpt.isPresent() && thesisOpt.get().getPdfDocument() != null) {
                PdfDocumentStepFive pdfDocument = thesisOpt.get().getPdfDocument();
                constancyThesisStepFiveService.removePDFDocumentFromConstancy(thesisOpt.get());
                pdfDocumentStepFiveService.deletePdfDocumentById(pdfDocument.getId());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
