package dwr.tablica.api2.ZTMApiDownload.Models.Departure_;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
// TODO jezeli bedziemy tworzyc wiecej operacji w mongo na tych danych, trzeba bedzie stworzyc asembler do tworzenia klasy jak w przypadku klas display!
@Getter
@Setter
@Document(collection = "departures")
public class DepartureDTO {
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
