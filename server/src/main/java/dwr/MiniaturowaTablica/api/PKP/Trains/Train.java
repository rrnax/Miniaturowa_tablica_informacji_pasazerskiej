package dwr.MiniaturowaTablica.api.PKP.Trains;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "PKP_TRAINS   ")
public class Train {
    public String id;
    public String name;
    public String line;
    public String status; // czy jest w drodze, czy na stacji czy skończył swój kurs czy go zaczyna
    public Integer distanceTraveled; // w metrach
    public List<Schedule> scheduleList;
    public Schedule lastStopSchedule; // przechowuje info o ostatnim przystanku

    public Train() {
    }

    public Train(String id, String name, String line, String status, Integer distanceTraveled, List<Schedule> scheduleList, Schedule lastStopSchedule) {
        this.id = id;
        this.name = name;
        this.line = line;
        this.status = status;
        this.distanceTraveled = distanceTraveled;
        this.scheduleList = scheduleList;
        this.lastStopSchedule = lastStopSchedule;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void deleteFirstSchedule(){
        if(scheduleList.size()>1)
            scheduleList.remove(0);
    }

    public Schedule getLastStopSchedule() {
        return lastStopSchedule;
    }
    public Schedule getFirstSchedule(){
        return scheduleList.get(0);
    }

    public void setLastStopSchedule(Schedule lastStopSchedule) {
        this.lastStopSchedule = lastStopSchedule;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", line='" + line + '\'' +
                ", status='" + status + '\'' +
                ", distanceTraveled=" + distanceTraveled +
                ", scheduleList=" + scheduleList +
                ", lastStopSchedule=" + lastStopSchedule +
                '}';
    }
}
