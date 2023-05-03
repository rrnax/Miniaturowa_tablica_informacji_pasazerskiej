package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Display_.helpers.DisplayDTOArrayList;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Display_.helpers.WarsawDisplay;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Display_.helpers.staticDataLoader;
import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplayDTO;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZTMWarsawRepository {
    public static List<DisplayDTO> getAllDisplays() throws IOException {
        List<WarsawDisplay> displayList;
        List<DisplayDTO> displayDTOS = new ArrayList<>();
        DisplayDTOArrayList displayDTOArrayList;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        displayList = staticDataLoader.loadDataFromJsonFile(".\\src\\main\\resources\\static\\ztmWarszawa.json");
        if (!displayList.isEmpty()) {
            displayList.forEach(e -> {
                DisplayDTO displayDTO = new DisplayDTO(e);
                displayDTOS.add(displayDTO);
            });
            displayDTOArrayList = new DisplayDTOArrayList(displayDTOS);
            return displayDTOArrayList.displayDTOS;
        }
        return null;
    }

    


    public static String convertToJson(Object o){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
        return gson.toJson(o);
    }




}
