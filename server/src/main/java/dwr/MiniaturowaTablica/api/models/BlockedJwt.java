package dwr.MiniaturowaTablica.api.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="blockedJwt")
public class BlockedJwt {

   @Id
   private String id;

   @NotBlank
   private String token;



}
