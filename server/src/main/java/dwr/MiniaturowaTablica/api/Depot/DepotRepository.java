package dwr.MiniaturowaTablica.api.Depot;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DepotRepository extends MongoRepository<Depot,String> {
}
