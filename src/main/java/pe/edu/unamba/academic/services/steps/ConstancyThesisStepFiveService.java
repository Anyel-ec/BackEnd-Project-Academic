package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.ConstancyThesisStepFive;
import pe.edu.unamba.academic.repositories.steps.ConstancyThesisStepFiveRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConstancyThesisStepFiveService {
    private final ConstancyThesisStepFiveRepository constancyThesisStepFiveRepository;

    public List<ConstancyThesisStepFive> getAllConstancyThesis() {
        return constancyThesisStepFiveRepository.findAll();
    }
    public Optional<ConstancyThesisStepFive> getConstancyThesisById(Long id) {
        return constancyThesisStepFiveRepository.findById(id);
    }
    public ConstancyThesisStepFive createConstancyThesis(ConstancyThesisStepFive constancyThesisStepFive) {
        return constancyThesisStepFiveRepository.save(constancyThesisStepFive);
    }
    public Optional<ConstancyThesisStepFive> updateConstancyThesis(Long id, ConstancyThesisStepFive updatedConstancyThesis) {
        log.info("Actualizando la constancia de tesis con ID: {}", id);
        return Optional.ofNullable(constancyThesisStepFiveRepository.findById(id)
                .map(existingThesis -> {
                    updateFields(existingThesis, updatedConstancyThesis);
                    log.info("Constancia de tesis actualizada con éxito para el ID: {}", id);
                    return constancyThesisStepFiveRepository.save(existingThesis);
                })
                .orElseGet(() -> {
                    log.warn("No se encontró la constancia de tesis con ID: {}", id);
                    return null; // O lanzar una excepción si se espera que la constancia siempre exista
                }));
    }

    private void updateFields(ConstancyThesisStepFive existingThesis, ConstancyThesisStepFive updatedThesis) {
        if (updatedThesis.getReportReviewStepFour() != null) {
            existingThesis.setReportReviewStepFour(updatedThesis.getReportReviewStepFour());
        }
        if (updatedThesis.getObservations() != null) {
            existingThesis.setObservations(updatedThesis.getObservations());
        }
        // Asegura que `meetsRequirements` se actualiza incluso si es false
        existingThesis.setMeetsRequirements(updatedThesis.isMeetsRequirements());

        // Aquí puedes agregar más campos para actualizar según sea necesario
    }


    public void removePDFDocumentFromConstancy(ConstancyThesisStepFive constancyThesis) {
        constancyThesis.setPdfDocument(null);  // Eliminara la referencia al documento PDF
        constancyThesisStepFiveRepository.save(constancyThesis);  // Guardar la actualización en la base de datos
    }
    public boolean checkIfPDFExists(Long reservationId) {
        return constancyThesisStepFiveRepository.findById(reservationId)
                .map(ConstancyThesisStepFive::getPdfDocument)
                .isPresent();
    }
    public boolean deleteConstancyThesisById(Long id) {
        if(constancyThesisStepFiveRepository.existsById(id)) {
            constancyThesisStepFiveRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
