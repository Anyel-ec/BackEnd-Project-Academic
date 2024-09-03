package pe.edu.unamba.academic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.FilterCertificateNumber;
import pe.edu.unamba.academic.repositories.FilterCertificateNumberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FilterCertificateNumberService {
    @Autowired
    private FilterCertificateNumberRepository filterCertificateNumberRepository;

    public List<FilterCertificateNumber> getAllFilterCertificateNumbers() {
        return filterCertificateNumberRepository.findAll();
    }

    public Optional<FilterCertificateNumber> getFilterCertificateNumberById(Long id) {
        return filterCertificateNumberRepository.findById(id);
    }

    public FilterCertificateNumber saveFilterCertificateNumber(FilterCertificateNumber filterCertificateNumber) {
        return filterCertificateNumberRepository.save(filterCertificateNumber);
    }

    public void deleteFilterCertificateNumber(Long id) {
        filterCertificateNumberRepository.deleteById(id);
    }

    public FilterCertificateNumber updateFilterCertificateNumber(Long id, FilterCertificateNumber filterCertificateNumberDetails) {
        FilterCertificateNumber filterCertificateNumber = filterCertificateNumberRepository.findById(id).get();
        filterCertificateNumber.setNumber(filterCertificateNumberDetails.getNumber());
        filterCertificateNumber.setCertificateFilter(filterCertificateNumberDetails.getCertificateFilter());
        filterCertificateNumber.setYearThesisProposal(filterCertificateNumberDetails.getYearThesisProposal());
        return filterCertificateNumberRepository.save(filterCertificateNumber);
    }
}
