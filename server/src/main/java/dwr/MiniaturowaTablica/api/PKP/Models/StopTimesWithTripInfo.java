package dwr.MiniaturowaTablica.api.PKP.Models;

public class StopTimesWithTripInfo {
    public StopTimesWithTripInfo(StopTimes stopTimes, Trip trip) {
        this.stopTimes = stopTimes;
        this.trip = trip;
    }

    StopTimes stopTimes;
    Trip trip;

    public StopTimes getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(StopTimes stopTimes) {
        this.stopTimes = stopTimes;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

   public void print(){
       System.out.println("{");
       System.out.println(stopTimes);
       System.out.println(trip);
       System.out.println("}");
   }
}
