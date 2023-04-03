package dwr.MiniaturowaTablica.api.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection="users")
public class User implements UserDetails {
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

    private boolean isActive = false;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach( it -> authorities.add(new SimpleGrantedAuthority(it.getName().name())));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public ERole getName(){
        ArrayList<Role> role = new ArrayList<>(roles);
        return role.get(0).getName();
    }
}
