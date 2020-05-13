package DataBase.Repository;

import DataBase.Domain.OrderContent;
import org.springframework.data.repository.CrudRepository;

public interface OrderContentRepository extends CrudRepository <OrderContent, Integer> {
    public OrderContent findByOrderContentId(int id);
    public void deleteById(int id);
}
