package dwr.MiniaturowaTablica.api.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;

    @NotBlank(message = "User name is empty!")
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(min = 5, max = 50, message = "Password length invalid!")
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email(message = "Email should be valid!")
    private String email;

    @DocumentReference
    private Set<Role> roles = new HashSet<>();
    
    private Set<FavoriteStation> favoriteStations = new HashSet<>();


    private boolean isActive = false;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(it -> authorities.add(new SimpleGrantedAuthority(it.getName().name())));
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

    public ERole getName() {
        ArrayList<Role> role = new ArrayList<>(roles);
        return role.get(0).getName();
    }
}
