package dwr.MiniaturowaTablica.api.repository;

import dwr.MiniaturowaTablica.api.models.BlockedJwt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedJwtRepository extends MongoRepository<BlockedJwt, String> {
   Boolean existsByToken(String token);
}
