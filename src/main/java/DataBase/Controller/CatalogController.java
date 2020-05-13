package DataBase.Controller;

import DataBase.Domain.Catalog;
import DataBase.Repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class CatalogController {

    @Autowired
    private CatalogRepository catalogRepository;

    @GetMapping("/insert/catalog")
    public String catalog(Map<String, Object> model){
        generateIterators(model);

        return "catalog";
    }

    @PostMapping("/insert/catalog/delete")
    public String deleteCatalog(
            @RequestParam int detailId,
            Map<String, Object> model
    ){
        catalogRepository.deleteById(detailId);
        generateIterators(model);
        return "catalog";
    }

    @PostMapping("/insert/catalog")
    public String addDetail(@RequestParam String goodsName, Map<String, Object> model) {
        Catalog tempCatalog = new Catalog(goodsName);
        catalogRepository.save(tempCatalog);

        generateIterators(model);

        return "catalog";
    }

    private void generateIterators(Map<String, Object> model) {
        Iterable<Catalog> it = catalogRepository.findAll();
        model.put("details", it);
    }
}
