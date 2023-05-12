package dwr.MiniaturowaTablica.api.PKP.Models.Trips;
//route_id,service_id,trip_id,trip_headsign,trip_short_name
public class Trip {
    String route_id;
    String service_id;
    String trip_id;
    String trip_headsign;
    String trip_short_name;

    public Trip(String route_id, String service_id, String trip_id, String trip_headsign, String trip_short_name) {
        this.route_id = route_id;
        this.service_id = service_id;
        this.trip_id = trip_id;
        this.trip_headsign = trip_headsign;
        this.trip_short_name = trip_short_name;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getTrip_headsign() {
        return trip_headsign;
    }

    public void setTrip_headsign(String trip_headsign) {
        this.trip_headsign = trip_headsign;
    }

    public String getTrip_short_name() {
        return trip_short_name;
    }

    public void setTrip_short_name(String trip_short_name) {
        this.trip_short_name = trip_short_name;
    }

    @Override
    public String toString() {
        return "Trips{" +
                "route_id='" + route_id + '\'' +
                ", service_id='" + service_id + '\'' +
                ", trip_id='" + trip_id + '\'' +
                ", trip_headsign='" + trip_headsign + '\'' +
                ", trip_short_name='" + trip_short_name + '\'' +
                '}';
    }
}
