package DataBase.Controller;

import DataBase.Domain.Catalog;
import DataBase.Domain.Goods;
import DataBase.Repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
public class CatalogController {

    @Autowired
    private CatalogRepository catalogRepository;

    @GetMapping("/catalog/")
    public Iterable<Catalog> list() {
        return catalogRepository.findAll();
    }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<Catalog> retrieveDetails(@PathVariable int id) {
        Optional<Catalog> detail = catalogRepository.findById(id);

        if (!detail.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detail.get());
    }

    @DeleteMapping("/catalog/{id}")
    public void deleteDetail(@PathVariable int id) {
        catalogRepository.deleteById(id);
    }

    @PostMapping("/catalog/")
    public ResponseEntity<Catalog> createDetail(@RequestBody Catalog detail) {
        Catalog savedDetail = catalogRepository.save(detail);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDetail.getDetailId()).toUri();

        return ResponseEntity.created(location).body(savedDetail);
    }

    @PutMapping("/catalog/{id}")
    public ResponseEntity<Catalog> updateCatalog(@RequestBody Catalog input, @PathVariable int id) {

        Optional<Catalog> stored = catalogRepository.findById(id);

        if (!stored.isPresent())
            return ResponseEntity.notFound().build();

        Catalog updated = stored.get();
        updated.setGoodsName(input.getGoodsName());
        catalogRepository.save(updated);

        return ResponseEntity.ok(updated);
    }
}
