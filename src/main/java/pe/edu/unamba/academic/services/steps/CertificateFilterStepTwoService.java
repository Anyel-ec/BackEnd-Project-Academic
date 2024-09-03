package pe.edu.unamba.academic.services.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.CertificateFilterStepTwo;
import pe.edu.unamba.academic.repositories.steps.CertificateFilterStepTwoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateFilterStepTwoService {
    @Autowired
    private CertificateFilterStepTwoRepository certificateFilterStepTwoRepository;

    public List<CertificateFilterStepTwo> getAllCertificateFilters() {
        return certificateFilterStepTwoRepository.findAll();
    }

    public Optional<CertificateFilterStepTwo> getCertificateFilterById(Long id) {
        return certificateFilterStepTwoRepository.findById(id);
    }

    public CertificateFilterStepTwo saveCertificateFilter(CertificateFilterStepTwo certificateFilter) {
        return certificateFilterStepTwoRepository.save(certificateFilter);
    }

    public void deleteCertificateFilter(Long id) {
        certificateFilterStepTwoRepository.deleteById(id);
    }

    public CertificateFilterStepTwo updateCertificateFilter(Long id, CertificateFilterStepTwo certificateFilterDetails) {
        CertificateFilterStepTwo certificateFilter = certificateFilterStepTwoRepository.findById(id).get();
        certificateFilter.setMeetsRequirements(certificateFilterDetails.isMeetsRequirements());
        certificateFilter.setObservations(certificateFilterDetails.getObservations());
        return certificateFilterStepTwoRepository.save(certificateFilter);
    }
}
