package dwr.MiniaturowaTablica.api.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
