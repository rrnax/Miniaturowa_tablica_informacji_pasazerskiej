package dwr.MiniaturowaTablica.api.ZTM.cities.gdansk;

import com.google.gson.*;


import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dwr.MiniaturowaTablica.api.ZTM.cities.gdansk.Models.Departure_.*;
import dwr.MiniaturowaTablica.api.ZTM.cities.gdansk.Models.Display_.Display;
import dwr.MiniaturowaTablica.api.ZTM.cities.gdansk.Models.Display_.DisplayAssembler;
import dwr.MiniaturowaTablica.api.ZTM.cities.gdansk.Models.Display_.GeneralInfoDisplays;
import dwr.MiniaturowaTablica.api.ZTM.cities.gdansk.Models.Stop_.*;
import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplayDTO;
import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplaysRepository;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Displays.WarsawDisplay;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.*;

@Repository
public class ZTMRepository {
    @Autowired
    DepartureAssembler departureAssembler;
    @Autowired
    StopsRepository stopsRepository;
    @Autowired
    DisplaysRepository displaysRepository;
    @Autowired
    DeparturesRepository departuresRepository;
    @Autowired
    StopAssembler stopAssembler;
    @Autowired
    MongoOperations mongoOperations;

    private final int MAX_OUTPUT_VALUES = 10;
    public static HttpClient httpClientConf() {

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create HttpClient with new SSLContext.
            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMillis(3 * 1000))
                    .sslContext(sc) // SSL context 'sc' initialised as earlier
                    .build();

            return httpClient;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getAllDisplays() {
        HttpClient httpClient = httpClientConf();
        // PDF 2.5 przypisanie  słupków  przystankowych  do  tablic  należących  do  ZTM wGdańsku
        String url = "https://ckan.multimediagdansk.pl/dataset/c24aa637-3619-4dc2-a171-a23eec8f2172/resource/ee910ad8-8ffa-4e24-8ef9-d5a335b07ccb/download/displays.json";
        // Create request.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .GET()
                .build();

        //Send Request
        HttpResponse<String> response =
                null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseJson = response.body();

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

            GeneralInfoDisplays generalInfoDisplays = gson.fromJson(responseJson, GeneralInfoDisplays.class);

            if (generalInfoDisplays == null || generalInfoDisplays.getDisplays().isEmpty())
            {
                return "Brak Danych o Przystankach";
            }
            List <Display> displays = generalInfoDisplays.getDisplays();
            List<DisplayDTO> dtos = new ArrayList<>();
            DisplayAssembler displayAssembler = new DisplayAssembler();
            for (Display display : displays)
            {
                dtos.add(displayAssembler.toDisplayDTO(display));
            }


            return gson.toJson(dtos);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public static String getAllDisplays_ListWithUniqeNameByInputName(String name) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

        List<DisplayDTO> filteredDisplays = new ArrayList<>();

        try {
            // get all displays
            List<DisplayDTO> allDisplays = Arrays.asList(gson.fromJson(getAllDisplays(), DisplayDTO[].class));

            // filter by name
            for (DisplayDTO display : allDisplays) {
                if (display.getName().equals(name)) {
                    filteredDisplays.add(display);
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error while getting displays: " + e.getMessage());
        }

        if (filteredDisplays.isEmpty()) {
            return "Brak danych o przystankach o nazwie: " + name;
        }

        return gson.toJson(filteredDisplays);
    }


    public static List<String> getUniqueDisplayNames() {
        HttpClient httpClient = httpClientConf();
        String url = "https://ckan.multimediagdansk.pl/dataset/c24aa637-3619-4dc2-a171-a23eec8f2172/resource/ee910ad8-8ffa-4e24-8ef9-d5a335b07ccb/download/displays.json";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseJson = response.body();

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

            GeneralInfoDisplays generalInfoDisplays = gson.fromJson(responseJson, GeneralInfoDisplays.class);

            if (generalInfoDisplays == null || generalInfoDisplays.getDisplays().isEmpty()) {
                return new ArrayList<>();
            }

            List<String> displayNames = new ArrayList<>();
            Set<String> uniqueDisplayNames = new HashSet<>();
            DisplayAssembler displayAssembler = new DisplayAssembler();
            for (Display display : generalInfoDisplays.getDisplays()) {
                DisplayDTO dto = displayAssembler.toDisplayDTO(display);
                displayNames.add(dto.getName());
            }

            for (String displayName : displayNames) {
                if (!uniqueDisplayNames.contains(displayName)) {
                    uniqueDisplayNames.add(displayName);
                }
            }

            return new ArrayList<>(uniqueDisplayNames);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public String getAllStops ()
    {
        HttpClient httpClient = httpClientConf();
        // PDF 2.5
        String url = "https://ckan.multimediagdansk.pl/dataset/c24aa637-3619-4dc2-a171-a23eec8f2172/resource/d3e96eb6-25ad-4d6c-8651-b1eb39155945/download/stopsingdansk.json";

        // Create request.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .GET()
                .build();

        //Send Request
        HttpResponse<String> response =
                null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseJson = response.body();
            // gson initialize
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

            // full json from api goes to gson
            GeneralInfoStops generalInfoStops = gson.fromJson(responseJson, GeneralInfoStops.class);

            if (generalInfoStops == null || generalInfoStops.getStops().isEmpty())
            {
                return "Brak info o Stopach";
            }

            List<Stop> stops = generalInfoStops.getStops();
            List<StopDTO> stopDTOList = new ArrayList<>();
            for (int i = 0; i < stops.size(); i++)
            {
                Stop tmp = stops.get(i);
                StopDTO tmpDTO = stopAssembler.toStopDTO(tmp);
                stopDTOList.add(tmpDTO);
            }


            return gson.toJson(stopDTOList);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    //Argument {stopId} jest identyfikatorem słupka – wartość stopId z zasobu Lista przystanków
    // ex. value - 101
    public String getTimeDeparturesFromStop(int stopid) {
        HttpClient httpClient = httpClientConf();
        StringBuilder stringBuilder = new StringBuilder("https://ckan2.multimediagdansk.pl/departures?stopId=");
        stringBuilder.append(stopid);
        String url = stringBuilder.toString();

        // Create request.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .GET()
                .build();

        //Send Request
        HttpResponse<String> response =
                null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseJson = response.body();
            // gson initialize
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();


            // full json from api goes to gson
            GeneralInfoDepartures generalInfoDepartures = gson.fromJson(responseJson, GeneralInfoDepartures.class);
            //  System.out.println( generalInfoStops.getStops().size());
            if (generalInfoDepartures == null || generalInfoDepartures.getDepartures().isEmpty())
            {
                return "";//Brak info o odjazdach z tego slupka
            }
            else
            {
                List<Departure> departures = generalInfoDepartures.getDepartures();
                List<DepartureDTO> listToSend = new ArrayList<>();
                //System.out.println(departures.size());
                int i=0;
                for (Departure departure : departures) {

                    DepartureDTO dto = departureAssembler.toDepartureDTO(departure,stopid);
                    listToSend.add(dto);

                }
                Collections.sort(listToSend, new Comparator<DepartureDTO>() {
                    public int compare(DepartureDTO departure1, DepartureDTO departure2) {
                        return departure1.getEstimatedTime().compareTo(departure2.getEstimatedTime());
                    }
                });
                return gson.toJson(listToSend);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public String getInfoAboutLinesFromDisplay(int displayCode) {
// Create a Gson instance with pretty printing
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

        String tmp="";
        // Find the display with the given displayCode
        Optional<DisplayDTO> display = displaysRepository.findByDisplayCode(displayCode);

// If the display is found, retrieve the list of stops and departures
        if (display.isPresent()) {
            List<Integer> stopsList = new ArrayList<Integer>();
            List<DepartureDTO> odjazdy = new ArrayList<DepartureDTO>();

            // Add the stop IDs to the stopsList
            if (display.get().getIdStop1() != 0) {
                stopsList.add(display.get().getIdStop1());
            }
            if (display.get().getIdStop2() != 0) {
                stopsList.add(display.get().getIdStop2());
            }
            if (display.get().getIdStop3() != 0) {
                stopsList.add(display.get().getIdStop3());
            }
            if (display.get().getIdStop4() != 0) {
                stopsList.add(display.get().getIdStop4());
            }
            // Iterate through the stops and retrieve the departures for each stop

            for (int i = 0; i < stopsList.size(); i++) {
                tmp = getTimeDeparturesFromStop(stopsList.get(i));
                //System.out.println(tmp.toString());
                if (tmp.startsWith("[")) {
                    DepartureDTO[] dtosTmp = gson.fromJson(tmp, DepartureDTO[].class);
                    for (int j = 0; j < dtosTmp.length; j++) {
                        odjazdy.add(dtosTmp[j]);
                    }
                } else {
                    // Handle non-array response appropriately
                    return "Brak Odjazdow";
                }
            }
            // Create a JSON object with the display information, the list of stop IDs, and the list of departures
            JsonObject displayInfo = new JsonObject();
            displayInfo.addProperty("displaycode", display.get().getDisplayCode());
            displayInfo.addProperty("name", display.get().getName());

            JsonArray departuresArray = new JsonArray();
            int i=0;
            for (DepartureDTO departure : odjazdy) {
                if (i < MAX_OUTPUT_VALUES) {
                    JsonObject tmp_dep = new JsonObject();
                    tmp_dep.addProperty("id", departure.getId());
                    tmp_dep.addProperty("delayInSeconds", departure.getDelayInSeconds());
                    tmp_dep.addProperty("estimatedTime", departure.getEstimatedTime());
                    tmp_dep.addProperty("headsign", departure.getHeadsign());
                    tmp_dep.addProperty("routeId", departure.getRouteId());
                    tmp_dep.addProperty("scheduledTripStartTime", departure.getScheduledTripStartTime());
                    tmp_dep.addProperty("tripId", departure.getTripId());
                    tmp_dep.addProperty("status", departure.getStatus());
                    tmp_dep.addProperty("theoreticalTime", departure.getTheoreticalTime());
                    tmp_dep.addProperty("timestamp", departure.getTimestamp());
                    tmp_dep.addProperty("trip", departure.getTrip());
                    tmp_dep.addProperty("vehicleCode", departure.getVehicleCode());
                    tmp_dep.addProperty("vehicleId", departure.getVehicleId());
                    tmp_dep.addProperty("vehicleService", departure.getVehicleService());
                    tmp_dep.addProperty("StopID", departure.getStopID_fromRequest());
                    departuresArray.add(tmp_dep);
                    i++;
                }
            }
            displayInfo.add("departures", departuresArray);

            // Convert the list of DepartureDTO objects to a JSON string
            String json = gson.toJson(displayInfo);
            return json;// or do something else with the JSON string
        } else {
            return "Display not found.";
        }
    }
    public String getInfoAboutLinesFromDisplay(int displayCode,int lineId) {
        // Create a Gson instance with pretty printing
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

        String tmp="";
        // Find the display with the given displayCode
        Optional<DisplayDTO> display = displaysRepository.findByDisplayCode(displayCode);

// If the display is found, retrieve the list of stops and departures
        if (display.isPresent()) {
            List<Integer> stopsList = new ArrayList<Integer>();
            List<DepartureDTO> odjazdy = new ArrayList<DepartureDTO>();

            // Add the stop IDs to the stopsList
            if (display.get().getIdStop1() != 0) {
                stopsList.add(display.get().getIdStop1());
            }
            if (display.get().getIdStop2() != 0) {
                stopsList.add(display.get().getIdStop2());
            }
            if (display.get().getIdStop3() != 0) {
                stopsList.add(display.get().getIdStop3());
            }
            if (display.get().getIdStop4() != 0) {
                stopsList.add(display.get().getIdStop4());
            }
            // Iterate through the stops and retrieve the departures for each stop

            for (int i = 0; i < stopsList.size(); i++) {
                tmp = getTimeDeparturesFromStop(stopsList.get(i));
                //System.out.println(tmp.toString());
                if (tmp.startsWith("[")) {
                    DepartureDTO[] dtosTmp = gson.fromJson(tmp, DepartureDTO[].class);
                    for (int j = 0; j < dtosTmp.length; j++) {
                        odjazdy.add(dtosTmp[j]);
                    }
                } else {
                    // Handle non-array response appropriately
                    return "Brak Odjazdow";
                }
            }


            // Create a JSON object with the display information, the list of stop IDs, and the list of departures
            JsonObject displayInfo = new JsonObject();
            displayInfo.addProperty("displaycode", display.get().getDisplayCode());
            displayInfo.addProperty("name", display.get().getName());

            JsonArray departuresArray = new JsonArray();
            int i=0;
            for (DepartureDTO departure : odjazdy) {
                if (i < MAX_OUTPUT_VALUES &&  departure.getRouteId() == lineId)
                {
                    JsonObject tmp_dep = new JsonObject();
                    tmp_dep.addProperty("id", departure.getId());
                    tmp_dep.addProperty("delayInSeconds", departure.getDelayInSeconds());
                    tmp_dep.addProperty("estimatedTime", departure.getEstimatedTime());
                    tmp_dep.addProperty("headsign", departure.getHeadsign());
                    tmp_dep.addProperty("routeId", departure.getRouteId());
                    tmp_dep.addProperty("scheduledTripStartTime", departure.getScheduledTripStartTime());
                    tmp_dep.addProperty("tripId", departure.getTripId());
                    tmp_dep.addProperty("status", departure.getStatus());
                    tmp_dep.addProperty("theoreticalTime", departure.getTheoreticalTime());
                    tmp_dep.addProperty("timestamp", departure.getTimestamp());
                    tmp_dep.addProperty("trip", departure.getTrip());
                    tmp_dep.addProperty("vehicleCode", departure.getVehicleCode());
                    tmp_dep.addProperty("vehicleId", departure.getVehicleId());
                    tmp_dep.addProperty("vehicleService", departure.getVehicleService());
                    tmp_dep.addProperty("StopID",departure.getStopID_fromRequest());
                    departuresArray.add(tmp_dep);
                    i++;
                }


            }
            displayInfo.add("departures", departuresArray);


            // Convert the list of DepartureDTO objects to a JSON string
            String json = gson.toJson(displayInfo);
            return json;// or do something else with the JSON string
        } else {
            return "Display not found.";
        }

    }

    public List<Integer> getIdStopsForGdanskDisplayCode(String displayCode) {
        List<Integer> idStops = new ArrayList<>();

        MongoCollection<Document> collection = mongoOperations.getCollection("displays");

        BasicDBObject query = new BasicDBObject();
        query.put("displayCode", displayCode);
        FindIterable<Document> cursor = collection.find(query);

        for (Document document : cursor) {
            idStops.add(document.getInteger("idStop1"));
            if(document.getInteger("idStop2")>0) idStops.add(document.getInteger("idStop2"));
            if(document.getInteger("idStop3")>0) idStops.add(document.getInteger("idStop3"));
            if(document.getInteger("idStop4")>0) idStops.add(document.getInteger("idStop4"));
        }

        return idStops;
    }



}
