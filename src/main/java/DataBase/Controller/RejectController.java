package DataBase.Controller;

import DataBase.Domain.Goods;
import DataBase.Domain.OrderEntity;
import DataBase.Domain.Reject;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.OrderRepository;
import DataBase.Repository.RejectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
public class RejectController {
    @Autowired
    private RejectRepository rejectRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/reject/")
    public Iterable<Reject> list() {
        return rejectRepository.findAll();
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<Reject> retrieveReject(@PathVariable int id) {
        Optional<Reject> reject = rejectRepository.findById(id);

        if (!reject.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reject.get());
    }

    @DeleteMapping("/reject/{id}")
    public void deleteReject(@PathVariable int id) {
        rejectRepository.deleteById(id);
    }

    @PostMapping("/reject/")
    public ResponseEntity<Reject> createReject(@RequestBody Reject reject) {
        Reject savedReject = rejectRepository.save(reject);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedReject.getRejectId()).toUri();

        return ResponseEntity.created(location).body(savedReject);
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Reject> updateReject(@RequestBody Reject input, @PathVariable int id) {

        Optional<Reject> stored = rejectRepository.findById(id);

        if (!stored.isPresent())
            return ResponseEntity.notFound().build();

        Reject updated = stored.get();
        updated.setOrderEntity(input.getOrderEntity());
        updated.setAmount(input.getAmount());
        updated.setGoods(input.getGoods());
        rejectRepository.save(updated);

        return ResponseEntity.ok(updated);
    }
}
