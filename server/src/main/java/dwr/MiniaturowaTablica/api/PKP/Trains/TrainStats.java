package dwr.MiniaturowaTablica.api.PKP.Trains;

// class for trains statistics, average speed today, number of arrivals etc.
public class TrainStats {
    private String averageSpeed;
    private String arrivalsToday;
    private String arrivalsMaxToday;
    private String arrivalsInMonth;



    public TrainStats(String averageSpeed, String arrivalsToday, String arrivalsMaxToday, String arrivalsInMonth) {
        this.averageSpeed = averageSpeed;
        this.arrivalsToday = arrivalsToday;
        this.arrivalsMaxToday = arrivalsMaxToday;
        this.arrivalsInMonth = arrivalsInMonth;
    }

    public String getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getArrivalsToday() {
        return arrivalsToday;
    }

    public void setArrivalsToday(String arrivalsToday) {
        this.arrivalsToday = arrivalsToday;
    }

    public String getArrivalsInMonth() {
        return arrivalsInMonth;
    }

    public void setArrivalsInMonth(String arrivalsInMonth) {
        this.arrivalsInMonth = arrivalsInMonth;
    }

    public String getArrivalsMaxToday() {
        return arrivalsMaxToday;
    }

    public void setArrivalsMaxToday(String arrivalsMaxToday) {
        this.arrivalsMaxToday = arrivalsMaxToday;
    }
}
