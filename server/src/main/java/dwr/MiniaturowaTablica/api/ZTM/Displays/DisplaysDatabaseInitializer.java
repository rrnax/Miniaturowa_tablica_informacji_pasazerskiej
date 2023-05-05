package dwr.MiniaturowaTablica.api.ZTM.Displays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplaysRepository;
import dwr.MiniaturowaTablica.api.ZTM.cities.gdansk.ZTMRepository;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.TimeTable;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.ZTMWarsawRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.List;


// FUNKCJA DODAJĄCA DISPLAYS DO BAZY ( NAJPIERW JĄ CZYŚCI A POTEM DODAJE )
// ZROBIŁ JĄ DAREK

@Component
public class DisplaysDatabaseInitializer implements CommandLineRunner {
    @Autowired
    public DisplaysRepository displaysRepository;
    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
    @Autowired
    public ZTMRepository gdanskRepository;
    @Autowired
    public TimeTable timeTable;

    @SneakyThrows
    public void run(String... args){
        loadStaticDataToDB();
    }
    public void loadStaticDataToDB() throws IOException {
        displaysRepository.deleteALL();
        //wczytanie Warszawy
        displaysRepository.saveAll(ZTMWarsawRepository.getAllDisplays());
        //Wczytanie Gdańska
        displaysRepository.saveAll(gson.fromJson(gdanskRepository.getAllDisplays(), new TypeToken<List<DisplayDTO>>() {
        }.getType()));

        //timeTable.getLineTimetable("1001","01","125");
    }
}
