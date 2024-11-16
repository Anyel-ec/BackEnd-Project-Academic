package pe.edu.unamba.academic.controller.steps;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.steps.PDFDocumentStepOne;
import pe.edu.unamba.academic.models.steps.TitleReservationStepOne;
import pe.edu.unamba.academic.repositories.steps.PDFDocumentStepOneRepository;
import pe.edu.unamba.academic.repositories.steps.TitleReservationStepOneRepository;
import pe.edu.unamba.academic.services.steps.TitleReservationStepOneService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservas_titulo")
public class TitleReservationStepOneController {
    private final TitleReservationStepOneService titleReservationStepOneService;
    private final TitleReservationStepOneRepository titleReservationStepOneRepository;
    private final PDFDocumentStepOneRepository pdfDocumentStepOneRepository;
    @GetMapping("/")
    public List<TitleReservationStepOne> getAllTitleReservations() {
        return titleReservationStepOneService.getAllTitleReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TitleReservationStepOne> getTitleReservationById(@PathVariable Long id) {
        return titleReservationStepOneService.getTitleReservationById(id)
                .map(titleReservation -> ResponseEntity.ok().body(titleReservation))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/{id}/uploadPdf")
    public ResponseEntity<Map<String, String>> uploadPdf(@PathVariable Long id, @RequestBody Map<String, String> pdfData) {
        Optional<TitleReservationStepOne> reservationOpt = titleReservationStepOneRepository.findById(id);

        if (reservationOpt.isPresent()) {
            TitleReservationStepOne reservation = reservationOpt.get();

            // Crea y guarda el documento PDF
            PDFDocumentStepOne pdfDocument = new PDFDocumentStepOne();
            pdfDocument.setPdfData(pdfData.get("pdfData"));
            pdfDocument = pdfDocumentStepOneRepository.save(pdfDocument);

            // Asocia el PDF guardado a la reserva existente
            reservation.setPdfDocument(pdfDocument);
            titleReservationStepOneRepository.save(reservation);

            // Retorna una respuesta JSON con el mensaje de éxito
            Map<String, String> response = new HashMap<>();
            response.put("message", "PDF asociado con éxito");
            return ResponseEntity.ok(response);
        } else {
            // Retorna una respuesta JSON con el mensaje de error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Reservación no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    @PostMapping("/")
    public ResponseEntity<?> saveTitleReservation(@Valid @RequestBody TitleReservationStepOne titleReservation) {
        try {
            TitleReservationStepOne savedReservation = titleReservationStepOneService.saveTitleReservation(titleReservation);
            return ResponseEntity.ok(savedReservation);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Ya existe una reservación con este título")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar la reservación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar la reservación: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTitleReservation(@PathVariable Long id, @RequestBody TitleReservationStepOne titleReservationDetails) {
        try {
            TitleReservationStepOne updatedReservation = titleReservationStepOneService.updateTitleReservation(id, titleReservationDetails);
            return ResponseEntity.ok(updatedReservation);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Ya existe una reserva con este título")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar la reservación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar la reservación: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTitleReservation(@PathVariable Long id) {
        try {
            titleReservationStepOneService.deleteTitleReservation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/buscar")
    public List<TitleReservationStepOne> searchTitleReservationsByTitle(@RequestParam("title") String title) {
        return titleReservationStepOneService.searchTitleReservationsByTitle(title);
    }
}
