package dwr.MiniaturowaTablica.api.PKP.Arrivals;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

// SSA = SIMPLE STOP ARRIVALS (READY TO DB)

@Data
@Document(collection = "PKP_ARRIVALS")
public class SSA {
    public String stop_id;
    public String route_id;
    public String service_id;
    public String arrival_time;
    public String estimatedTime; //departure time
    public String tripId;
    public String headsign;
    public String trip_short_name;
    public String official_dist_traveled;

    public SSA(String stop_id, String arrival_time, String estimatedTime, String tripId, String route_id, String service_id, String headsign, String trip_short_name, String official_dist_traveled) {
        this.stop_id = stop_id;
        this.arrival_time = arrival_time;
        this.estimatedTime = estimatedTime;
        this.tripId = tripId;
        this.route_id = route_id;
        this.service_id = service_id;
        this.headsign = headsign;
        this.trip_short_name = trip_short_name;
        this.official_dist_traveled = official_dist_traveled;
    }


}
