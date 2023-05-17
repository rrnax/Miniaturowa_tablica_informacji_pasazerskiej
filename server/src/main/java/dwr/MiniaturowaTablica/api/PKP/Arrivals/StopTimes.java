package dwr.MiniaturowaTablica.api.PKP.Arrivals;
//trip_id,stop_sequence,stop_id,arrival_time,departure_time,platform,official_dist_traveled
public class StopTimes {
    public String trip_id;
    public String stop_sequence;
    public String stop_id;
    public String arrival_time;
    public String departure_time;
    public String platform;
    public String official_dist_traveled;

    public StopTimes(String trip_id, String stop_sequence, String stop_id, String arrival_time, String departure_time, String platform, String official_dist_traveled) {
        this.trip_id = trip_id;
        this.stop_sequence = stop_sequence;
        this.stop_id = stop_id;
        this.arrival_time = arrival_time;
        this.departure_time = departure_time;
        this.platform = platform;
        this.official_dist_traveled = official_dist_traveled;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getStop_sequence() {
        return stop_sequence;
    }

    public void setStop_sequence(String stop_sequence) {
        this.stop_sequence = stop_sequence;
    }

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOfficial_dist_traveled() {
        return official_dist_traveled;
    }

    public void setOfficial_dist_traveled(String official_dist_traveled) {
        this.official_dist_traveled = official_dist_traveled;
    }

    @Override
    public String toString() {
        return "StopTimes{" +
                "trip_id='" + trip_id + '\'' +
                ", stop_sequence='" + stop_sequence + '\'' +
                ", stop_id='" + stop_id + '\'' +
                ", arrival_time='" + arrival_time + '\'' +
                ", departure_time='" + departure_time + '\'' +
                ", platform='" + platform + '\'' +
                ", official_dist_traveled='" + official_dist_traveled + '\'' +
                '}';
    }
}
