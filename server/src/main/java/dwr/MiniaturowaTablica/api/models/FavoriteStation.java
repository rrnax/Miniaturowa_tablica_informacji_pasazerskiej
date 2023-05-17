package dwr.MiniaturowaTablica.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteStation {

    private Long id = 0L;

    private String cityName;

    private String stopName;

    private Set<String> stopIds;

    private Boolean status;
}
