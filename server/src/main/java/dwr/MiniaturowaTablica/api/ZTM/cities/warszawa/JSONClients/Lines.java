package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplaysRepository;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.Helpers.ZtmObject;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.Helpers.ZtmValue;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.ZTMWarsawRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@Component
public class Lines {

    private static final String API_URL = "https://api.um.warszawa.pl/api/action/dbtimetable_get";
    //@Value("${dwr.apikey.warsaw}")
    private static String API_KEY = "34f08efc-3c02-486e-8fc6-b599d0ec45c3";
    private static BeanFactory SpringContext;
    @Autowired
    public DisplaysRepository displaysRepository;
    @Autowired
    public ZTMWarsawRepository ztmWarsawRepository;

    public static void loadLine(String busStopId, String busStopNr) {

        try {
            URL url = new URL(API_URL + "?id=88cd555f-6f31-43ca-9de4-66c479ad5942&busstopId=" + busStopId + "&busstopNr=" + busStopNr + "&apikey=" + API_KEY);
            System.out.println(url);
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
            ztmValueList.forEach(e->{
                System.out.println(e.getValue());
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> getAllLines(String busStopId){
        return ztmWarsawRepository.getIdStopsForDisplayCode(busStopId);
    }
}