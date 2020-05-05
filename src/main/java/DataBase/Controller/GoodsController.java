package DataBase.Controller;

import DataBase.Domain.Catalog;
import DataBase.Domain.Goods;
import DataBase.Domain.Provider;
import DataBase.Repository.CatalogRepository;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
