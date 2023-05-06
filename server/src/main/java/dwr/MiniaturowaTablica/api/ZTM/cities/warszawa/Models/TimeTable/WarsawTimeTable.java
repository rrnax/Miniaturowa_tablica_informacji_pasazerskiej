package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.TimeTable;

public class WarsawTimeTable {
   private String czas;
   private String kierunek;
   private String linia;

   public WarsawTimeTable(String czas, String kierunek, String linia) {
      this.czas = czas;
      this.kierunek = kierunek;
      this.linia = linia;
   }

   public String getCzas() {
      return czas;
   }

   public void setCzas(String czas) {
      this.czas = czas;
   }

   public String getKierunek() {
      return kierunek;
   }

   public void setKierunek(String kierunek) {
      this.kierunek = kierunek;
   }

   public String getLinia() {
      return linia;
   }

   public void setLinia(String linia) {
      this.linia = linia;
   }

   @Override
   public String toString() {
      return  "czas='" + czas + '\'' +
              ", kierunek='" + kierunek + '\'' +
              ", linia='" + linia + '\'' ;
   }
}

