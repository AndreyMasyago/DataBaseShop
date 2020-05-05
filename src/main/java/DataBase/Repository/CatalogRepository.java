package DataBase.Repository;

import DataBase.Domain.Catalog;
import org.springframework.data.repository.CrudRepository;

public interface CatalogRepository extends CrudRepository<Catalog, Integer> {
    public Catalog findByDetailId(int detailId);
}
