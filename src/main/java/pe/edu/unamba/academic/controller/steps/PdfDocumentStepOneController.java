package pe.edu.unamba.academic.controller.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.PdfDocumentStepOne;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
import pe.edu.unamba.academic.services.steps.PdfDocumentStepOneService;
import pe.edu.unamba.academic.services.steps.TitleReservationStepOneService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pdfDocument/OneStep")
public class PdfDocumentStepOneController {
    private final PdfDocumentStepOneService pdfDocumentStepOneService;
    private final TitleReservationStepOneService titleReservationStepOneService;

    @PostMapping("/{id}/upload")
    public ResponseEntity<?> uploadPDFDocument(@PathVariable Long id, @RequestBody Map<String, String> documentData) {
        String base64Pdf = documentData.get("pdfData");
        if (base64Pdf == null || base64Pdf.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Los datos del PDF no deben estar vacíos.");
        }

        return titleReservationStepOneService.getTitleReservationById(id)
                .map(titleReservation -> {
                    try {
                        PdfDocumentStepOne pdfDocument = pdfDocumentStepOneService.savePDFDocument(base64Pdf);
                        titleReservation.setPdfDocument(pdfDocument);
                        titleReservationStepOneService.saveTitleReservation(titleReservation);
                        return ResponseEntity.ok(pdfDocument);
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el documento PDF.");
                    }
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<?> checkPDFExists(@PathVariable Long id) {
        boolean exists = titleReservationStepOneService.checkIfPDFExists(id);
        return ResponseEntity.ok(Map.of("exists", exists));
    }


    @GetMapping("/{id}/view")
    public ResponseEntity<Map<String, Object>> viewPDFDocument(@PathVariable Long id) {
        try {
            // Buscar la reservación por ID
            Optional<TitleReservationStepOne> reservation = titleReservationStepOneService.getTitleReservationById(id);

            // Verificar si la reservación tiene un PDF asociado
            if (reservation.isPresent() && reservation.get().getPdfDocument() != null) {
                PdfDocumentStepOne pdfDocument = reservation.get().getPdfDocument();

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
    public ResponseEntity<Void> deletePdfDocument(@PathVariable Long id) {
        try {
            Optional<TitleReservationStepOne> reservation = titleReservationStepOneService.getTitleReservationById(id);

            if (reservation.isPresent() && reservation.get().getPdfDocument() != null) {
                PdfDocumentStepOne pdfDocument = reservation.get().getPdfDocument();
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
