package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Lines;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LinesApiClient {
    private static final String API_URL = "https://api.um.warszawa.pl/api/action/dbtimetable_get";
    @Value("${dwr.apikey.warsaw}")
    private static String API_KEY;

    public static void loadLine(String busStopId, String busStopNr) {

        try {
            URL url = new URL(API_URL + "?id=88cd555f-6f31-43ca-9de4-66c479ad5942&busstopId=" + busStopId + "&busstopNr=" + busStopNr + "&apikey=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}