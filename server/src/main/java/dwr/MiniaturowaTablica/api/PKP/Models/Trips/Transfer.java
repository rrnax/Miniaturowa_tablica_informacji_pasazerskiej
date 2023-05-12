package dwr.MiniaturowaTablica.api.PKP.Models.Trips;


public class Transfer {
    private String fromStopId;
    private String toStopId;
    private String fromTripId;
    private String toTripId;
    private String transferType;

    public Transfer(String fromStopId, String toStopId, String fromTripId, String toTripId, String transferType) {
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.fromTripId = fromTripId;
        this.toTripId = toTripId;
        this.transferType = transferType;
    }

    public String getFromStopId() {
        return fromStopId;
    }

    public void setFromStopId(String fromStopId) {
        this.fromStopId = fromStopId;
    }

    public String getToStopId() {
        return toStopId;
    }

    public void setToStopId(String toStopId) {
        this.toStopId = toStopId;
    }
    public void setFromTripId(String fromTripId) {
        this.fromTripId = fromTripId;
    }

    public void setToTripId(String toTripId) {
        this.toTripId = toTripId;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }


    @Override
    public String toString() {
        return "Transfer{" +
                "fromStopId='" + fromStopId + '\'' +
                ", toStopId='" + toStopId + '\'' +
                ", fromTripId='" + fromTripId + '\'' +
                ", toTripId='" + toTripId + '\'' +
                ", transferType='" + transferType + '\'' +
                '}';
    }
}
