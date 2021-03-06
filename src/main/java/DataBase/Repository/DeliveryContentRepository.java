package DataBase.Repository;

import DataBase.Domain.DeliveryContent;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryContentRepository extends CrudRepository<DeliveryContent, Integer> {
    public DeliveryContent findByDeliveryContentId(int id);
    public void deleteById(int id);
}
