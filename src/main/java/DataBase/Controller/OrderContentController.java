package DataBase.Controller;

import DataBase.Domain.Goods;
import DataBase.Domain.OrderContent;
import DataBase.Domain.OrderEntity;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.OrderContentRepository;
import DataBase.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class OrderContentController {
    @Autowired
    private OrderContentRepository orderContentRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/insert/orderContent")
    public String orderContent(Map<String, Object> model) {
        generateIterators(model);
        return "orderContent";
    }

    @PostMapping("/insert/orderContent/delete")
    public String deleteOrderContent(
            @RequestParam int orderContentId,
            Map<String, Object> model
    ){
        orderContentRepository.deleteById(orderContentId);
        generateIterators(model);
        return "orderContent";
    }

    @PostMapping("/insert/orderContent")
    public String addOrderContent(
            @RequestParam int goodsId,
            @RequestParam int amount,
            @RequestParam int orderId,
            Map<String, Object> model
    ){
        Goods tempGoods = goodsRepository.findByGoodsId(goodsId);
        OrderEntity tempOrder = orderRepository.findByOrderId(orderId);
        OrderContent tempOrderContent = new OrderContent(
                tempGoods, amount, tempOrder
        );
        orderContentRepository.save(tempOrderContent);

        generateIterators(model);
        return "orderContent";
    }

    private void generateIterators(Map<String, Object> model) {
        Iterable<OrderContent> orderContentIt = orderContentRepository.findAll();
        model.put("orderContent", orderContentIt);

        Iterable<Goods> goodsIt = goodsRepository.findAll();
        model.put("goods", goodsIt);

        Iterable<OrderEntity> orderEntityIt = orderRepository.findAll();
        model.put("order", orderEntityIt);
    }
}
