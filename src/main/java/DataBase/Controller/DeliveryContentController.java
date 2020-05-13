package DataBase.Controller;

import DataBase.Domain.Delivery;
import DataBase.Domain.DeliveryContent;
import DataBase.Domain.Goods;
import DataBase.Repository.DeliveryContentRepository;
import DataBase.Repository.DeliveryRepository;
import DataBase.Repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class DeliveryContentController {
    @Autowired
    private DeliveryContentRepository deliveryContentRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping("/insert/deliveryContent")
    public String deliveryContent(Map<String, Object> model) {
        generateIterators(model);
        return "deliveryContent";
    }

    @PostMapping("/insert/deliveryContent/delete")
    public String deleteDeliveryContent(
            @RequestParam int deliveryContentId,
            Map<String, Object> model){
        deliveryContentRepository.deleteById(deliveryContentId);
        generateIterators(model);
        return "deliveryContent";
    }


    @PostMapping("/insert/deliveryContent")
    public String addDeliveryContent(
            @RequestParam int deliveryId,
            @RequestParam int amount,
            @RequestParam int goodsId,
            Map<String, Object> model){
        Delivery tempDelivery = deliveryRepository.findByDeliveryId(deliveryId);
        Goods tempGoods = goodsRepository.findByGoodsId(goodsId);
        DeliveryContent tempDeliveryContent = new DeliveryContent(
                tempDelivery, amount, tempGoods
        );
        deliveryContentRepository.save(tempDeliveryContent);

        generateIterators(model);
        return "deliveryContent";
    }


    private void generateIterators(Map<String, Object> model) {
        Iterable<DeliveryContent> deliveryContentIt = deliveryContentRepository.findAll();
        model.put("deliveryContent", deliveryContentIt);

        Iterable<Goods> goodsIt = goodsRepository.findAll();
        model.put("goods", goodsIt);

        Iterable<Delivery> deliveryIt = deliveryRepository.findAll();
        model.put("deliveries", deliveryIt);
    }
}
