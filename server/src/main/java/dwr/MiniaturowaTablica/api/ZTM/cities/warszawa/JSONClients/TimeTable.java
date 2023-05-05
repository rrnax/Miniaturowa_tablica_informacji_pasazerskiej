package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.Helpers.ZtmObject;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.JSONClients.Helpers.ZtmValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@Component
public class TimeTable {
        @Autowired
        private Lines myLine;
        private final String API_ENDPOINT = "https://api.um.warszawa.pl/api/action/dbtimetable_get?id=e923fa0e-d96c-43f9-ae6e-60518c9f3238";

        private final String apiKey = "34f08efc-3c02-486e-8fc6-b599d0ec45c3";

        public  void getLineTimetable(String busStopId, String busStopNr, String line) throws IOException, JsonSyntaxException {
            String urlString = String.format("%s&busstopId=%s&busstopNr=%s&line=%s&apikey=%s", API_ENDPOINT, busStopId, busStopNr, line, apiKey);
            URL url = new URL(urlString);
            System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
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

            List<String> l = myLine.getAllLines(busStopId);
            l.forEach(e->{
                System.out.println(e);
            });


//            Gson gson = new Gson();
//            Type listType = new TypeToken<List<ZtmObject>>() {}.getType();
//            List<RawTimeTable> rawTimeTables = gson.fromJson(response.toString(), listType);
//            rawTimeTables.forEach(e->{
//                System.out.println(e);
//            });



        }


    }


