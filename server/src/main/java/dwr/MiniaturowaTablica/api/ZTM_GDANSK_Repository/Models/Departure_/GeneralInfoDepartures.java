package dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Departure_;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneralInfoDepartures {
    private String lastUpdate;
    private List<Departure> departures;
}
