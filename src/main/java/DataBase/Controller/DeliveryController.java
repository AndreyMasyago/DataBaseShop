package DataBase.Controller;

import DataBase.Domain.Delivery;
import DataBase.Domain.DeliveryContent;
import DataBase.Domain.Goods;
import DataBase.Repository.DeliveryContentRepository;
import DataBase.Repository.DeliveryRepository;
import DataBase.Repository.GoodsRepository;
import DataBase.Request.DeliveryContentRequest;
import DataBase.Request.DeliveryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.*;

@RestController
public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryContentRepository deliveryContentRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping("/api/delivery/")
    public Iterable<Delivery> list() {
        return deliveryRepository.findAll();
    }

    @GetMapping("/api/delivery/{id}")
    public ResponseEntity<Delivery> retrieveDelivery(@PathVariable int id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);

        if (!delivery.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(delivery.get());
    }

    @DeleteMapping("/api/delivery/{id}")
    public void deleteDelivery(@PathVariable int id) {
        deliveryRepository.deleteById(id);
    }

    @PostMapping("/api/delivery")
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        Delivery savedDelivery = deliveryRepository.save(delivery);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDelivery.getDeliveryId()).toUri();

        return ResponseEntity.created(location).body(savedDelivery);
    }

    @PutMapping("/api/delivery/{id}")
    public ResponseEntity<Delivery> updateDelivery(@RequestBody Delivery input, @PathVariable int id) {

        Optional<Delivery> stored = deliveryRepository.findById(id);

        if (!stored.isPresent())
            return ResponseEntity.notFound().build();

        Delivery updated = stored.get();
        updated.setArrivingDateOnStorage(input.getArrivingDateOnStorage());
        deliveryRepository.save(updated);

        return ResponseEntity.ok(updated);
    }

    @PostMapping("/api/delivery/addDelivery")
    @Transactional
    public ResponseEntity<Map<String, Object>> addDelivery(@RequestBody DeliveryRequest request) {
        Map<String, Object> errors = request.validate();
        if (errors != null) {
            return ResponseEntity.badRequest().body(errors);
        }

        Delivery delivery = new Delivery();
        delivery.setArrivingDateOnStorage(request.arrivingDateOnStorage);
        deliveryRepository.save(delivery);

        List<DeliveryContent> contentList = new ArrayList<>();

        for (DeliveryContentRequest content : request.content) {
            DeliveryContent deliveryContent = new DeliveryContent();
            deliveryContent.setDelivery(delivery);
            deliveryContent.setAmount(content.amount);

            Optional<Goods> goods = goodsRepository.findById(content.goodsId);
            if (!goods.isPresent()) {
                continue;
            }
            deliveryContent.setGoods(goods.get());

            contentList.add(deliveryContent);
        }

        deliveryContentRepository.saveAll(contentList);

        Map<String, Object> response = new HashMap<>();
        response.put("deliveryId", delivery.getDeliveryId());

        return ResponseEntity.ok(response);
    }
}
