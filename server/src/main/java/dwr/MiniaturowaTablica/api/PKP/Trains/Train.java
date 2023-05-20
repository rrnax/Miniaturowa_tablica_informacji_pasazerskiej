package dwr.MiniaturowaTablica.api.PKP.Trains;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "PKP_TRAINS   ")
public class Train {
    public String id;
    public String name;
    public String line;
    public String status; // czy jest w drodze, czy na stacji czy skończył swój kurs czy go zaczyna
    public Integer distanceTraveled; // w metrach

    public Train(String id, String name, String line, String status, Integer distanceTraveled) {
        this.id = id;
        this.name = name;
        this.line = line;
        this.status = status;
        this.distanceTraveled = distanceTraveled;
    }

    public Train() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(Integer distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }
}
