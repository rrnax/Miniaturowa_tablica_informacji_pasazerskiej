package dwr.MiniaturowaTablica.api.ZTM;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dwr.MiniaturowaTablica.api.ZTM.Displays.Display;
import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Modifier;
import java.util.*;

@Repository
public class ztmRepository {


    @Autowired
    private MongoOperations mongoOperations;

    public static final List<String> citiesList = Arrays.asList("warszawa","gdansk");

    public static String convertToJson(Object o){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
        return gson.toJson(o);
    }

    // get all unique names for displays
    public Map<String, List<String>> getAllUniqueDisplaysNames(String city){
        List<Display> result = new ArrayList<>();
        Query query = new Query();
        query.addCriteria(Criteria.where("city").regex("^"+city+"$"));
        List<DisplayDTO> cityDisplays = mongoOperations.find(query,DisplayDTO.class);
        // Grupowanie kodów po nazwie
        Map<String, List<String>> map = new HashMap<>();
        cityDisplays.forEach(e->{
            if (!map.containsKey(e.getName())) {
                map.put(e.getName(), new ArrayList<String>());
            }
            List<String> codeList = map.get(e.getName());
            if (!codeList.contains(e.getDisplayCode())) {
                codeList.add(e.getDisplayCode());
            }
        });

        return map;

    }



}
