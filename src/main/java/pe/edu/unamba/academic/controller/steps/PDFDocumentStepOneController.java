package pe.edu.unamba.academic.controller.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.unamba.academic.models.steps.PDFDocumentStepOne;
import pe.edu.unamba.academic.services.steps.PDFDocumentStepOneService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pdfDocument/OneStep")
public class PDFDocumentStepOneController {

    @Autowired
    private PDFDocumentStepOneService pdfDocumentStepOneService;

    @PostMapping("/")
    public ResponseEntity<PDFDocumentStepOne> uploadPDFDocument(@RequestBody Map<String, String> documentData) {
        try {
            // Recuperar el PDF en base64 desde el cuerpo de la petición
            String base64Pdf = documentData.get("pdfData");
            PDFDocumentStepOne pdfDocument = pdfDocumentStepOneService.savePDFDocument(base64Pdf);
            return new ResponseEntity<>(pdfDocument, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Obtener un documento PDF por ID
    @GetMapping("/{id}")
    public ResponseEntity<PDFDocumentStepOne> getPDFDocument(@PathVariable Long id) {
        Optional<PDFDocumentStepOne> pdfDocument = pdfDocumentStepOneService.getPDFDocumentById(id);
        return pdfDocument.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Listar todos los documentos PDF
    @GetMapping("/")
    public ResponseEntity<List<PDFDocumentStepOne>> listAllPDFDocuments() {
        List<PDFDocumentStepOne> pdfDocuments = pdfDocumentStepOneService.getAllPDFDocuments();
        return new ResponseEntity<>(pdfDocuments, HttpStatus.OK);
    }

    // Eliminar documento PDF por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePDFDocument(@PathVariable Long id) {
        pdfDocumentStepOneService.deletePDFDocumentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
