package DataBase.Controller;

import DataBase.Domain.Delivery;
import DataBase.Repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Map;

@Controller
public class DeliveryController {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping("/insert/delivery")
    public String delivery(Map<String, Object> model) {
        generateIterators(model);

        return "delivery";
    }

    private void generateIterators(Map<String, Object> model) {
        Iterable<Delivery> it = deliveryRepository.findAll();
        model.put("deliveries", it);
    }

    @PostMapping("/insert/delivery/delete")
    public String deleteDelivery(
            @RequestParam int deliveryId,
            Map<String, Object> model
    ){
        deliveryRepository.deleteById(deliveryId);
        generateIterators(model);
        return "delivery";
    }

    @PostMapping("/insert/delivery")
    public String addDelivery(@RequestParam Date deliveryDate, Map<String, Object> model) {
        Delivery tempDelivery = new Delivery(deliveryDate);
        deliveryRepository.save(tempDelivery);

        generateIterators(model);

        return "delivery";
    }
}
