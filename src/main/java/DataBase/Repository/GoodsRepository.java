package DataBase.Repository;

import DataBase.Domain.Goods;
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
}
