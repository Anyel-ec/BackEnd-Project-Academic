package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.DefenseCertificateStepNine;
import pe.edu.unamba.academic.repositories.DefenseCertificateStepNineRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DefenseCertificateStepNineService {
    @Autowired
    private DefenseCertificateStepNineRepository defenseCertificateStepNineRepository;

    public List<DefenseCertificateStepNine> getAllCertificates() {
        return defenseCertificateStepNineRepository.findAll();
    }

    public Optional<DefenseCertificateStepNine> getCertificateById(Long id) {
        return defenseCertificateStepNineRepository.findById(id);
    }

    public DefenseCertificateStepNine saveCertificate(DefenseCertificateStepNine certificate) {
        return defenseCertificateStepNineRepository.save(certificate);
    }

    public void deleteCertificate(Long id) {
        defenseCertificateStepNineRepository.deleteById(id);
    }

    public DefenseCertificateStepNine updateCertificate(Long id, DefenseCertificateStepNine certificateDetails) {
        DefenseCertificateStepNine certificate = defenseCertificateStepNineRepository.findById(id).get();
        certificate.setMeetsRequirements(certificateDetails.isMeetsRequirements());
        certificate.setObservations(certificateDetails.getObservations());
        return defenseCertificateStepNineRepository.save(certificate);
    }
}
