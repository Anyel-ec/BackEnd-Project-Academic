package pe.edu.unamba.academic.controller.steps;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.PDFDocumentStepOne;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
import pe.edu.unamba.academic.services.steps.PDFDocumentStepOneService;
import pe.edu.unamba.academic.services.steps.TitleReservationStepOneService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/pdfDocument/OneStep")
public class PDFDocumentStepOneController {

    private final PDFDocumentStepOneService pdfDocumentStepOneService;
    private final TitleReservationStepOneService titleReservationStepOneService;

    public PDFDocumentStepOneController(PDFDocumentStepOneService pdfDocumentStepOneService,
                                        TitleReservationStepOneService titleReservationStepOneService) {
        this.pdfDocumentStepOneService = pdfDocumentStepOneService;
        this.titleReservationStepOneService = titleReservationStepOneService;
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<PDFDocumentStepOne> uploadPDFDocument(@PathVariable Long id, @RequestBody Map<String, String> documentData) {
        try {
            Optional<TitleReservationStepOne> reservation = titleReservationStepOneService.getTitleReservationById(id);

            if (!reservation.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String base64Pdf = documentData.get("pdfData");
            if (base64Pdf == null || base64Pdf.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Si no hay PDF o está vacío
            }

            PDFDocumentStepOne pdfDocument = pdfDocumentStepOneService.savePDFDocument(base64Pdf);

            TitleReservationStepOne titleReservation = reservation.get();
            titleReservation.setPdfDocument(pdfDocument);
            titleReservationStepOneService.saveTitleReservation(titleReservation);

            return new ResponseEntity<>(pdfDocument, HttpStatus.CREATED); // Devolver el documento creado con su ID
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Manejar errores del servidor
        }
    }


    @GetMapping("/{id}/view")
    public ResponseEntity<Map<String, Object>> viewPDFDocument(@PathVariable Long id) {
        try {
            // Buscar la reservación por ID
            Optional<TitleReservationStepOne> reservation = titleReservationStepOneService.getTitleReservationById(id);

            // Verificar si la reservación tiene un PDF asociado
            if (reservation.isPresent() && reservation.get().getPdfDocument() != null) {
                PDFDocumentStepOne pdfDocument = reservation.get().getPdfDocument();

                // Preparar la respuesta con los datos en base64 y el ID del PDF
                Map<String, Object> response = new HashMap<>();
                response.put("pdfData", pdfDocument.getPdfData());
                response.put("pdfDocumentId", pdfDocument.getId());

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // Retornar un 404 si no hay PDF asociado a la reservación
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "No PDF document found for the given reservation.");
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Manejar errores del servidor
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "An internal error occurred while fetching the PDF document.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePDFDocument(@PathVariable Long id) {
        try {
            Optional<TitleReservationStepOne> reservation = titleReservationStepOneService.getTitleReservationById(id);

            if (reservation.isPresent() && reservation.get().getPdfDocument() != null) {
                PDFDocumentStepOne pdfDocument = reservation.get().getPdfDocument();
                titleReservationStepOneService.removePDFDocumentFromReservation(reservation.get());
                pdfDocumentStepOneService.deletePDFDocumentById(pdfDocument.getId());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // PDF eliminado exitosamente
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Si el PDF no existe
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Manejar errores del servidor
        }
    }

}
