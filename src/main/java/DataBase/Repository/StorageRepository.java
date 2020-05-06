package DataBase.Repository;

import DataBase.Domain.Storage;
import org.springframework.data.repository.CrudRepository;

public interface StorageRepository extends CrudRepository<Storage, Integer> {
    public Storage findByCellsId(Integer cellsId);
}
