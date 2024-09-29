package pe.edu.unamba.academic.services.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.PDFDocumentStepOne;
import pe.edu.unamba.academic.repositories.steps.PDFDocumentStepOneRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PDFDocumentStepOneService {

    @Autowired
    private PDFDocumentStepOneRepository pdfDocumentStepOneRepository;

    // Guardar documento PDF
    public PDFDocumentStepOne savePDFDocument(String pdfData) {
        PDFDocumentStepOne pdfDocument = new PDFDocumentStepOne();
        pdfDocument.setPdfData(pdfData);
        return pdfDocumentStepOneRepository.save(pdfDocument);
    }

    // Obtener documento PDF por ID
    public Optional<PDFDocumentStepOne> getPDFDocumentById(Long id) {
        return pdfDocumentStepOneRepository.findById(id);
    }

    // Listar todos los documentos
    public List<PDFDocumentStepOne> getAllPDFDocuments() {
        return pdfDocumentStepOneRepository.findAll();
    }

    // Eliminar documento PDF por ID
    public void deletePDFDocumentById(Long id) {
        pdfDocumentStepOneRepository.deleteById(id);
    }
}
