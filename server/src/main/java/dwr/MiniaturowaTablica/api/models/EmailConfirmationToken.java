package dwr.MiniaturowaTablica.api.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;

@Data
@Document(collection = "emailConfirmationToken")
public class EmailConfirmationToken {
   @Id
   private String tokenId;

   private String confirmationToken;

   private LocalDate expDate;

   @DocumentReference
   private User user;

   public EmailConfirmationToken(String confirmationToken, LocalDate expDate, User user) {
      this.confirmationToken = confirmationToken;
      this.expDate = expDate;
      this.user = user;
   }
}
