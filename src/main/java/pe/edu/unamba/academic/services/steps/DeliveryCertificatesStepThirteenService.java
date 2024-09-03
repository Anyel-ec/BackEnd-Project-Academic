package pe.edu.unamba.academic.services.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.unamba.academic.models.steps.DeliveryCertificatesStepThirteen;
import pe.edu.unamba.academic.repositories.steps.DeliveryCertificatesStepThirteenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryCertificatesStepThirteenService {
    @Autowired
    private DeliveryCertificatesStepThirteenRepository deliveryCertificatesStepThirteenRepository;

    public List<DeliveryCertificatesStepThirteen> getAllDeliveries() {
        return deliveryCertificatesStepThirteenRepository.findAll();
    }

    public Optional<DeliveryCertificatesStepThirteen> getDeliveryById(Long id) {
        return deliveryCertificatesStepThirteenRepository.findById(id);
    }

    public DeliveryCertificatesStepThirteen saveDelivery(DeliveryCertificatesStepThirteen delivery) {
        return deliveryCertificatesStepThirteenRepository.save(delivery);
    }

    public void deleteDelivery(Long id) {
        deliveryCertificatesStepThirteenRepository.deleteById(id);
    }

    public DeliveryCertificatesStepThirteen updateDelivery(Long id, DeliveryCertificatesStepThirteen deliveryDetails) {
        DeliveryCertificatesStepThirteen delivery = deliveryCertificatesStepThirteenRepository.findById(id).get();
        delivery.setMeetsRequirements(deliveryDetails.isMeetsRequirements());
        delivery.setObservations(deliveryDetails.getObservations());
        return deliveryCertificatesStepThirteenRepository.save(delivery);
    }
}
