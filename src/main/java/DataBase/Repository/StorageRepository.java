package DataBase.Repository;

import DataBase.Domain.Storage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StorageRepository extends CrudRepository<Storage, Integer> {
    public Storage findByCellsId(Integer cellsId);
    public void deleteById(int id);

    // 15.1
    @Query(
            "SELECT storage, storage.cellsSize - COALESCE(SUM(st.amount * goods.size), 0) " +
                    "FROM Storage storage " +
                    "LEFT JOIN storage.storageTransactionsList st " +
                    "LEFT JOIN st.goods goods " +
                    "GROUP BY storage"
    )
    public List<Object[]> getFreeSpace();

    // 15.2
    @Query(
            "SELECT COALESCE(SUM(st.amount * goods.size), 0) " +
                    "FROM Storage storage " +
                    "LEFT JOIN storage.storageTransactionsList st " +
                    "LEFT JOIN st.goods goods "
    )
    public Long getUsedSpaceSum();

    // 15.3
    @Query(
            "SELECT COALESCE(SUM (storage.cellsSize), 0) " +
                    "FROM Storage storage "
    )
    public Long getTotalSpaceSum();
}
