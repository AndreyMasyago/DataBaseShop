package DataBase.Repository;

import DataBase.Domain.Catalog;
import DataBase.Domain.Provider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ProviderRepository extends CrudRepository<Provider, Integer> {
    public Provider findByProviderId(int providerId);
    public void deleteById(int id);

    @Query(
            "SELECT p " +
            "FROM Provider p " +
                    "JOIN p.goodsList goods " +
                    "JOIN goods.deliveryContentList deliveryContent " +
            "WHERE goods.catalog.goodsName LIKE CONCAT('%',:goodsSearch,'%') " +
                    "AND p.category LIKE CONCAT('%',:categorySearch,'%') " +
                    "AND deliveryContent.delivery.arrivingDateOnStorage BETWEEN :arrivingDateFrom AND :arrivingDateTo " +
            "GROUP BY p.providerName HAVING SUM(deliveryContent.amount) > :amountLimit"
    )
    public List<Provider> findDeliveredMoreThanCount(
            @Param("goodsSearch") String goodsSearch,
            @Param("categorySearch") String categorySearch,
            @Param("arrivingDateTo") Date arrivingDateTo,
            @Param("arrivingDateFrom") Date arrivingDateFrom,
            @Param("amountLimit") Integer amountLimit
    );

    @Query(
            "SELECT p " +
            "FROM Provider p " +
                    "JOIN p.goodsList goods " +
                    "JOIN goods.deliveryContentList deliveryContent " +
            "WHERE goods.catalog.goodsName LIKE CONCAT('%',:goodsSearch,'%') " +
                    "AND p.category LIKE CONCAT('%',:categorySearch,'%') " +
            "GROUP BY p.providerId HAVING SUM(deliveryContent.amount) > :amountLimit"
    )
    public List<Provider> findDeliveredMoreThanCount(
            @Param("goodsSearch") String goodsSearch,
            @Param("categorySearch") String categorySearch,
            @Param("amountLimit") Long amountLimit
    );

    @Query(
            "SELECT count(p1.providerId) from Provider p1 where p1.providerId in (SELECT DISTINCT p.providerId as providerId " +
                    "FROM Provider p " +
                    "JOIN p.goodsList goods " +
                    "JOIN goods.deliveryContentList deliveryContent " +
                    "WHERE goods.catalog.goodsName LIKE CONCAT('%',:goodsSearch,'%') " +
                    "AND p.category LIKE CONCAT('%',:categorySearch,'%') " +
                    "GROUP BY p.providerId HAVING SUM(deliveryContent.amount) > :amountLimit " +
            ")"
    )
    public Long countDeliveredMoreThanCount(
            @Param("goodsSearch") String goodsSearch,
            @Param("categorySearch") String categorySearch,
            @Param("amountLimit") Long amountLimit
    );
}