package dwr.MiniaturowaTablica.api.payload.request;
import dwr.MiniaturowaTablica.api.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class RoleRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String usernameToRoleChange;
    @NotBlank
    private Set<Role> role;

}
