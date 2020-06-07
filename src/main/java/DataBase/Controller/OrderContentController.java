package DataBase.Controller;

import DataBase.Domain.Goods;
import DataBase.Domain.OrderContent;
import DataBase.Domain.OrderEntity;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.OrderContentRepository;
import DataBase.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        model.put("currentId", 0);
        return "orderContent";
    }

    @PostMapping("/insert/orderContent/delete")
    public String deleteOrderContent(
            @RequestParam int orderContentId,
            Map<String, Object> model
    ){
        orderContentRepository.deleteById(orderContentId);
        generateIterators(model);
        model.put("currentId", 0);
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
        model.put("currentId", tempOrderContent.getOrderContentId());
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

    @GetMapping(value="/order-content/order-content-by-date/", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> orderContentFilteredByDate(@RequestParam String goodsSearch, @RequestParam Integer amountLimit) {
        Map<String, Object> response = new HashMap<>();
        List<OrderContent> orderContents = orderContentRepository.getOrderContentFilteredByDate(goodsSearch, amountLimit);
        Long count = orderContentRepository.countOrderContentFilteredByDate(goodsSearch, amountLimit);

        ArrayList<Map<String, Object>> orderContentList = new ArrayList<>();

        Map<String, Object> orderContentInfo;

        for (OrderContent oc : orderContents) {
            orderContentInfo = new HashMap<>();

            orderContentInfo.put("goodsName", oc.getGoods().getGoodsName());
            orderContentInfo.put("orderDate", oc.getOrderEntity().getOrderDate());
            orderContentInfo.put("amount", oc.getAmount());

            orderContentList.add(orderContentInfo);
        }

        response.put("results", orderContentList);
        response.put("count", count);

        return response;
    }
}
