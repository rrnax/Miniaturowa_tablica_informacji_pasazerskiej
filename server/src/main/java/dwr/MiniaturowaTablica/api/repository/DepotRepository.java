package dwr.MiniaturowaTablica.api.repository;

import dwr.MiniaturowaTablica.api.models.Depot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepotRepository extends MongoRepository<Depot, String> {
}
