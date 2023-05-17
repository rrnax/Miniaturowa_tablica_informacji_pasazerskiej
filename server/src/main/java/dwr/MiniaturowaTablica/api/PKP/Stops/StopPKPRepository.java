package dwr.MiniaturowaTablica.api.PKP.Stops;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StopPKPRepository extends MongoRepository<Stop, String> {
}