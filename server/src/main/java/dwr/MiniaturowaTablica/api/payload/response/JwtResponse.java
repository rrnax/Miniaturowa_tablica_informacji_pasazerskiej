package dwr.MiniaturowaTablica.api.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String id;
	private String username;
	private String email;
	private List<String> roles;
}
