package DataBase.Repository;

import DataBase.Domain.Provider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProviderRepository extends CrudRepository<Provider, Integer> {
    public Provider findByProviderId(int providerId);
    public void deleteById(int id);

    // 1.1
    @Query(
            "SELECT p " +
            "FROM Provider p " +
                    "JOIN p.goodsList goods " +
                    "JOIN goods.deliveryContentList deliveryContent " +
            "WHERE goods.catalog.detailId = :goodsSearch " +
                    "AND p.category = :categorySearch " +
                    "AND deliveryContent.delivery.arrivingDateOnStorage BETWEEN :arrivingDateFrom AND :arrivingDateTo " +
            "GROUP BY p.providerId HAVING SUM(deliveryContent.amount) > :amountLimit"
    )
    public List<Provider> findDeliveredMoreThanCount(
            @Param("goodsSearch") Integer goodsSearch,
            @Param("categorySearch") String categorySearch,
            @Param("arrivingDateFrom") Date arrivingDateFrom,
            @Param("arrivingDateTo") Date arrivingDateTo,
            @Param("amountLimit") Long amountLimit
    );

    // 1.2.
    @Query(
            "SELECT COUNT(p1.providerId) from Provider p1 WHERE p1.providerId IN " +
                    "(SELECT DISTINCT p.providerId as providerId " +
                            "FROM Provider p " +
                                    "JOIN p.goodsList goods " +
                                    "JOIN goods.deliveryContentList deliveryContent " +
                            "WHERE goods.catalog.detailId = :goodsSearch " +
                                    "AND p.category = :categorySearch " +
                                    "AND deliveryContent.delivery.arrivingDateOnStorage BETWEEN :arrivingDateFrom AND :arrivingDateTo " +
                            "GROUP BY p.providerId HAVING SUM(deliveryContent.amount) > :amountLimit " +
                    ")"
    )
    public Long countDeliveredMoreThanCount(
            @Param("goodsSearch") Integer goodsSearch,
            @Param("categorySearch") String categorySearch,
            @Param("arrivingDateFrom") Date arrivingDateFrom,
            @Param("arrivingDateTo") Date arrivingDateTo,
            @Param("amountLimit") Long amountLimit
    );
}