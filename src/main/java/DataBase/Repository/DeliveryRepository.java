package DataBase.Repository;


import DataBase.Domain.Delivery;
import org.springframework.data.repository.CrudRepository;


public interface DeliveryRepository extends CrudRepository<Delivery, Integer> {
}
