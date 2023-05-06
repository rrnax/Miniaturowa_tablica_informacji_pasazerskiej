package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.TimeTable;

public class WarsawTimeTable {
   private String estimatedTime;
   private String kierunek;
   private String tripid;

   public WarsawTimeTable(String estimatedTime, String kierunek, String tripid) {
      this.estimatedTime = estimatedTime;
      this.kierunek = kierunek;
      this.tripid = tripid;
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

   public String getTripid() {
      return tripid;
   }

   public void setTripid(String tripid) {
      this.tripid = tripid;
   }

   @Override
   public String toString() {
      return  "czas='" + estimatedTime + '\'' +
              ", kierunek='" + kierunek + '\'' +
              ", linia='" + tripid + '\'' ;
   }
}

