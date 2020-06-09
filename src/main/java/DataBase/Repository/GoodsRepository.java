package DataBase.Repository;

import DataBase.Domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, Integer>  {
    public Goods findByGoodsId(Integer goodsId);
    public void deleteById(int id);

    // 2.1
    @Query(
            "SELECT g FROM Goods g INNER JOIN FETCH g.catalog c INNER JOIN FETCH g.provider p WHERE c.goodsName LIKE CONCAT('%', :goodsSearch, '%')"
    )
    public List<Goods> getGoodsInfo(@Param("goodsSearch") String goodsSearch);

    // 2.2
    @Query(
            "SELECT COUNT(g.goodsId) FROM Goods g INNER JOIN g.catalog c WHERE c.goodsName LIKE CONCAT('%', :goodsSearch, '%')"
    )
    public Long countGoodsInfo(@Param("goodsSearch") String goodsSearch);

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
            "SELECT DISTINCT p.providerName " +
            "FROM Goods g " +
                    "INNER JOIN g.provider p " +
                    "INNER JOIN g.rejectList reject " +
                    "INNER JOIN reject.orderEntity orderEntity "
    )
    public List<String> getRejectProviders();
}
