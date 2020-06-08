package DataBase.Repository;

import DataBase.Domain.StorageTransactions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StorageTransactionsRepository extends CrudRepository<StorageTransactions, Integer> {
    public StorageTransactions findByStorageTransactionId(int id);
    public void deleteById(int id);

    // 4
    @Query(
            "SELECT g.goodsId, storage.cellsId, c.goodsName, g.size, SUM(st.amount), SUM(st.amount) * g.size " +
                    "FROM StorageTransactions st " +
                    "INNER JOIN st.goods g " +
                    "INNER JOIN g.catalog c " +
                    "INNER JOIN st.storage storage " +
                    "GROUP BY g.goodsId, c.goodsName, storage.cellsId"
    )
    public List<Object[]> getStoredGoods();
}
