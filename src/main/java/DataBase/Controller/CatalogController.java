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
    public ResponseEntity<Object> createDetail(@RequestBody Catalog detail) {
        Catalog savedDetail = catalogRepository.save(detail);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDetail.getDetailId()).toUri();

        return ResponseEntity.created(location).body(savedDetail);
    }

    @PutMapping("/catalog/{id}")
    public ResponseEntity<Object> updateCatalog(@RequestBody Catalog detail, @PathVariable int id) {

        Optional<Catalog> detailOptional = catalogRepository.findById(id);

        if (!detailOptional.isPresent())
            return ResponseEntity.notFound().build();

        detail.setDetailId(id);
        catalogRepository.save(detail);

        return ResponseEntity.ok(detail);
    }
}
