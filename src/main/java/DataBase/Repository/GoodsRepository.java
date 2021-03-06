package DataBase.Repository;

import DataBase.Domain.Goods;
import DataBase.Domain.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, Integer>  {
    public Goods findByGoodsId(Integer goodsId);
    public void deleteById(int id);

    // 2.1
    @Query(
            "SELECT g FROM Goods g INNER JOIN FETCH g.catalog c INNER JOIN FETCH g.provider p WHERE c.detailId= :goodsSearch"
    )
    public List<Goods> getGoodsInfo(@Param("goodsSearch") Integer goodsSearch);

    // 2.2
    @Query(
            "SELECT COUNT(g.goodsId) FROM Goods g INNER JOIN g.catalog c WHERE c.detailId= :goodsSearch"
    )
    public Long countGoodsInfo(@Param("goodsSearch") Integer goodsSearch);

    // 5
    @Query(
            "SELECT g, SUM(orderContent.amount) " +
            "FROM Goods g " +
                    "INNER JOIN g.catalog c " +
                    "INNER JOIN g.provider p " +
                    "INNER JOIN g.orderContentList orderContent " +
            "GROUP BY g.goodsId " +
            "ORDER BY SUM(orderContent.amount) DESC "
    )
    public Page<Object[]> getBestSellers(Pageable pageable);

    // 10.1
    @Query(
            "SELECT g, SUM(reject.amount) " +
            "FROM Goods g " +
                    "INNER JOIN FETCH g.catalog c " +
                    "INNER JOIN FETCH g.provider p " +
                    "INNER JOIN g.rejectList reject " +
                    "INNER JOIN reject.orderEntity orderEntity " +
            "GROUP BY g.goodsId, c.detailId, p.providerId " +
            "ORDER BY SUM(reject.amount) DESC "
    )
    public List<Object[]> getRejects();

    // 10.2
    @Query(
            "SELECT DISTINCT p " +
            "FROM Goods g " +
                    "INNER JOIN g.provider p " +
                    "INNER JOIN g.rejectList reject " +
                    "INNER JOIN reject.orderEntity orderEntity "
    )
    public List<Provider> getRejectProviders();

    // 11
    @Query(
            "SELECT g, SUM(orderContent.amount), SUM(orderContent.amount * (g.sellingPrice - g.purchasePrice)) " +
            "FROM Goods g " +
                    "INNER JOIN FETCH g.catalog c " +
                    "INNER JOIN FETCH g.provider p " +
                    "INNER JOIN g.orderContentList orderContent " +
                    "INNER JOIN orderContent.orderEntity orderEntity " +
            "WHERE orderEntity.orderDate = :reportDate " +
            "GROUP BY g.goodsId, c.detailId, p.providerId "
    )
    public List<Object[]> getDailyReport(@Param("reportDate") Date reportDate);

    // 13
    @Query(
            "SELECT g, storage.cellsId, COALESCE(SUM(storageTransaction.amount), 0) " +
            "FROM Goods g " +
                    "INNER JOIN g.catalog c " +
                    "LEFT JOIN g.storageTransactionsList storageTransaction " +
                    "INNER JOIN storageTransaction.storage storage " +
            "GROUP BY g.goodsId, storage.cellsId, c.detailId"
    )
    public List<Object[]> getStorageReport();

    // 12
    @Query(value="SELECT * FROM finance_report(:startDate, :endDate)", nativeQuery=true)
    List<Object[]> getFinanceReport(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
