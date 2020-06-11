package DataBase.Controller;

import DataBase.Domain.Goods;
import DataBase.Domain.Provider;
import DataBase.Repository.CatalogRepository;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private ProviderRepository providerRepository;

    @GetMapping("/api/goods/")
    public Iterable<Goods> list() {
        return goodsRepository.findAll();
    }

    @GetMapping("/api/goods/{id}")
    public ResponseEntity<Goods> retrieveGoods(@PathVariable int id) {
        Optional<Goods> goods = goodsRepository.findById(id);

        if (!goods.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goods.get());
    }

    @DeleteMapping("/api/goods/{id}")
    public void deleteGoods(@PathVariable int id) {
        goodsRepository.deleteById(id);
    }

    @PostMapping("/api/goods")
    public ResponseEntity<Goods> createGoods(@RequestBody Goods goods) {
        Goods savedGoods = goodsRepository.save(goods);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedGoods.getGoodsId()).toUri();

        return ResponseEntity.created(location).body(savedGoods);
    }

    @PutMapping("/api/goods/{id}")
    public ResponseEntity<Goods> updateGoods(@RequestBody Goods input, @PathVariable int id) {

        Optional<Goods> stored = goodsRepository.findById(id);

        if (!stored.isPresent())
            return ResponseEntity.notFound().build();

        Goods updated = stored.get();
        updated.setCatalog(input.getCatalog());
        updated.setDeliveryTime(input.getDeliveryTime());
        updated.setSize(input.getSize());
        updated.setPurchasePrice(input.getPurchasePrice());
        updated.setSellingPrice(input.getSellingPrice());
        updated.setProducer(input.getProducer());
        updated.setProvider(input.getProvider());
        goodsRepository.save(updated);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/api/goods/goods-details/")
    @ResponseBody
    public Map<String, Object> goodsDetails(@RequestParam String goodsSearch) {
        Map<String, Object> response = new HashMap<>();
        List<Goods> goods = goodsRepository.getGoodsInfo(goodsSearch);
        Long count = goodsRepository.countGoodsInfo(goodsSearch);

        ArrayList<Map<String, Object>> goodsInfoList = new ArrayList<>();

        Map<String, Object> goodsInfo;

        for (Goods p : goods) {
            goodsInfo = new HashMap<>();

            goodsInfo.put("goodsId", p.getGoodsId());
            goodsInfo.put("deliveryTime", p.getDeliveryTime());
            goodsInfo.put("purchasePrice", p.getPurchasePrice());
            goodsInfo.put("sellingPrice", p.getSellingPrice());
            goodsInfo.put("producer", p.getProducer());
            goodsInfo.put("goodsName", p.getGoodsName());
            goodsInfo.put("providerName", p.getProvider().getProviderName());
            goodsInfo.put("category", p.getProvider().getCategory());

            goodsInfoList.add(goodsInfo);
        }

        response.put("results", goodsInfoList);
        response.put("count", count);

        return response;
    }

    @GetMapping("/api/goods/bestsellers/")
    @ResponseBody
    public Map<String, Object> bestsellers() {
        Map<String, Object> response = new HashMap<>();
        Page<Object[]> bestSellers = goodsRepository.getBestSellers(PageRequest.of(0,10));

        ArrayList<Map<String, Object>> goodsInfoList = new ArrayList<>();

        Map<String, Object> goodsInfo;

        for (Object[] b: bestSellers) {
            Goods p = (Goods) b[0];
            Long amount = (Long) b[1];
            goodsInfo = new HashMap<>();

            goodsInfo.put("goodsId", p.getGoodsId());
            goodsInfo.put("deliveryTime", p.getDeliveryTime());
            goodsInfo.put("purchasePrice", p.getPurchasePrice());
            goodsInfo.put("sellingPrice", p.getSellingPrice());
            goodsInfo.put("producer", p.getProducer());
            goodsInfo.put("goodsName", p.getGoodsName());
            goodsInfo.put("providerName", p.getProvider().getProviderName());
            goodsInfo.put("category", p.getProvider().getCategory());

            goodsInfo.put("amount", amount);

            goodsInfoList.add(goodsInfo);
        }

        response.put("results", goodsInfoList);

        return response;
    }

    @GetMapping("/api/goods-rejects/")
    @ResponseBody
    public Map<String, Object> rejects() {
        Map<String, Object> response = new HashMap<>();
        List<Object[]> rejects = goodsRepository.getRejects();

        ArrayList<Map<String, Object>> rejectsList = new ArrayList<>();

        Map<String, Object> goodsInfo;

        for (Object[] r: rejects) {
            goodsInfo = new HashMap<>();
            Goods g = (Goods) r[0];
            Long amount = (Long) r[1];

            goodsInfo.put("goodsId", g.getGoodsId());
            goodsInfo.put("producer", g.getProducer());
            goodsInfo.put("goodsName", g.getGoodsName());
            goodsInfo.put("providerName", g.getProvider().getProviderName());
            goodsInfo.put("amount", amount);

            rejectsList.add(goodsInfo);
        }

        response.put("results", rejectsList);

        return response;
    }

    @GetMapping("/api/reject-providers/")
    @ResponseBody
    public Map<String, Object> rejectProviders() {
        Map<String, Object> response = new HashMap<>();
        List<Provider> rejects = goodsRepository.getRejectProviders();

        response.put("results", rejects);

        return response;
    }

    @GetMapping("/api/goods/daily-report/")
    @ResponseBody
    public Map<String, Object> dailyReport(@RequestParam("reportDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date reportDate) {
        Map<String, Object> response = new HashMap<>();
        List<Object[]> dailyReport = goodsRepository.getDailyReport(reportDate);

        ArrayList<Map<String, Object>> goodsInfoList = new ArrayList<>();

        Map<String, Object> goodsInfo;

        for (Object[] b: dailyReport) {
            Goods p = (Goods) b[0];
            Long amount = (Long) b[1];
            Long total = (Long) b[2];
            goodsInfo = new HashMap<>();

            goodsInfo.put("deliveryTime", p.getDeliveryTime());
            goodsInfo.put("purchasePrice", p.getPurchasePrice());
            goodsInfo.put("sellingPrice", p.getSellingPrice());
            goodsInfo.put("producer", p.getProducer());
            goodsInfo.put("goodsName", p.getGoodsName());
            goodsInfo.put("providerName", p.getProvider().getProviderName());
            goodsInfo.put("category", p.getProvider().getCategory());
            goodsInfo.put("goodsId", p.getGoodsId());

            goodsInfo.put("amount", amount);
            goodsInfo.put("total", total);

            goodsInfoList.add(goodsInfo);
        }

        response.put("results", goodsInfoList);

        return response;
    }

    @GetMapping("/api/goods/storage-report/")
    @ResponseBody
    public Map<String, Object> storageReport() {
        Map<String, Object> response = new HashMap<>();
        List<Object[]> dailyReport = goodsRepository.getStorageReport();

        ArrayList<Map<String, Object>> goodsInfoList = new ArrayList<>();

        Map<String, Object> goodsInfo;

        for (Object[] b: dailyReport) {
            Goods p = (Goods) b[0];
            Integer cellsId = (Integer) b[1];
            Long amount = (Long) b[2];
            goodsInfo = new HashMap<>();

            goodsInfo.put("goodsId", p.getGoodsId());
            goodsInfo.put("deliveryTime", p.getDeliveryTime());
            goodsInfo.put("purchasePrice", p.getPurchasePrice());
            goodsInfo.put("sellingPrice", p.getSellingPrice());
            goodsInfo.put("producer", p.getProducer());
            goodsInfo.put("goodsName", p.getGoodsName());
            goodsInfo.put("providerName", p.getProvider().getProviderName());
            goodsInfo.put("category", p.getProvider().getCategory());

            goodsInfo.put("cellsId", cellsId);
            goodsInfo.put("amount", amount);

            goodsInfoList.add(goodsInfo);
        }

        response.put("results", goodsInfoList);

        return response;
    }

    @GetMapping("/api/goods/finance-report/")
    @ResponseBody
    public Map<String, Object> financeReport(
                @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
    ) {
        Map<String, Object> response = new HashMap<>();
        List<Object[]> financeReport = goodsRepository.getFinanceReport(startDate, endDate);

        ArrayList<Map<String, Object>> resultList = new ArrayList<>();

        Map<String, Object> reportItem;

        for (Object[] b: financeReport) {
            reportItem = new HashMap<>();

            reportItem.put("date", b[0]);
            reportItem.put("income", b[1]);
            reportItem.put("expense", b[2]);
            reportItem.put("rejects", b[3]);
            reportItem.put("rowTotal", b[4]);

            resultList.add(reportItem);
        }

        response.put("results", resultList);

        return response;
    }
}
