package DataBase.Controller;

import DataBase.Domain.Catalog;
import DataBase.Domain.Goods;
import DataBase.Domain.Provider;
import DataBase.Repository.CatalogRepository;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/goods/")
    public Iterable<Goods> list() {
        return goodsRepository.findAll();
    }

    @GetMapping("/goods/{id}")
    public ResponseEntity<Goods> retrieveGoods(@PathVariable int id) {
        Optional<Goods> goods = goodsRepository.findById(id);

        if (!goods.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goods.get());
    }

    @DeleteMapping("/goods/{id}")
    public void deleteGoods(@PathVariable int id) {
        goodsRepository.deleteById(id);
    }

    @PostMapping("/goods")
    public ResponseEntity<Goods> createGoods(@RequestBody Goods goods) {
        Goods savedGoods = goodsRepository.save(goods);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedGoods.getGoodsId()).toUri();

        return ResponseEntity.created(location).body(savedGoods);
    }

    @PutMapping("/goods/{id}")
    public ResponseEntity<Goods> updateGoods(@RequestBody Goods goods, @PathVariable int id) {

        Optional<Goods> goodsOptional = goodsRepository.findById(id);

        if (!goodsOptional.isPresent())
            return ResponseEntity.notFound().build();

        goods.setGoodsId(id);
        goodsRepository.save(goods);

        return ResponseEntity.ok(goods);
    }

    @GetMapping(value="/goods/goods-details/", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> goodsDetails(@RequestParam String goodsSearch) {
        Map<String, Object> response = new HashMap<>();
        List<Goods> goods = goodsRepository.getGoodsInfo(goodsSearch);
        Long count = goodsRepository.countGoodsInfo(goodsSearch);

        ArrayList<Map<String, Object>> goodsInfoList = new ArrayList<>();

        Map<String, Object> goodsInfo;

        for (Goods p : goods) {
            goodsInfo = new HashMap<>();

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

    @GetMapping(value="/goods/bestsellers/", produces=MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value="/goods/rejects/", produces=MediaType.APPLICATION_JSON_VALUE)
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

            goodsInfo.put("producer", g.getProducer());
            goodsInfo.put("goodsName", g.getGoodsName());
            goodsInfo.put("providerName", g.getProvider().getProviderName());
            goodsInfo.put("amount", amount);

            rejectsList.add(goodsInfo);
        }

        response.put("results", rejectsList);

        return response;
    }

    @GetMapping(value="/goods/reject-providers/", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> rejectProviders() {
        Map<String, Object> response = new HashMap<>();
        List<String> rejects = goodsRepository.getRejectProviders();

        response.put("results", rejects);

        return response;
    }
}
