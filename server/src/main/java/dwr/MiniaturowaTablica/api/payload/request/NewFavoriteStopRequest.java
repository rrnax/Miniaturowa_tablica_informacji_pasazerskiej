package dwr.MiniaturowaTablica.api.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewFavoriteStopRequest {
    @NotBlank
    private String cityName;

    @NotBlank
    private String stopName;

    private Set<String> stopIds;

    private Boolean status;
}
