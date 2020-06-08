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

    @GetMapping("/deliveryContent/")
    public Iterable<DeliveryContent> list() {
        return deliveryContentRepository.findAll();
    }

    @GetMapping("/deliveryContent/{id}")
    public ResponseEntity<DeliveryContent> retrieveDeliveryContent(@PathVariable int id) {
        Optional<DeliveryContent> deliveryContent = deliveryContentRepository.findById(id);

        if (!deliveryContent.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deliveryContent.get());
    }

    @DeleteMapping("/deliveryContent/{id}")
    public void deleteDeliveryContent(@PathVariable int id) {
        deliveryContentRepository.deleteById(id);
    }

    @PostMapping("/deliveryContent/")
    public ResponseEntity<DeliveryContent> createDeliveryContent(@RequestBody DeliveryContent deliveryContent) {
        DeliveryContent savedDeliveryContent = deliveryContentRepository.save(deliveryContent);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDeliveryContent.getDeliveryContentId()).toUri();

        return ResponseEntity.created(location).body(savedDeliveryContent);
    }

    @PutMapping("/deliveryContent/{id}")
    public ResponseEntity<Object> updateDeliveryContent(@RequestBody DeliveryContent deliveryContent, @PathVariable int id) {

        Optional<DeliveryContent> deliveryContentOptional = deliveryContentRepository.findById(id);

        if (!deliveryContentOptional.isPresent())
            return ResponseEntity.notFound().build();

        deliveryContent.setDeliveryContentId(id);
        deliveryContentRepository.save(deliveryContent);

        return ResponseEntity.ok(deliveryContent);
    }
}
