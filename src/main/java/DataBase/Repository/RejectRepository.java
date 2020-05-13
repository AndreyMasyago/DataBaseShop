package DataBase.Repository;

import DataBase.Domain.Reject;
import org.springframework.data.repository.CrudRepository;

public interface RejectRepository  extends CrudRepository<Reject, Integer> {
    public Reject findByRejectId(int id);
    public void deleteById(int id);
}