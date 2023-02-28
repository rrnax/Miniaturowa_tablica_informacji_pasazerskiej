package dwr.MiniaturowaTablica.api.repository;

import dwr.MiniaturowaTablica.api.models.ERole;
import dwr.MiniaturowaTablica.api.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByName(ERole name);
}
