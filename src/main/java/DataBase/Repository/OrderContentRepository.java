package DataBase.Repository;

import DataBase.Domain.Goods;
import DataBase.Domain.OrderContent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderContentRepository extends CrudRepository <OrderContent, Integer> {
    public OrderContent findByOrderContentId(int id);
    public void deleteById(int id);

    // 3.1
    @Query(
            "SELECT orderContent " +
            "FROM OrderContent orderContent " +
                    "INNER JOIN FETCH orderContent.orderEntity orderEntity " +
                    "INNER JOIN FETCH orderContent.goods goods " +
                    "INNER JOIN FETCH goods.catalog c " +
            "WHERE c.detailId = :goodsSearch " +
                    "AND orderEntity.orderDate BETWEEN :orderDateFrom AND :orderDateTo " +
                    "AND orderContent.amount > :amountLimit"
    )
    public List<OrderContent> getOrderContentFilteredByDate(
            @Param("goodsSearch") Integer goodsSearch,
            @Param("orderDateFrom") Date orderDateFrom,
            @Param("orderDateTo") Date orderDateTo,
            @Param("amountLimit") Integer amountLimit
    );

    // 3.2
    @Query(
            "SELECT COUNT(orderContent) " +
            "FROM OrderContent orderContent " +
                    "INNER JOIN orderContent.orderEntity orderEntity " +
                    "INNER JOIN orderContent.goods goods " +
                    "INNER JOIN goods.catalog c " +
            "WHERE c.detailId= :goodsSearch " +
                    "AND orderContent.amount > :amountLimit " +
                    "AND orderEntity.orderDate BETWEEN :orderDateFrom AND :orderDateTo "
    )
    public Long countOrderContentFilteredByDate(
            @Param("goodsSearch") Integer goodsSearch,
            @Param("orderDateFrom") Date orderDateFrom,
            @Param("orderDateTo") Date orderDateTo,
            @Param("amountLimit") Integer amountLimit
    );

    // 6
    @Query(
            "SELECT goods, EXTRACT(MONTH FROM orderEntity.orderDate), SUM(orderContent.amount) " +
            "FROM OrderContent orderContent " +
                    "INNER JOIN orderContent.orderEntity orderEntity " +
                    "INNER JOIN orderContent.goods goods " +
                    "INNER JOIN goods.catalog c " +
            "WHERE c.goodsName LIKE CONCAT('%', :goodsSearch, '%') " +
            "GROUP BY goods.goodsId, EXTRACT(MONTH FROM orderEntity.orderDate) " +
            "ORDER BY EXTRACT(MONTH FROM orderEntity.orderDate), goods.goodsId"
    )
    List<Object[]> getMonthlyAverageSales(@Param("goodsSearch") String goodsSearch);

    // 7
    @Query(
            "SELECT " +
                    "CAST (SUM( " +
                        "CASE " +
                        "WHEN provider.providerId= :providerSearch THEN (orderContent.amount * (goods.sellingPrice - goods.purchasePrice)) " +
                        "ELSE 0 END" +
                    ") as float) / SUM(orderContent.amount * (goods.sellingPrice - goods.purchasePrice)), " +
                    "CAST (SUM( " +
                        "CASE " +
                        "WHEN provider.providerId= :providerSearch THEN orderContent.amount " +
                        "ELSE 0 END" +
                    ") as float) / SUM(orderContent.amount) " +
            "FROM OrderContent orderContent " +
                    "INNER JOIN orderContent.orderEntity orderEntity " +
                    "INNER JOIN orderContent.goods goods " +
                    "INNER JOIN goods.provider provider"
    )
    List<Object[]> getProviderIncomeStats(@Param("providerSearch") Integer providerSearch);

    // 8
    @Query(
            "SELECT " +
                    "CAST (SUM(orderContent.amount * goods.purchasePrice) as float)" +
                    " / " +
                    "SUM(orderContent.amount * goods.sellingPrice) " +
            "FROM OrderContent orderContent " +
                    "INNER JOIN orderContent.orderEntity orderEntity " +
                    "INNER JOIN orderContent.goods goods " +
                    "INNER JOIN goods.provider provider"
    )
    Float getOverhead();
}
