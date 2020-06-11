package DataBase.Repository;

import DataBase.Domain.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
    public OrderEntity findByOrderId(Integer orderId);
    public void deleteById(int id);

    // 16.1
    @Query(
         "SELECT orderEntity, SUM(orderContent.amount * goods.sellingPrice) " +
         "FROM OrderEntity orderEntity " +
                 "INNER JOIN orderEntity.orderContentList orderContent " +
                 "INNER JOIN orderContent.goods goods " +
         "WHERE orderEntity.orderDate > current_date " +
         "GROUP BY orderEntity"
    )
    public List<Object[]> getFutureOrders();

    // 16.2
    @Query(
            "SELECT SUM(orderContent.amount * goods.sellingPrice), COUNT(DISTINCT orderEntity.orderId) " +
                    "FROM OrderEntity orderEntity " +
                    "INNER JOIN orderEntity.orderContentList orderContent " +
                    "INNER JOIN orderContent.goods goods " +
                    "WHERE orderEntity.orderDate > current_date "
    )
    public List<Object[]> getFutureOrdersCount();
}
