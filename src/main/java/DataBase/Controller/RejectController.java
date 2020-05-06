package DataBase.Controller;

import DataBase.Domain.Goods;
import DataBase.Domain.OrderEntity;
import DataBase.Domain.Reject;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.OrderRepository;
import DataBase.Repository.RejectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RejectController {
    @Autowired
    private RejectRepository rejectRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/insert/reject")
    public String reject(Map<String, Object> model) {
        generateIterators(model);
        return "reject";
    }

    @PostMapping("/insert/reject")
    public String addReject(
            @RequestParam int goodsId,
            @RequestParam int amount,
            @RequestParam int orderId,
            Map<String, Object> model
    ){
        Goods tempGoods = goodsRepository.findByGoodsId(goodsId);
        OrderEntity tempOrder = orderRepository.findByOrderId(orderId);
        Reject tempReject = new Reject(
                tempGoods, amount, tempOrder
        );
        rejectRepository.save(tempReject);

        generateIterators(model);
        return "reject";
    }

    private void generateIterators(Map<String, Object> model) {
        Iterable<Reject> rejectIt = rejectRepository.findAll();
        model.put("reject", rejectIt);

        Iterable<Goods> goodsIt = goodsRepository.findAll();
        model.put("goods", goodsIt);

        Iterable<OrderEntity> orderEntityIt = orderRepository.findAll();
        model.put("order", orderEntityIt);
    }
}
