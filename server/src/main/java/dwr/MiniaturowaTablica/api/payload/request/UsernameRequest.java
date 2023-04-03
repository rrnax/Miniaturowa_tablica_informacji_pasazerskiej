package dwr.MiniaturowaTablica.api.payload.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
@Getter
@Setter
@AllArgsConstructor
public class UsernameRequest {
    @NotBlank
    private String Username;
    @NotBlank
    private String Password;
    @NotBlank
    private String UsernameToFind;
}
