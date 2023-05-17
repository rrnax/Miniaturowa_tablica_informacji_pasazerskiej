package dwr.MiniaturowaTablica.api.PKP.Trains;

import dwr.MiniaturowaTablica.api.PKP.Stops.Stop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrainMongoRepository extends MongoRepository<Train, String> {
}
