package dwr.MiniaturowaTablica.api.PKP.Models;
//stop_id,stop_name,stop_lat,stop_lon,stop_IBNR
public class Stop {
    public String stop_id;
    public String stop_name;
    public String stop_lat;
    public String stop_lon;
    public String stop_IBNR;

    public Stop(String stop_id, String stop_name, String stop_lat, String stop_lon) {
        this.stop_id = stop_id;
        this.stop_name = stop_name;
        this.stop_lat = stop_lat;
        this.stop_lon = stop_lon;
    }

    public Stop(String stop_id, String stop_name, String stop_lat, String stop_lon, String stop_IBNR) {
        this.stop_id = stop_id;
        this.stop_name = stop_name;
        this.stop_lat = stop_lat;
        this.stop_lon = stop_lon;
        this.stop_IBNR = stop_IBNR;
    }

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public String getStop_lat() {
        return stop_lat;
    }

    public void setStop_lat(String stop_lat) {
        this.stop_lat = stop_lat;
    }

    public String getStop_lon() {
        return stop_lon;
    }

    public void setStop_lon(String stop_lon) {
        this.stop_lon = stop_lon;
    }

    public String getStop_IBNR() {
        return stop_IBNR;
    }

    public void setStop_IBNR(String stop_IBNR) {
        this.stop_IBNR = stop_IBNR;
    }

    @Override
    public String toString() {
        return "Stops{" +
                "stop_id='" + stop_id + '\'' +
                ", stop_name='" + stop_name + '\'' +
                ", stop_lat='" + stop_lat + '\'' +
                ", stop_lon='" + stop_lon + '\'' +
                ", stop_IBNR='" + stop_IBNR + '\'' +
                '}';
    }
}
