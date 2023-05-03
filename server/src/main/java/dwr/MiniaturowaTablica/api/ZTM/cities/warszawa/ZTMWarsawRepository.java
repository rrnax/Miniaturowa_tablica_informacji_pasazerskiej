package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa;

import com.google.gson.GsonBuilder;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Display_.helpers.DisplayDTOList;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Display_.helpers.WarsawDisplay;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Display_.helpers.staticDataLoader;
import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplayDTO;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZTMWarsawRepository {
    public static DisplayDTOList displayDTOList;


    public static List<DisplayDTO> getAllDisplays() throws IOException {
        List<WarsawDisplay> displayList;
        List<DisplayDTO> displayDTOS = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        displayList = staticDataLoader.loadDataFromJsonFile(".\\src\\main\\resources\\static\\ztmWarszawa.json");
        if (!displayList.isEmpty()) {
            displayList.forEach(e -> {
                DisplayDTO displayDTO = new DisplayDTO(e);
                displayDTOS.add(displayDTO);
            });
            displayDTOList = new DisplayDTOList(displayDTOS);

            return displayDTOList.displayDTOS;
        }
        return null;
    }

}
