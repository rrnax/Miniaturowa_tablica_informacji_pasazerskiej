package dwr.MiniaturowaTablica.api.ZTM.Displays;

import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Display_.helpers.WarsawDisplay;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import static java.lang.Integer.parseInt;

@Getter
@Setter
@Document(collection = "displays")
public class DisplayDTO   {


    private int displayCode;
    private String city;
    private String originalName;
    private String name;
    private String type;
    private String drivingDirection;

    private int idStop1;
    private int idStop2;
    private int idStop3;
    private int idStop4;

    public DisplayDTO() {
    }


    public DisplayDTO(WarsawDisplay warsawDisplay){
        setCity("Warszawa");
        setDisplayCode(0);
        setName(warsawDisplay.getNazwa_zespolu());
        setOriginalName(warsawDisplay.getNazwa_zespolu());
        setType("?");
        setDrivingDirection(warsawDisplay.getKierunek());
        setIdStop1(parseInt(warsawDisplay.getSlupek()));
        setIdStop2(0);
        setIdStop3(0);
        setIdStop4(0);
    }

    public void setNextIdStop(DisplayDTO displayDTO){
        int checkedNewIdStop = displayDTO.getIdStop1();
        if(getIdStop1()!=checkedNewIdStop
                && getIdStop2()!=checkedNewIdStop
                && getIdStop3()!=checkedNewIdStop
                && getIdStop4()!=checkedNewIdStop) {
            if (getIdStop1() == 0) setIdStop1(checkedNewIdStop);
            else if (getIdStop2() == 0) setIdStop2(checkedNewIdStop);
            else if (getIdStop3() == 0) setIdStop3(checkedNewIdStop);
            else if (getIdStop4() == 0) setIdStop4(checkedNewIdStop);
        }
    }

    @Override
    public String toString() {
        return "DisplayDTO{" +
                "displayCode=" + displayCode +
                ", city='" + city + '\'' +
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

    public void setCity(String city) {
        this.city = city;
    }
}