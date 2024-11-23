package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.PdfDocumentStepOne;
import pe.edu.unamba.academic.repositories.steps.PdfDocumentStepOneRepository;

@RequiredArgsConstructor
@Service
public class PdfDocumentStepOneService {

    private final PdfDocumentStepOneRepository pdfDocumentStepOneRepository;

    public PdfDocumentStepOne savePDFDocument(String pdfData) {
        PdfDocumentStepOne pdfDocument = new PdfDocumentStepOne();
        pdfDocument.setPdfData(pdfData);  // Se guarda el PDF en base64
        return pdfDocumentStepOneRepository.save(pdfDocument);
    }

    public void deletePDFDocumentById(Long id) {
        if (pdfDocumentStepOneRepository.existsById(id)) {
            pdfDocumentStepOneRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("El documento PDF con ID " + id + " no existe.");
        }
    }
}
