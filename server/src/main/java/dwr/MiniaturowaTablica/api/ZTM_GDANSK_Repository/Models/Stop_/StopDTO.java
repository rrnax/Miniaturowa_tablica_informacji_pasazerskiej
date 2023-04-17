package dwr.tablica.api2.ZTMApiDownload.Models.Stop_;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Getter
@Setter
@Document(collection = "stops")
public class StopDTO {
    private int stopId;

    private String stopCode;
    private String stopName;
    private String stopShortName;
    private String stopDesc;
    private String date;
    private float stopLat;
    private float stopLon;
}

