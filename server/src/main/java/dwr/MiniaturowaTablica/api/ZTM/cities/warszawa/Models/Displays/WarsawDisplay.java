package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Displays;


// WarsawDisplays is data for every stop in departure
// WarsawDisplays przechowuje informacje o każdym przystanku w rozróżnieniu na kierunek i numer słupka
public class WarsawDisplay {
    public String zespol;
    public String slupek;
    public String nazwa_zespolu;
    public String id_ulicy;
    public String szer_geo;
    public String dlug_geo;
    public String kierunek;

    @Override
    public String toString() {
        return
                "zespol='" + zespol + '\'' +
                ", slupek='" + slupek + '\'' +
                ", nazwa_zespolu='" + nazwa_zespolu + '\'' +
                ", id_ulicy='" + id_ulicy + '\'' +
                ", szer_geo='" + szer_geo + '\'' +
                ", dlug_geo='" + dlug_geo + '\'' +
                ", kierunek='" + kierunek + '\'';
    }

    public String getZespol() {
        return zespol;
    }

    public void setZespol(String zespol) {
        this.zespol = zespol;
    }

    public String getSlupek() {
        return slupek;
    }

    public void setSlupek(String slupek) {
        this.slupek = slupek;
    }

    public String getNazwa_zespolu() {
        return nazwa_zespolu;
    }

    public void setNazwa_zespolu(String nazwa_zespolu) {
        this.nazwa_zespolu = nazwa_zespolu;
    }

    public String getId_ulicy() {
        return id_ulicy;
    }

    public void setId_ulicy(String id_ulicy) {
        this.id_ulicy = id_ulicy;
    }

    public String getSzer_geo() {
        return szer_geo;
    }

    public void setSzer_geo(String szer_geo) {
        this.szer_geo = szer_geo;
    }

    public String getDlug_geo() {
        return dlug_geo;
    }

    public void setDlug_geo(String dlug_geo) {
        this.dlug_geo = dlug_geo;
    }

    public String getKierunek() {
        return kierunek;
    }

    public void setKierunek(String destination) {
        if(destination.isEmpty() || destination.contains("___")) this.kierunek="null";
        else if(destination.contains("-")){
            this.kierunek = clearDestination(destination).trim();
        }
        else {
            this.kierunek = destination;
        }
    }

    public static String clearDestination(String destination){
        return destination.replaceAll("-","");
    }

}

