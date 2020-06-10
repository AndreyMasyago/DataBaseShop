package DataBase.Controller;

import DataBase.Domain.Delivery;
import DataBase.Domain.OrderEntity;
import DataBase.Domain.Provider;
import DataBase.Repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
public class ProviderController {

    @Autowired
    private ProviderRepository providerRepository;

    @GetMapping("/api/provider/")
    public Iterable<Provider> list() {
        return providerRepository.findAll();
    }

    @GetMapping("/api/provider/{id}")
    public ResponseEntity<Provider> retrieveProvider(@PathVariable int id) {
        Optional<Provider> provider = providerRepository.findById(id);

        if (!provider.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(provider.get());
    }

    @DeleteMapping("/api/provider/{id}")
    public void deleteProvider(@PathVariable int id) {
        providerRepository.deleteById(id);
    }

    @PostMapping("/api/provider/")
    public ResponseEntity<Provider> createProvider(@RequestBody Provider provider) {
        Provider savedProvider = providerRepository.save(provider);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProvider.getProviderId()).toUri();

        return ResponseEntity.created(location).body(savedProvider);
    }

    @PutMapping("/api/provider/{id}")
    public ResponseEntity<Provider> updateProvider(@RequestBody Provider input, @PathVariable int id) {

        Optional<Provider> stored = providerRepository.findById(id);

        if (!stored.isPresent())
            return ResponseEntity.notFound().build();

        Provider updated = stored.get();
        updated.setProviderName(input.getProviderName());
        updated.setCategory(input.getCategory());
        providerRepository.save(updated);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/api/provider/delivered-more-than-count/")
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
