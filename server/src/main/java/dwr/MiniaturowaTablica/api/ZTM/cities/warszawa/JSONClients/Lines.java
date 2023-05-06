package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplaysRepository;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.Helpers.ZtmObject;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.Helpers.ZtmValue;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.WarsawLines;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.ZTMWarsawRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Lines {

    private static final String API_URL = "https://api.um.warszawa.pl/api/action/dbtimetable_get";
    //@Value("${dwr.apikey.warsaw}")
    private static String API_KEY = "34f08efc-3c02-486e-8fc6-b599d0ec45c3";

    @Autowired
    private DisplaysRepository displaysRepository;
    @Autowired
    @Lazy
    private ZTMWarsawRepository ztmWarsawRepository;

    public static List<WarsawLines> loadLine(String busStopId, String busStopNr) {

        try {
            URL url = new URL(API_URL + "?id=88cd555f-6f31-43ca-9de4-66c479ad5942&busstopId=" + busStopId + "&busstopNr=" + busStopNr + "&apikey=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String inputLine;
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(String.valueOf(response)).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("result");

            Gson gson = new Gson();
            Type listType = new TypeToken<List<ZtmObject>>() {}.getType();
            List<ZtmObject> ztmObjectList = gson.fromJson(jsonArray, listType);

            List<ZtmValue> ztmValueList = new ArrayList<>();
            for (ZtmObject ztmObject : ztmObjectList) {
                ztmValueList.addAll(ztmObject.getValues());
            }
            List<WarsawLines> result = new ArrayList<>();
            ztmValueList.forEach(e->{
                if(!result.contains(e.getValue()) && result.size()<2)
                result.add(new WarsawLines(busStopNr,e.getValue()));
            });
            return result;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}