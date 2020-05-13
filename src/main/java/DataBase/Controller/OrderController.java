package DataBase.Controller;

import DataBase.Domain.OrderEntity;
import DataBase.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/insert/order")
    public String order(Map<String, Object> model) {
        generateIterators(model);
        return "order";
    }

    @PostMapping("/insert/order/delete")
    public String deleteOrder(
            @RequestParam int orderId,
            Map<String, Object> model
    ){
        orderRepository.deleteById(orderId);
        generateIterators(model);
        return "order";
    }

    private void generateIterators(Map<String, Object> model) {
        Iterable<OrderEntity> it = orderRepository.findAll();
        model.put("orders", it);
    }

    @PostMapping("/insert/order")
    public String addOrder(@RequestParam Date orderDate, Map<String, Object> model){
        OrderEntity tempOrderEntity = new OrderEntity(orderDate);
        orderRepository.save(tempOrderEntity);

        generateIterators(model);

        return "order";
    }
}
