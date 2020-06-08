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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private ProviderRepository providerRepository;

    @GetMapping("/insert/goods")
    public String goods(Map<String, Object> model) {
        generateIterators(model);
        model.put("currentId", 0);
        return "goods";
    }

    @PostMapping("/insert/goods/delete")
    public String deleteGoods(
            @RequestParam int goodsId,
            Map<String, Object> model
    ){
        goodsRepository.deleteById(goodsId);
        generateIterators(model);

        return "goods";
    }

    @PostMapping("/insert/goods")
    public String addGoods(
            @RequestParam int detailId,
            @RequestParam int size,
            @RequestParam int deliveryTime,
            @RequestParam int purchasePrice,
            @RequestParam int sellingPrice,
            @RequestParam String producer,
            @RequestParam int providerId,
            Map<String, Object> model){
        Catalog tempCatalog = catalogRepository.findByDetailId(detailId);
        Provider tempProvider = providerRepository.findByProviderId(providerId);
        Goods tempGoods = new Goods(
                tempCatalog, size, deliveryTime, purchasePrice,
                sellingPrice, producer, tempProvider);
        goodsRepository.save(tempGoods);

        generateIterators(model);
        model.put("currentId", tempGoods.getGoodsId());
        return "goods";
    }

    private void generateIterators(Map<String, Object> model) {
        Iterable<Goods> goodsIt = goodsRepository.findAll();
        model.put("goods", goodsIt);

        Iterable<Catalog> catalogIt = catalogRepository.findAll();
        model.put("catalog", catalogIt);

        Iterable<Provider> providerIt = providerRepository.findAll();
        model.put("providers", providerIt);
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
}
