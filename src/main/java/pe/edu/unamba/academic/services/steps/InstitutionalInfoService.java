package pe.edu.unamba.academic.services.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.InstitutionalInfo;
import pe.edu.unamba.academic.repositories.steps.InstitutionalInfoRepository;

@Service
@RequiredArgsConstructor
public class InstitutionalInfoService {

    private final InstitutionalInfoRepository repository;

    public InstitutionalInfo getInstitutionalInfo() {
        return repository.findById(1L).orElse(null);
    }

    public InstitutionalInfo saveInstitutionalInfo(InstitutionalInfo info) {
        info.setId(1L); // Forzar el ID único
        return repository.save(info);
    }

    public void deleteInstitutionalInfo() {
        repository.deleteById(1L); // Eliminar solo el registro con ID 1
    }
}
