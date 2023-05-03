package dwr.MiniaturowaTablica.api.ZTM.cities.gdansk.Models.Departure_;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneralInfoDepartures {
    private String lastUpdate;
    private List<Departure> departures;
}
