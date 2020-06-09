package DataBase.Controller;

import DataBase.Domain.DeliveryContent;
import DataBase.Domain.Goods;
import DataBase.Domain.OrderContent;
import DataBase.Domain.OrderEntity;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.OrderContentRepository;
import DataBase.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
public class OrderContentController {
    @Autowired
    private OrderContentRepository orderContentRepository;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/orderContent/")
    public Iterable<OrderContent> list() {
        return orderContentRepository.findAll();
    }

    @GetMapping("/orderContent/{id}")
    public ResponseEntity<OrderContent> retrieveOrderContent(@PathVariable int id) {
        Optional<OrderContent> orderContent = orderContentRepository.findById(id);

        if (!orderContent.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderContent.get());
    }

    @DeleteMapping("/orderContent/{id}")
    public void deleteOrderContent(@PathVariable int id) {
        orderContentRepository.deleteById(id);
    }

    @PostMapping("/orderContent/")
    public ResponseEntity<OrderContent> createOrderContent(@RequestBody OrderContent orderContent) {
        OrderContent savedOrderContent = orderContentRepository.save(orderContent);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedOrderContent.getOrderContentId()).toUri();

        return ResponseEntity.created(location).body(savedOrderContent);
    }

    @PutMapping("/orderContent/{id}")
    public ResponseEntity<OrderContent> updateOrderContent(@RequestBody OrderContent orderContent, @PathVariable int id) {

        Optional<OrderContent> orderContentOptional = orderContentRepository.findById(id);

        if (!orderContentOptional.isPresent())
            return ResponseEntity.notFound().build();

        orderContent.setOrderContentId(id);
        orderContentRepository.save(orderContent);

        return ResponseEntity.ok(orderContent);
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

    @GetMapping(value="/order-content/monthly-average-sales/", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> monthlyAverageSales(@RequestParam String goodsSearch) {
        Map<String, Object> response = new HashMap<>();
        List<Object[]> sales = orderContentRepository.getMonthlyAverageSales(goodsSearch);

        ArrayList<Map<String, Object>> salesList = new ArrayList<>();

        Map<String, Object> salesInfo;

        for (Object[] o : sales) {
            Goods g = (Goods) o[0];
            Integer month = (Integer) o[1];
            Long amount = (Long) o[2];

            salesInfo = new HashMap<>();

            salesInfo.put("goodsId", g.getGoodsId());
            salesInfo.put("goodsName", g.getGoodsName());
            salesInfo.put("producer", g.getProducer());

            salesInfo.put("month", month);
            salesInfo.put("amount", amount);

            salesList.add(salesInfo);
        }

        response.put("results", salesList);

        return response;
    }

    @GetMapping(value="/order-content/provider-income-stats/", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> providerIncomeStats(@RequestParam String providerSearch) {
        Object[] incomeStats = orderContentRepository.getProviderIncomeStats(providerSearch);

        Map<String, Object> response = new HashMap<>();
        response.put("shareOfSale", incomeStats[0]);
        response.put("shareOfAmount", incomeStats[1]);

        return response;
    }
}
