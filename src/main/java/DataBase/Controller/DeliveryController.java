package DataBase.Controller;

import DataBase.Domain.Delivery;
import DataBase.Domain.Goods;
import DataBase.Repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;

@RestController
public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping("/delivery/")
    public Iterable<Delivery> list() {
        return deliveryRepository.findAll();
    }

    @GetMapping("/delivery/{id}")
    public ResponseEntity<Delivery> retrieveDelivery(@PathVariable int id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);

        if (!delivery.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(delivery.get());
    }

    @DeleteMapping("/delivery/{id}")
    public void deleteDelivery(@PathVariable int id) {
        deliveryRepository.deleteById(id);
    }

    @PostMapping("/delivery")
    public ResponseEntity<Object> createDelivery(@RequestBody Delivery delivery) {
        Delivery savedDelivery = deliveryRepository.save(delivery);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDelivery.getDeliveryId()).toUri();

        return ResponseEntity.created(location).body(savedDelivery);
    }

    @PutMapping("/delivery/{id}")
    public ResponseEntity<Object> updateDelivery(@RequestBody Delivery delivery, @PathVariable int id) {

        Optional<Delivery> deliveryOptional = deliveryRepository.findById(id);

        if (!deliveryOptional.isPresent())
            return ResponseEntity.notFound().build();

        delivery.setDeliveryId(id);
        deliveryRepository.save(delivery);

        return ResponseEntity.ok(delivery);
    }
}
