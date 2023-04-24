package dwr.MiniaturowaTablica.api.repository;
import dwr.MiniaturowaTablica.api.models.EmailConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfirmationRepository extends MongoRepository<EmailConfirmationToken, String> {
   EmailConfirmationToken findByConfirmationToken(String confirmationToken);
}
