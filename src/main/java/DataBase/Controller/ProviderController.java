package DataBase.Controller;

import DataBase.Domain.Provider;
import DataBase.Repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ProviderController {

    @Autowired
    private ProviderRepository providerRepository;

    @GetMapping("/insert/provider")
    public String provider(Map<String, Object> model) {
        Iterable<Provider> it = providerRepository.findAll();
        model.put("providers", it);

        return "provider";
    }

    @PostMapping("/insert/provider")
    public String addProvider(
            @RequestParam String providerName,
            @RequestParam String category,
            Map<String, Object> model) {

        Provider tempProvider = new Provider(providerName, category);
        providerRepository.save(tempProvider);

        Iterable<Provider> it = providerRepository.findAll();
        model.put("providers", it);

        return "provider";
    }
}