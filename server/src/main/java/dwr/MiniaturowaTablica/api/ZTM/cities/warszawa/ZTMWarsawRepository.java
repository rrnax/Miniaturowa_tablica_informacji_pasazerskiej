package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa;

import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Displays.DisplayDTOList;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Displays.WarsawDisplay;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.staticDataLoader;
import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplayDTO;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ZTMWarsawRepository {
    public static DisplayDTOList displayDTOList;
    @Autowired
    private MongoOperations mongoOperations;

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


    public List<String> getIdStopsForDisplayCode(String displayCode) {
        List<String> idStops = new ArrayList<>();

        MongoCollection<Document> collection = mongoOperations.getCollection("displays");

        BasicDBObject query = new BasicDBObject();
        query.put("displayCode", displayCode);
        FindIterable<Document> cursor = collection.find(query);

        for (Document document : cursor) {
            idStops.add(WarsawDisplay.prepareIdStop(document.get("idStop1").toString()));
            if(document.getInteger("idStop2")>0) idStops.add(WarsawDisplay.prepareIdStop(document.get("idStop2").toString()));
            if(document.getInteger("idStop3")>0) idStops.add(WarsawDisplay.prepareIdStop(document.get("idStop3").toString()));
            if(document.getInteger("idStop4")>0) idStops.add(WarsawDisplay.prepareIdStop(document.get("idStop4").toString()));
        }

        return idStops;
    }

}
