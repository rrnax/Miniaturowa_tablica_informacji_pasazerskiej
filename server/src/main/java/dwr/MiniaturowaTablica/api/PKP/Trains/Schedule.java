package dwr.MiniaturowaTablica.api.PKP.Trains;

public class Schedule {
    public String arrival_time;
    public String estimatedTime;
    public String official_dist_traveled;
    public String StopName;

    public Schedule(String arrival_time, String estimatedTime, String official_dist_traveled, String stopName) {
        this.arrival_time = arrival_time;
        this.estimatedTime = estimatedTime;
        this.official_dist_traveled = official_dist_traveled;
        StopName = stopName;
    }

    public Schedule() {
    }


    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }


    public String getOfficial_dist_traveled() {
        return official_dist_traveled;
    }

    public void setOfficial_dist_traveled(String official_dist_traveled) {
        this.official_dist_traveled = official_dist_traveled;
    }

    public String getStopName() {
        return StopName;
    }

    public void setStopName(String stopName) {
        StopName = stopName;
    }
}
