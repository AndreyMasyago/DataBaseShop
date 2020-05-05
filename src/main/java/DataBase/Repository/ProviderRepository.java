package DataBase.Repository;

import DataBase.Domain.Provider;
import org.springframework.data.repository.CrudRepository;

public interface ProviderRepository extends CrudRepository<Provider, Integer> {
    public Provider findByProviderId(int providerId);
}