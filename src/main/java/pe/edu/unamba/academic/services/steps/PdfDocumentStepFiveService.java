package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.PdfDocumentStepFive;
import pe.edu.unamba.academic.repositories.steps.PdfDocumentStepFiveRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PdfDocumentStepFiveService {

    private final PdfDocumentStepFiveRepository pdfDocumentStepFiveRepository;

    public PdfDocumentStepFive savePdfDocument(String pdfData) {
        PdfDocumentStepFive pdfDocument = new PdfDocumentStepFive();
        pdfDocument.setPdfData(pdfData);  // Guarda el PDF en formato Base64
        return pdfDocumentStepFiveRepository.save(pdfDocument);
    }


    public void deletePdfDocumentById(Long id) {
        if (pdfDocumentStepFiveRepository.existsById(id)) {
            pdfDocumentStepFiveRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("El documento PDF con ID " + id + " no existe.");
        }
    }


    public Optional<PdfDocumentStepFive> getPdfDocumentById(Long id) {
        return pdfDocumentStepFiveRepository.findById(id);
    }

    public List<PdfDocumentStepFive> getAllPdfDocuments() {
        return pdfDocumentStepFiveRepository.findAll();
    }
}
