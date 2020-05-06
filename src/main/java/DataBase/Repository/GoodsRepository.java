package DataBase.Repository;

import DataBase.Domain.Goods;
import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository<Goods, Integer>  {
    public Goods findByGoodsId(Integer goodsId);
}
