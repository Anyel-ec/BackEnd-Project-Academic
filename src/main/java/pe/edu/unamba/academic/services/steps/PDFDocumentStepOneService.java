package pe.edu.unamba.academic.services.steps;

import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.PDFDocumentStepOne;
import pe.edu.unamba.academic.repositories.steps.PDFDocumentStepOneRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PDFDocumentStepOneService {

    private final PDFDocumentStepOneRepository pdfDocumentStepOneRepository;

    // Constructor explícito para la inyección de dependencias
    public PDFDocumentStepOneService(PDFDocumentStepOneRepository pdfDocumentStepOneRepository) {
        this.pdfDocumentStepOneRepository = pdfDocumentStepOneRepository;
    }

    public PDFDocumentStepOne savePDFDocument(String pdfData) {
        PDFDocumentStepOne pdfDocument = new PDFDocumentStepOne();
        pdfDocument.setPdfData(pdfData);  // Se guarda el PDF en base64
        return pdfDocumentStepOneRepository.save(pdfDocument);
    }

    public Optional<PDFDocumentStepOne> getPDFDocumentById(Long id) {
        return pdfDocumentStepOneRepository.findById(id);
    }

    public List<PDFDocumentStepOne> getAllPDFDocuments() {
        return pdfDocumentStepOneRepository.findAll();
    }

    public void deletePDFDocumentById(Long id) {
        if (pdfDocumentStepOneRepository.existsById(id)) {
            pdfDocumentStepOneRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("El documento PDF con ID " + id + " no existe.");
        }
    }
}
