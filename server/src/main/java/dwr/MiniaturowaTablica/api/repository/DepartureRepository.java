package dwr.MiniaturowaTablica.api.repository;

import dwr.MiniaturowaTablica.api.models.Departure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartureRepository extends MongoRepository<Departure, String> {
}
