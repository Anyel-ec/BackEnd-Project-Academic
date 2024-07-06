package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.ExtensionPeriodStepEight;
import pe.edu.unamba.academic.repositories.ExtensionPeriodStepEightRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExtensionPeriodStepEightService {
    @Autowired
    private ExtensionPeriodStepEightRepository extensionPeriodStepEightRepository;

    public List<ExtensionPeriodStepEight> getAllExtensions() {
        return extensionPeriodStepEightRepository.findAll();
    }

    public Optional<ExtensionPeriodStepEight> getExtensionById(Long id) {
        return extensionPeriodStepEightRepository.findById(id);
    }

    public ExtensionPeriodStepEight saveExtension(ExtensionPeriodStepEight extension) {
        return extensionPeriodStepEightRepository.save(extension);
    }

    public void deleteExtension(Long id) {
        extensionPeriodStepEightRepository.deleteById(id);
    }

    public ExtensionPeriodStepEight updateExtension(Long id, ExtensionPeriodStepEight extensionDetails) {
        ExtensionPeriodStepEight extension = extensionPeriodStepEightRepository.findById(id).get();
        extension.setStartDate(extensionDetails.getStartDate());
        extension.setEndDate(extensionDetails.getEndDate());
        extension.setObservations(extensionDetails.getObservations());
        return extensionPeriodStepEightRepository.save(extension);
    }
}
