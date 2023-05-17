package dwr.MiniaturowaTablica.api.PKP.Trains;

public class Train {
    public String name;
    public String line;
    public String status; // czy jest w drodze, czy na stacji czy skończył swój kurs czy go zaczyna
    public Float distanceTraveled; // w metrach

    public Train(String name, String line, String status, Float distanceTraveled) {
        this.name = name;
        this.line = line;
        this.status = status;
        this.distanceTraveled = distanceTraveled;
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

    public Float getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(Float distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }
}
