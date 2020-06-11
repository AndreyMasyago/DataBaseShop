package DataBase.Controller;

import DataBase.Domain.Reject;
import DataBase.Domain.Storage;
import DataBase.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class StorageController {

    @Autowired
    private StorageRepository storageRepository;

    @GetMapping("/api/storage/")
    public Iterable<Storage> list() {
        return storageRepository.findAll();
    }

    @GetMapping("/api/storage/{id}")
    public ResponseEntity<Storage> retrieveCell(@PathVariable int id) {
        Optional<Storage> cell = storageRepository.findById(id);

        if (!cell.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cell.get());
    }

    @DeleteMapping("/api/storage/{id}")
    public void deleteCell(@PathVariable int id) {
        storageRepository.deleteById(id);
    }

    @PostMapping("/api/storage/")
    public ResponseEntity<Storage> createCell(@RequestBody Storage cell) {
        Storage savedCell = storageRepository.save(cell);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCell.getCellsId()).toUri();

        return ResponseEntity.created(location).body(savedCell);
    }

    @PutMapping("/api/storage/{id}")
    public ResponseEntity<Storage> updateCell(@RequestBody Storage input, @PathVariable int id) {

        Optional<Storage> stored = storageRepository.findById(id);

        if (!stored.isPresent())
            return ResponseEntity.notFound().build();

        Storage updated = stored.get();
        updated.setCellsSize(input.getCellsSize());
        storageRepository.save(updated);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/api/storage/free-space/")
    public Map<String, Object> getStorageFreeSpace() {
        Map<String, Object> response = new HashMap<>();

        response.put("results",storageRepository.getFreeSpace());
        response.put("freeSpace", storageRepository.getTotalSpaceSum() - storageRepository.getUsedSpaceSum());

        return response;
    }
}
