package dwr.MiniaturowaTablica.api.ZTM;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplaysRepository;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.ZTMWarsawRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Modifier;

public class ztmRepository {
    static DisplaysRepository displaysRepository;

    public static String convertToJson(Object o){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
        return gson.toJson(o);
    }


    public static void loadStaticDataToDB() throws IOException {
//        displaysRepository.deleteALL();
//        displaysRepository.saveAll(ZTMWarsawRepository.getAllDisplays());
    }

}
