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

    @GetMapping("/storageTransactions/")
    public Iterable<StorageTransactions> list() {
        return storageTransactionsRepository.findAll();
    }

    @GetMapping("/storageTransactions/{id}")
    public ResponseEntity<StorageTransactions> retrieveStorageTransaction(@PathVariable int id) {
        Optional<StorageTransactions> storageTransaction = storageTransactionsRepository.findById(id);

        if (!storageTransaction.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(storageTransaction.get());
    }

    @DeleteMapping("/storageTransactions/{id}")
    public void deleteStorageTransaction(@PathVariable int id) {
        storageTransactionsRepository.deleteById(id);
    }

    @PostMapping("/storageTransactions/")
    public ResponseEntity<StorageTransactions> createStorageTransaction(@RequestBody StorageTransactions storageTransaction) {
        StorageTransactions savedStorageTransaction = storageTransactionsRepository.save(storageTransaction);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedStorageTransaction.getStorageTransactionId()).toUri();

        return ResponseEntity.created(location).body(savedStorageTransaction);
    }

    @PutMapping("/storageTransactions/{id}")
    public ResponseEntity<StorageTransactions> updateCell(
            @RequestBody StorageTransactions storageTransaction, @PathVariable int id) {

        Optional<StorageTransactions> storageTransactionsOptional = storageTransactionsRepository.findById(id);


        if (!storageTransactionsOptional.isPresent())
            return ResponseEntity.notFound().build();

        storageTransaction.setStorageTransactionId(id);
        storageTransactionsRepository.save(storageTransaction);

        return ResponseEntity.ok(storageTransaction);
    }

    @GetMapping(value="/storage-transactions/stored-goods/", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> storedGoods() {
        Map<String, Object> response = new HashMap<>();
        List<Object[]> storedGoods = storageTransactionsRepository.getStoredGoods();

        ArrayList<Map<String, Object>> storedGoodsList = new ArrayList<>();

        Map<String, Object> storedGoodsInfo;

        for (Object[] stored : storedGoods) {
            storedGoodsInfo = new HashMap<>();

            storedGoodsInfo.put("goodsId", stored[0]);
            storedGoodsInfo.put("cellsId", stored[1]);
            storedGoodsInfo.put("goodsName", stored[2]);
            storedGoodsInfo.put("size", stored[3]);
            storedGoodsInfo.put("totalAmount", stored[4]);
            storedGoodsInfo.put("totalSize", stored[5]);

            storedGoodsList.add(storedGoodsInfo);
        }

        response.put("results", storedGoodsList);

        return response;
    }

}
