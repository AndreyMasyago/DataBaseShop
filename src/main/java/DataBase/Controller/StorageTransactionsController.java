package DataBase.Controller;

import DataBase.Domain.*;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.StorageRepository;
import DataBase.Repository.StorageTransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.*;

@RestController
public class StorageTransactionsController {
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private StorageTransactionsRepository storageTransactionsRepository;

    @GetMapping("/api/storageTransactions/")
    public Iterable<StorageTransactions> list() {
        return storageTransactionsRepository.findAll();
    }

    @GetMapping("/api/storageTransactions/{id}")
    public ResponseEntity<StorageTransactions> retrieveStorageTransaction(@PathVariable int id) {
        Optional<StorageTransactions> storageTransaction = storageTransactionsRepository.findById(id);

        if (!storageTransaction.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(storageTransaction.get());
    }

    @DeleteMapping("/api/storageTransactions/{id}")
    public void deleteStorageTransaction(@PathVariable int id) {
        storageTransactionsRepository.deleteById(id);
    }

    @PostMapping("/api/storageTransactions/")
    public ResponseEntity<StorageTransactions> createStorageTransaction(@RequestBody StorageTransactions storageTransaction) {
        StorageTransactions savedStorageTransaction = storageTransactionsRepository.save(storageTransaction);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStorageTransaction.getStorageTransactionId()).toUri();

        return ResponseEntity.created(location).body(savedStorageTransaction);
    }

    @PutMapping("/api/storageTransactions/{id}")
    public ResponseEntity<StorageTransactions> updateCell(@RequestBody StorageTransactions input, @PathVariable int id) {

        Optional<StorageTransactions> stored = storageTransactionsRepository.findById(id);

        if (!stored.isPresent())
            return ResponseEntity.notFound().build();

        StorageTransactions updated = stored.get();
        updated.setStorage(input.getStorage());
        updated.setAmount(input.getAmount());
        updated.setGoods(input.getGoods());
        updated.setTransactionDate(input.getTransactionDate());
        storageTransactionsRepository.save(updated);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/api/storage-transactions/stored-goods/")
    @ResponseBody
    public Map<String, Object> storedGoods() {
        Map<String, Object> response = new HashMap<>();
        List<Object[]> storedGoods = storageTransactionsRepository.getStoredGoods();

        response.put("results", storedGoods);

        return response;
    }

}
