package dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Display_;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "displays")
public class DisplayDTO   {


    private int displayCode;

    private String originalName;
    private String name;
    private String type;
    private String drivingDirection;

    private int idStop1;
    private int idStop2;
    private int idStop3;
    private int idStop4;



    @Override
    public String toString() {
        return "DisplayDTO{" +
                "displayCode=" + displayCode +
                ", originalName='" + originalName + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", drivingDirection='" + drivingDirection + '\'' +
                ", idStop1=" + idStop1 +
                ", idStop2=" + idStop2 +
                ", idStop3=" + idStop3 +
                ", idStop4=" + idStop4 +
                '}';
    }
}