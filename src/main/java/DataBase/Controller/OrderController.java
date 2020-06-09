package DataBase.Controller;

import DataBase.Domain.Goods;
import DataBase.Domain.OrderEntity;
import DataBase.Repository.OrderRepository;
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
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/order/")
    public Iterable<OrderEntity> list() {
        return orderRepository.findAll();
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderEntity> retrieveOrder(@PathVariable int id) {
        Optional<OrderEntity> orders = orderRepository.findById(id);

        if (!orders.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders.get());
    }

    @DeleteMapping("/order/{id}")
    public void deleteOrders(@PathVariable int id) {
        orderRepository.deleteById(id);
    }

    @PostMapping("/order/")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity orderEntity) {
        OrderEntity savedOrders = orderRepository.save(orderEntity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedOrders.getOrderId()).toUri();

        return ResponseEntity.created(location).body(savedOrders);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@RequestBody OrderEntity orderEntity, @PathVariable int id) {

        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);


        if (!orderEntityOptional.isPresent())
            return ResponseEntity.notFound().build();

        orderEntity.setOrderId(id);
        orderRepository.save(orderEntity);

        return ResponseEntity.ok(orderEntity);
    }
}
