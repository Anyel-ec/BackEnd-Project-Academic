package pe.edu.unamba.academic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unamba.academic.models.DeliveryCertificatesStepThirteen;
import pe.edu.unamba.academic.services.DeliveryCertificatesStepThirteenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/constancia_entrega_empastados")
public class DeliveryCertificatesStepThirteenController {

    @Autowired
    private DeliveryCertificatesStepThirteenService deliveryCertificatesStepThirteenService;

    @GetMapping("/")
    public List<DeliveryCertificatesStepThirteen> getAllDeliveries() {
        return deliveryCertificatesStepThirteenService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryCertificatesStepThirteen> getDeliveryById(@PathVariable Long id) {
        return deliveryCertificatesStepThirteenService.getDeliveryById(id)
                .map(delivery -> ResponseEntity.ok().body(delivery))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<DeliveryCertificatesStepThirteen> saveDelivery(@RequestBody DeliveryCertificatesStepThirteen delivery) {
        try {
            return ResponseEntity.ok().body(deliveryCertificatesStepThirteenService.saveDelivery(delivery));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryCertificatesStepThirteen> updateDelivery(@PathVariable Long id, @RequestBody DeliveryCertificatesStepThirteen deliveryDetails) {
        try {
            return ResponseEntity.ok().body(deliveryCertificatesStepThirteenService.updateDelivery(id, deliveryDetails));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        try {
            deliveryCertificatesStepThirteenService.deleteDelivery(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
