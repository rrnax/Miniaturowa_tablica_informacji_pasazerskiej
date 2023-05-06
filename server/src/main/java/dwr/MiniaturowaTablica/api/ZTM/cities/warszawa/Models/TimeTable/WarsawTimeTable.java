package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.TimeTable;

public class WarsawTimeTable {
   private String estimatedTime;
   private String kierunek;
   private String tripId;

   public WarsawTimeTable(String estimatedTime, String kierunek, String tripId) {
      this.estimatedTime = estimatedTime;
      this.kierunek = kierunek;
      this.tripId = tripId;
   }

   public String getEstimatedTime() {
      return estimatedTime;
   }

   public void setEstimatedTime(String estimatedTime) {
      this.estimatedTime = estimatedTime;
   }

   public String getKierunek() {
      return kierunek;
   }

   public void setKierunek(String kierunek) {
      this.kierunek = kierunek;
   }

   public String getTripId() {
      return tripId;
   }

   public void setTripId(String tripId) {
      this.tripId = tripId;
   }

   @Override
   public String toString() {
      return  "czas='" + estimatedTime + '\'' +
              ", kierunek='" + kierunek + '\'' +
              ", linia='" + tripId + '\'' ;
   }
}

