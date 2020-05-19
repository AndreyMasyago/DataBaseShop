package DataBase.Controller;

import DataBase.Domain.Storage;
import DataBase.Repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class StorageController {

    @Autowired
    private StorageRepository storageRepository;

    @GetMapping("/insert/storage")
    public String storage(Map<String, Object> model) {
        generateIterators(model);

        model.put("currentId", 0);
        return "storage";
    }

    @PostMapping("/insert/storage/delete")
    public String deleteStorage(
            @RequestParam int storageId,
            Map<String, Object> model
    ){
        storageRepository.deleteById(storageId);
        generateIterators(model);
        model.put("currentId", 0);
        return "storage";
    }

    @PostMapping("/insert/storage")
    public String addCell(
            @RequestParam int cellsSize,
            Map<String, Object> model
    ) {

        Storage tempStorage = new Storage(cellsSize);
        storageRepository.save(tempStorage);

        generateIterators(model);
        model.put("currentId", tempStorage.getCellsId());

        return "storage";
    }

    private void generateIterators(Map<String, Object> model) {
        Iterable<Storage> it = storageRepository.findAll();
        model.put("cells", it);
    }
}
