package DataBase.Repository;

import DataBase.Domain.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
    public OrderEntity findByOrderId(Integer orderId);
    public void deleteById(int id);
}
