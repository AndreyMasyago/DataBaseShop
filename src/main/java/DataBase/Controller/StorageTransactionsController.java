package DataBase.Controller;

import DataBase.Domain.*;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.StorageRepository;
import DataBase.Repository.StorageTransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
