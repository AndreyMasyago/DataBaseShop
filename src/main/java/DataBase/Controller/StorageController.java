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
import java.util.Map;
import java.util.Optional;

@RestController
public class StorageController {

    @Autowired
    private StorageRepository storageRepository;

    @GetMapping("/storage/")
    public Iterable<Storage> list() {
        return storageRepository.findAll();
    }

    @GetMapping("/storage/{id}")
    public ResponseEntity<Storage> retrieveCell(@PathVariable int id) {
        Optional<Storage> cell = storageRepository.findById(id);

        if (!cell.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cell.get());
    }

    @DeleteMapping("/storage/{id}")
    public void deleteCell(@PathVariable int id) {
        storageRepository.deleteById(id);
    }

    @PostMapping("/storage/")
    public ResponseEntity<Storage> createCell(@RequestBody Storage cell) {
        Storage savedCell = storageRepository.save(cell);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedCell.getCellsId()).toUri();

        return ResponseEntity.created(location).body(savedCell);
    }

    @PutMapping("/storage/{id}")
    public ResponseEntity<Storage> updateCell(@RequestBody Storage cell, @PathVariable int id) {

        Optional<Storage> cellOptional = storageRepository.findById(id);


        if (!cellOptional.isPresent())
            return ResponseEntity.notFound().build();

        cell.setCellsId(id);
        storageRepository.save(cell);

        return ResponseEntity.ok(cell);
    }
}
