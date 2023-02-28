package dwr.MiniaturowaTablica.api.repository;

import dwr.MiniaturowaTablica.api.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
}

