package dwr.MiniaturowaTablica.api.repository;

import dwr.MiniaturowaTablica.api.models.Display;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisplayRepository extends MongoRepository<Display, String> {
}
