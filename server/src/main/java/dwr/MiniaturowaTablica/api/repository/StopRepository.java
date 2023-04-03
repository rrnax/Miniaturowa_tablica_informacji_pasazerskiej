package dwr.MiniaturowaTablica.api.repository;

import dwr.MiniaturowaTablica.api.models.Stop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends MongoRepository<Stop,String> {
}
