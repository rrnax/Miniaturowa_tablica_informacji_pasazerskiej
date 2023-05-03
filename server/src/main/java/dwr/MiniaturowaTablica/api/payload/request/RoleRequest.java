package dwr.MiniaturowaTablica.api.payload.request;

import dwr.MiniaturowaTablica.api.models.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
