package dwr.MiniaturowaTablica.api.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Data
@Document(collection="users")
public class User {
    @Id
    private String id;

    @NotBlank(message = "User name is empty!")
    @Size(max=20)
    private String username;

    @NotBlank
    @Size(min=5,max=50, message="Password length invalid!")
    private String password;

    @NotBlank
    @Size(max=50)
    @Email(message = "Email should be valid!")
    private String email;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public ERole getName(){
        ArrayList<Role> role = new ArrayList<>(roles);
        return role.get(0).getName();
    }
}
