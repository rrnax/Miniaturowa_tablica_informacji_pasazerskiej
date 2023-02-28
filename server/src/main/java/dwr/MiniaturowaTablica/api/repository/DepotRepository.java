package dwr.MiniaturowaTablica.api.repository;

import dwr.MiniaturowaTablica.api.models.Depot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepotRepository extends MongoRepository<Depot,String> {
}
