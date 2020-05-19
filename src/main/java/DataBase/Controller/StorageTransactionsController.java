package DataBase.Controller;

import DataBase.Domain.*;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.StorageRepository;
import DataBase.Repository.StorageTransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Map;

@Controller
public class StorageTransactionsController {
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private StorageTransactionsRepository storageTransactionsRepository;

    @GetMapping("/insert/storageTransactions")
    public String storageTransaction(Map<String, Object> model) {
        generateIterators(model);
        model.put("currentId", 0);
        return "storageTransactions";
    }

    @PostMapping("/insert/storageTransactions/delete")
    public String deleteStorageTransaction(
            @RequestParam int storageTransactionId,
            Map<String, Object> model){
        storageTransactionsRepository.deleteById(storageTransactionId);
        generateIterators(model);
        model.put("currentId", 0);
        return "storageTransactions";
    }

    @PostMapping("/insert/storageTransactions")
    public String addStorageTransaction(
            @RequestParam int goodsId,
            @RequestParam int amount,
            @RequestParam int cellsId,
            @RequestParam Date transactionDate,
            Map<String, Object> model
            ){
        Goods tempGoods = goodsRepository.findByGoodsId(goodsId);
        Storage tempStorage = storageRepository.findByCellsId(cellsId);
        StorageTransactions tempStorageTransaction = new StorageTransactions(
                tempGoods, amount, tempStorage, transactionDate
        );
        storageTransactionsRepository.save(tempStorageTransaction);

        generateIterators(model);
        model.put("currentId", tempStorageTransaction.getStorageTransactionId());
        return "storageTransactions";
    }

    private void generateIterators(Map<String, Object> model) {
        Iterable<StorageTransactions> storageTransactionsIt = storageTransactionsRepository.findAll();
        model.put("storageTransactions", storageTransactionsIt);

        Iterable<Goods> goodsIt = goodsRepository.findAll();
        model.put("goods", goodsIt);

        Iterable<Storage> storageIt = storageRepository.findAll();
        model.put("storage", storageIt);
    }
}
