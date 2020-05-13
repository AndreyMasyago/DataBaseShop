package DataBase.Repository;

import DataBase.Domain.StorageTransactions;
import org.springframework.data.repository.CrudRepository;

public interface StorageTransactionsRepository extends CrudRepository<StorageTransactions, Integer> {
    public StorageTransactions findByStorageTransactionId(int id);
    public void deleteById(int id);
}
