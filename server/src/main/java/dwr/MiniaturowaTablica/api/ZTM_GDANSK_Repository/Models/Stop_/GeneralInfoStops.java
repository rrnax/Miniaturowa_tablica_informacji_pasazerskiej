package dwr.tablica.api2.ZTMApiDownload.Models.Stop_;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GeneralInfoStops
{
    private   String lastUpdate;
    private List<Stop> stops;

    @Override
    public String toString() {
        return "GeneralInfoStops{" +
                "lastUpdate='" + lastUpdate + '\'' +
                '}';
    }
}
