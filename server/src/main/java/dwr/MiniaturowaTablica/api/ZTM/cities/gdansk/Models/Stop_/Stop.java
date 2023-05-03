package dwr.MiniaturowaTablica.api.ZTM.cities.gdansk.Models.Stop_;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Stop {
    private int stopId;
    private String stopCode;
    private String stopName;
    private String stopShortName;
    private String stopDesc;
    private String subName;
    private String date;
    private int zoneId;
    private String zoneName;
    private int virtual;
    private int nonpassenger;
    private int depot;
    private int ticketZoneBorder;
    private int onDemand;
    private Date activationDate;
    private String stopUrl;
    private String locationType;
    private String parentStation;
    private String stopTimezone;
    private String wheelchairBoarding;
    private float stopLat;
    private float stopLon;



}
