package dwr.MiniaturowaTablica.api.PKP.Models.Arrivals;

import dwr.MiniaturowaTablica.api.PKP.Models.Stops.Stop;
import dwr.MiniaturowaTablica.api.PKP.Models.Stops.StopPKPRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Document(collection = "PKP_ARRIVALS")
public class SimpleStopArrivals {
    public String stop_id;
    public String route_id;
    public String service_id;
    public String arrival_time;
    public String departure_time;
    public String platform;
    public String trip_headsign;
    public String trip_short_name;
    public String official_dist_traveled;

    public SimpleStopArrivals(String stop_id,String arrival_time, String departure_time, String platform, String route_id, String service_id, String trip_headsign, String trip_short_name, String official_dist_traveled) {
        this.stop_id = stop_id;
        this.arrival_time = arrival_time;
        this.departure_time = departure_time;
        this.platform = platform;
        this.route_id = route_id;
        this.service_id = service_id;
        this.trip_headsign = trip_headsign;
        this.trip_short_name = trip_short_name;
        this.official_dist_traveled = official_dist_traveled;
    }


}
