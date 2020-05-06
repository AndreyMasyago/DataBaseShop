package DataBase.Repository;

import DataBase.Domain.OrderContent;
import org.springframework.data.repository.CrudRepository;

public interface OrderContentRepository extends CrudRepository <OrderContent, Integer> {
}
