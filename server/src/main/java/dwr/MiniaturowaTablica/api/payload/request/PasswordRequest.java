package dwr.MiniaturowaTablica.api.payload.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class PasswordRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String newPassword;
}
