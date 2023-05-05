package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.TimeTable;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RawTimeTable {
    private String symbol2;
    private String symbol1;
    private int brygada;
    private String kierunek;
    private String trasa;
    private String czas;

    public RawTimeTable(String symbol2, String symbol1, int brygada, String kierunek, String trasa, String czas) {
        this.symbol2 = symbol2;
        this.symbol1 = symbol1;
        this.brygada = brygada;
        this.kierunek = kierunek;
        this.trasa = trasa;
        this.czas = czas;
    }

    public String getSymbol2() {
        return symbol2;
    }

    public String getSymbol1() {
        return symbol1;
    }

    public int getBrygada() {
        return brygada;
    }

    public String getKierunek() {
        return kierunek;
    }

    public String getTrasa() {
        return trasa;
    }

    public String getCzas() {
        return czas;
    }
}

