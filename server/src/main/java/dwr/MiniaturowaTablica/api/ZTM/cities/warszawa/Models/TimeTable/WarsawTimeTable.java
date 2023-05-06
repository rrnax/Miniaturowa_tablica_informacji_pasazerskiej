package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.TimeTable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class WarsawTimeTable {
   private String estimatedTime;
   private String kierunek;
   private String tripId;

   public WarsawTimeTable(String estimatedTime, String kierunek, String tripId) {
      setEstimatedTime(estimatedTime);
      setKierunek(kierunek);
      setTripId(tripId);
   }

   public String getEstimatedTime() {
      return estimatedTime;
   }

   public void setEstimatedTime(String estimatedTime) {
      Integer estimatedHour = Integer.parseInt(estimatedTime.substring(0,2).toString());
      if(estimatedHour>23){
         estimatedHour = estimatedHour-24;
         if(estimatedHour<9){
            estimatedTime = "0"+estimatedHour.toString() + estimatedTime.substring(2);
         }
         else{
            estimatedTime = estimatedHour.toString() + estimatedTime.substring(2);
         }

      }
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
      return  "czas='" + getEstimatedTime() + '\'' +
              ", kierunek='" + getKierunek() + '\'' +
              ", linia='" + getTripId() + '\'' ;
   }
}

