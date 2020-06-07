package DataBase.Controller;

import DataBase.Domain.Provider;
import DataBase.Repository.ProviderRepository;
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
public class ProviderController {

    @Autowired
    private ProviderRepository providerRepository;

    @GetMapping("/insert/provider")
    public String provider(Map<String, Object> model) {
        generateIterators(model);
        model.put("currentId", 0);

        return "provider";
    }

    private void generateIterators(Map<String, Object> model) {
        Iterable<Provider> it = providerRepository.findAll();
        model.put("providers", it);
    }

    @PostMapping("/insert/provider/delete")
    public String deleteProvider(
            @RequestParam int providerId,
            Map<String, Object> model
    ){
        providerRepository.deleteById(providerId);
        generateIterators(model);
        model.put("currentId", 0);
        return "provider";
    }

    @PostMapping("/insert/provider")
    public String addProvider(
            @RequestParam String providerName,
            @RequestParam String category,
            Map<String, Object> model) {

        Provider tempProvider = new Provider(providerName, category);
        providerRepository.save(tempProvider);

        generateIterators(model);

        model.put("currentId", tempProvider.getProviderId());
        return "provider";
    }

    @GetMapping(value="/provider/delivered-more-than-count/", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> deliveredMoreThanCount(
            @RequestParam String goodsSearch,
            @RequestParam String categorySearch,
            @RequestParam Long amountLimit,
            Map<String, Object> model) {

        List<Provider> providers = providerRepository.findDeliveredMoreThanCount(goodsSearch, categorySearch, amountLimit);
        Long count = providerRepository.countDeliveredMoreThanCount(goodsSearch, categorySearch, amountLimit);

        ArrayList<String> providerNames = new ArrayList<>();

        for (Provider p : providers) {
            providerNames.add(p.getProviderName());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("results", providerNames);
        response.put("count", count);

        return response;
    }
}
