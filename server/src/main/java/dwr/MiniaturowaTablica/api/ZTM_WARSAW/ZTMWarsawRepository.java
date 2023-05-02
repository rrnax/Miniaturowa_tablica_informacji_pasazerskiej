package dwr.MiniaturowaTablica.api.ZTM_WARSAW;

import com.google.gson.GsonBuilder;
import dwr.MiniaturowaTablica.api.ZTM_WARSAW.Models.Display_.helpers.DisplayDTOArrayList;
import dwr.MiniaturowaTablica.api.ZTM_WARSAW.Models.Display_.helpers.WarsawDisplay;
import dwr.MiniaturowaTablica.api.ZTM_WARSAW.Models.Display_.helpers.staticDataLoader;
import dwr.MiniaturowaTablica.api.models.ztm.DisplayDTO;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZTMWarsawRepository {
    public static List<DisplayDTO> getAllDisplays() throws IOException {
        List<WarsawDisplay> displayList = new ArrayList<>();
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




}
