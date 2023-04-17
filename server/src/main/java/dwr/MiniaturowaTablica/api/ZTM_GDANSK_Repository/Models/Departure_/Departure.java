package dwr.tablica.api2.ZTMApiDownload.Models.Departure_;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
