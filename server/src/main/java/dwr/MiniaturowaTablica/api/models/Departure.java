package dwr.MiniaturowaTablica.api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "departures")
public class Departure {
   @Id
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
   private int StopID_fromRequest;


}
