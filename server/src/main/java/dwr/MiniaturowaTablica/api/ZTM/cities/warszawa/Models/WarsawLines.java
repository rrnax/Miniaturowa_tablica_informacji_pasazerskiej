package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models;

public class WarsawLines {
    String idStop;
    String linia;

    public WarsawLines(String idStop, String linia) {
        this.idStop = idStop;
        this.linia = linia;
    }

    public String getIdStop() {
        return idStop;
    }

    public void setIdStop(String idStop) {
        this.idStop = idStop;
    }

    public String getLinia() {
        return linia;
    }

    public void setLinia(String linia) {
        this.linia = linia;
    }

    @Override
    public String toString() {
        return "idStop='" + getIdStop() + '\'' +
                ", linia='" + getLinia() + '\'';
    }
}
