package DataBase.Controller;

import DataBase.Domain.*;
import DataBase.Repository.GoodsRepository;
import DataBase.Repository.OrderContentRepository;
import DataBase.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/api/orderContent/")
    public Iterable<OrderContent> list() {
        return orderContentRepository.findAll();
    }

    @GetMapping("/api/orderContent/{id}")
    public ResponseEntity<OrderContent> retrieveOrderContent(@PathVariable int id) {
        Optional<OrderContent> orderContent = orderContentRepository.findById(id);

        if (!orderContent.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderContent.get());
    }

    @DeleteMapping("/api/orderContent/{id}")
    public void deleteOrderContent(@PathVariable int id) {
        orderContentRepository.deleteById(id);
    }

    @PostMapping("/api/orderContent/")
    public ResponseEntity<OrderContent> createOrderContent(@RequestBody OrderContent orderContent) {
        OrderContent savedOrderContent = orderContentRepository.save(orderContent);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedOrderContent.getOrderContentId()).toUri();

        return ResponseEntity.created(location).body(savedOrderContent);
    }

    @PutMapping("/api/orderContent/{id}")
    public ResponseEntity<OrderContent> updateOrderContent(@RequestBody OrderContent input, @PathVariable int id) {

        Optional<OrderContent> stored = orderContentRepository.findById(id);

        if (!stored.isPresent())
            return ResponseEntity.notFound().build();

        OrderContent updated = stored.get();
        updated.setAmount(input.getAmount());
        updated.setGoods(input.getGoods());
        updated.setOrderEntity(input.getOrderEntity());
        orderContentRepository.save(updated);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/api/order-content/order-content-by-date/")
    public Map<String, Object> orderContentFilteredByDate(
            @RequestParam Integer goodsSearch,
            @RequestParam Integer amountLimit,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date orderDateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date orderDateTo
    ) {
        Map<String, Object> response = new HashMap<>();
        List<OrderContent> orderContents = orderContentRepository.getOrderContentFilteredByDate(
                goodsSearch,
                orderDateFrom,
                orderDateTo,
                amountLimit
                );
        Long count = orderContentRepository.countOrderContentFilteredByDate(
                goodsSearch,
                orderDateFrom,
                orderDateTo,
                amountLimit
        );

        response.put("results", orderContents);
        response.put("count", count);

        return response;
    }

    @GetMapping("/api/order-content/monthly-average-sales/")
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

    @GetMapping("/api/order-content/provider-income-stats/")
    public Map<String, Object> providerIncomeStats(
            @RequestParam Integer providerSearch,
            @Param("orderDateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date orderDateFrom,
            @Param("orderDateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date orderDateTo
    ) {
        List<Object[]> incomeStats = orderContentRepository.getProviderIncomeStats(
                providerSearch, orderDateFrom, orderDateTo
        );

        Map<String, Object> response = new HashMap<>();
        response.put("shareOfSale", incomeStats.get(0)[0]);
        response.put("shareOfAmount", incomeStats.get(0)[1]);

        return response;
    }

    @GetMapping("/api/order-content/overhead/")
    public Map<String, Object> overhead() {
        Float overhead = orderContentRepository.getOverhead();

        Map<String, Object> response = new HashMap<>();
        response.put("overhead", overhead);

        return response;
    }

    @GetMapping("/api/order-content/monthly-average-sales-2/")
    public Map<String, Object> monthlyAverageSales2(
            @RequestParam Optional<Integer> detailId,
            @RequestParam Optional<Integer> month,
            @RequestParam Optional<Long> amountFrom,
            @RequestParam Optional<Long> amountTo,
            @RequestParam Optional<String> producerSearch,
            @RequestParam Optional<String> orderBy,
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<Integer> page
    ) {
        List<Object[]> sales = orderContentRepository.getMonthlyAverageSales2(
                detailId,
                month,
                amountFrom,
                amountTo,
                producerSearch,
                orderBy,
                pageSize,
                page
        );

        Integer count = orderContentRepository.getMonthlyAverageSalesCount(
                detailId,
                month,
                amountFrom,
                amountTo,
                producerSearch
        );

        ArrayList<Map<String, Object>> salesList = new ArrayList<>();

        Map<String, Object> salesInfo;

        for (Object[] o : sales) {
            Goods g = (Goods) o[0];
            Integer m = (Integer) o[1];
            Long amount = (Long) o[2];

            salesInfo = new HashMap<>();

            salesInfo.put("goodsId", g.getGoodsId());
            salesInfo.put("detailId", g.getCatalog().getDetailId());
            salesInfo.put("goodsName", g.getGoodsName());
            salesInfo.put("producer", g.getProducer());

            salesInfo.put("month", m);
            salesInfo.put("amount", amount);

            salesList.add(salesInfo);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("results", salesList);
        response.put("count", count);

        return response;
    }
}
