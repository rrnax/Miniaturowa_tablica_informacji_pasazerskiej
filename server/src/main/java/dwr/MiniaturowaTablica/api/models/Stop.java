package dwr.MiniaturowaTablica.api.models;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "stops")
public class Stop {
    private int stopId;

    private String stopCode;
    private String stopName;
    private String stopShortName;
    private String stopDesc;
    private String date;
    private float stopLat;
    private float stopLon;
}

