package dwr.MiniaturowaTablica.api.ZTM.cities.gdansk.Models.Departure_;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Departure {

    private String id;

    private int delayInSeconds;
    private String estimatedTime;
    private String headsign;
    private int routeId;
    private String scheduledTripStartTime;
    private int tripId;
    private String status;
    private String theoreticalTime;
    private String timestamp;
    private int trip;
    private String vehicleCode;
    private int vehicleId;
    private String vehicleService;


}
