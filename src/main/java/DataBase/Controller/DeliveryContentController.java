package DataBase.Controller;

import DataBase.Domain.Catalog;
import DataBase.Domain.Delivery;
import DataBase.Domain.DeliveryContent;
import DataBase.Domain.Goods;
import DataBase.Repository.DeliveryContentRepository;
import DataBase.Repository.DeliveryRepository;
import DataBase.Repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
public class DeliveryContentController {
    @Autowired
    private DeliveryContentRepository deliveryContentRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping("/api/deliveryContent/")
    public Iterable<DeliveryContent> list() {
        return deliveryContentRepository.findAll();
    }

    @GetMapping("/api/deliveryContent/{id}")
    public ResponseEntity<DeliveryContent> retrieveDeliveryContent(@PathVariable int id) {
        Optional<DeliveryContent> deliveryContent = deliveryContentRepository.findById(id);

        if (!deliveryContent.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deliveryContent.get());
    }

    @DeleteMapping("/api/deliveryContent/{id}")
    public void deleteDeliveryContent(@PathVariable int id) {
        deliveryContentRepository.deleteById(id);
    }

    @PostMapping("/api/deliveryContent/")
    public ResponseEntity<DeliveryContent> createDeliveryContent(@RequestBody DeliveryContent deliveryContent) {
        DeliveryContent savedDeliveryContent = deliveryContentRepository.save(deliveryContent);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDeliveryContent.getDeliveryContentId()).toUri();

        return ResponseEntity.created(location).body(savedDeliveryContent);
    }

    @PutMapping("/api/deliveryContent/{id}")
    public ResponseEntity<DeliveryContent> updateDeliveryContent(@RequestBody DeliveryContent input, @PathVariable int id) {

        Optional<DeliveryContent> stored = deliveryContentRepository.findById(id);

        if (!stored.isPresent())
            return ResponseEntity.notFound().build();

        DeliveryContent updated = stored.get();
        updated.setDelivery(input.getDelivery());
        updated.setAmount(input.getAmount());
        updated.setGoods(input.getGoods());
        deliveryContentRepository.save(updated);

        return ResponseEntity.ok(updated);
    }
}
