package dwr.MiniaturowaTablica.api.PKP;

import dwr.MiniaturowaTablica.api.PKP.Arrivals.SSA;
import dwr.MiniaturowaTablica.api.PKP.Arrivals.SSAService;
import dwr.MiniaturowaTablica.api.PKP.Arrivals.StopTimes;
import dwr.MiniaturowaTablica.api.PKP.Arrivals.StopTimesWithTripInfo;
import dwr.MiniaturowaTablica.api.PKP.Trains.Train;
import dwr.MiniaturowaTablica.api.PKP.Trains.TrainPKPService;
import dwr.MiniaturowaTablica.api.PKP.Trips.Transfer;
import dwr.MiniaturowaTablica.api.PKP.Trips.Trip;
import dwr.MiniaturowaTablica.api.PKP.Stops.Stop;
import dwr.MiniaturowaTablica.api.PKP.Stops.StopPKPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PKPRepository {
    public List<Transfer> transfers = new ArrayList<>();
    public List<Stop> stops = new ArrayList<>();
    public List<StopTimes> stopTimes = new ArrayList<>();
    public List<Trip> trips = new ArrayList<>();
    public List<StopTimesWithTripInfo> stopTimesWithTripInfos = new ArrayList<>();
    public List<SSA> simpleStopArrivals = new ArrayList<>();
    public Set<String> uniqueTrainNames = new HashSet<>();
    public List<Train> trains = new ArrayList<>();

    @Autowired
    private StopPKPService stopPKPService;

    @Autowired
    private SSAService SSAService;

    @Autowired
    private TrainPKPService trainPKPServices;

    @Autowired
    private MongoOperations mongoOperations;

    public List<StopTimes> AllStopTimesForStopId(String stopId){
        List<StopTimes> filteredStopTimes =stopTimes.stream()
                .filter(stopTime -> stopTime.getStop_id().equals(stopId)).collect(Collectors.toList());
        return  filteredStopTimes;
    }

    public List<SSA> allInfoForStop(String stopId){
        List<SSA> result = new ArrayList<>();
        Query query = new Query();
        Criteria stopIdCriteria = Criteria.where("stop_id").regex("^"+stopId+"$");
        query.addCriteria(stopIdCriteria);
        LocalDate now = LocalDate.now();
        String formattedNow = now.format(DateTimeFormatter.ISO_LOCAL_DATE);
        Criteria dateCriteria = Criteria.where("service_id").is(formattedNow);
        query.addCriteria(dateCriteria);
        Criteria timeCriteria = Criteria.where("estimatedTime").gte(LocalTime.now().toString());
        query.addCriteria(timeCriteria);
        query.with(Sort.by(
                Sort.Order.asc("service_id"), // sortuj po dacie w kolejności rosnącej
                Sort.Order.asc("estimatedTime") // sortuj po czasie w kolejności rosnącej
        ));
        result = mongoOperations.find(query, SSA.class);
        Iterator<SSA> iterator = result.iterator();
        while (iterator.hasNext()) {
            SSA e = iterator.next();
            if(Integer.valueOf(e.getEstimatedTime().substring(0, 2)) > 23) {
                iterator.remove();
            }
        }
        return result;
    }

    public List<Stop> getAllStops(){
        return stops;
    }

    public void loadStopsFromDB() {
        stops = stopPKPService.getAllStops();
    }

    public void loadStopTimesWithTripInfo() {
        for (StopTimes stopTime : stopTimes ) {
            for (Trip trip : trips) {
                if (stopTime.getTrip_id().equals(trip.getTrip_id()) ) {
                    StopTimesWithTripInfo combined = new StopTimesWithTripInfo(stopTime,trip);
                    stopTimesWithTripInfos.add(combined);
                    break; // przerwanie wewnętrznej pętli for po znalezieniu pasującego obiektu
                }
            }
        }
    }

    public void loadTransfers(){
        try {
            // TRANSFERS
            File file = new File("./src/main/resources/static/PKPdata/transfers.txt");
            Scanner scanner = new Scanner(file);
            // pomijamy pierwszą linię z nagłówkiem
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String fromStopId = parts[0];
                String toStopId = parts[1];
                String fromTripId = parts[2];
                String toTripId = parts[3];
                String transferType = parts[4];
                Transfer transfer = new Transfer(fromStopId, toStopId, fromTripId, toTripId, transferType);
                transfers.add(transfer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadStops(){
        stopPKPService.deleteAll();
        try {
            // STOPS
            //stop_id,stop_name,stop_lat,stop_lon,stop_IBNR
            File file = new File("./src/main/resources/static/PKPdata/stops.txt");
            Scanner scanner = new Scanner(file);
            // pomijamy pierwszą linię z nagłówkiem
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                Stop stop;
                if(parts.length==5)
                stop = new Stop(parts[0],parts[1], parts[2], parts[3],parts[4]);
                else {
                    stop = new Stop(parts[0],parts[1], parts[2], parts[3]," ");
                }
                stops.add(stop);
            }
            Collections.sort(stops, new Comparator<Stop>() {
                @Override
                public int compare(Stop o1, Stop o2) {
                    return o1.getName().compareTo(o2.getName()); // porównaj wartości pola name
                }
            });


            stopPKPService.saveAllStops(stops);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadStopTimes(){
        try {
            // STOP TIMES
            //trip_id,stop_sequence,stop_id,arrival_time,departure_time,platform,official_dist_traveled
            File file = new File("./src/main/resources/static/PKPdata/stop_times.txt");
            Scanner scanner = new Scanner(file);
            // pomijamy pierwszą linię z nagłówkiem
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                StopTimes stoptimes = new StopTimes(parts[0],parts[1], parts[2], parts[3],parts[4],parts[5],parts[6] );
                stopTimes.add(stoptimes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadTrips(){
        try {
            // TRIPS
            File file = new File("./src/main/resources/static/PKPdata/trips.txt");
            Scanner scanner = new Scanner(file);
            // pomijamy pierwszą linię z nagłówkiem
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                Trip trip= new Trip(parts[0],parts[1], parts[2], parts[3],parts[4]);
                trips.add(trip);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadSimpleStopArrivalsFromDB() {
        simpleStopArrivals = SSAService.getAllSSA();
    }

    public void loadSimpleStopArrivals() {
        SSAService.deleteSSA();
        stopTimesWithTripInfos.forEach(e->{
            simpleStopArrivals.add(new SSA(e.getStopTimes().stop_id,e.getStopTimes().arrival_time,e.getStopTimes().departure_time,e.getStopTimes().platform,e.getTrip().getRoute_id(), e.getTrip().getService_id(), e.getTrip().getTrip_headsign(), e.getTrip().getTrip_short_name(), e.getStopTimes().official_dist_traveled));
        });
        SSAService.saveAllSSA(simpleStopArrivals);
    }


    public void loadTrains(){
        loadUniqueTrainNames();
        uniqueTrainNames.forEach(uniqueTrainName->{
            Query query = new Query();
            List<SSA> result = new ArrayList<>();
            Criteria stopIdCriteria = Criteria.where("trip_short_name").regex("^"+uniqueTrainName+"$");
            query.addCriteria(stopIdCriteria);
            LocalDate now = LocalDate.now();
            String formattedNow = now.format(DateTimeFormatter.ISO_LOCAL_DATE);
            Criteria timeCriteria = Criteria.where("service_id").is(formattedNow); // dodaj kryterium dotyczące dzisiejszej daty
            query.addCriteria(timeCriteria);
            result = mongoOperations.find(query, SSA.class);

            LocalTime currentTime = LocalTime.now();
            List<SSA> currentArrivals = new ArrayList<>();
            List<SSA> historyArriavals = new ArrayList<>();

            for (SSA ssa : result) {
                LocalTime arrivalTime;
                Integer hour;
                hour = Integer.valueOf(ssa.getArrival_time().substring(0,2));
                if(hour>23){
//                    String hourStr = String.valueOf(hour-24);
//                    if(hourStr.length()<2)
//                        hourStr = "0"+hourStr;
//                   arrivalTime = LocalTime.parse(hourStr + ssa.getArrival_time().substring(2)); // czasami api podaje czas np. 24:51:00
                    continue;

                } else arrivalTime = LocalTime.parse(ssa.getArrival_time());
                if (arrivalTime.isAfter(currentTime)) {
                    currentArrivals.add(ssa);
                } else {
                    historyArriavals.add(ssa);
                }
            }
            if(historyArriavals.size()>0)
                currentArrivals.add(0,historyArriavals.get(historyArriavals.size()-1));
            if(currentArrivals.size()>0)
            trains.add(new Train(uniqueTrainName,uniqueTrainName,"?","?",Integer.valueOf(currentArrivals.get(0).official_dist_traveled)));
        });
        trainPKPServices.deleteAll();
        trainPKPServices.saveAllTrains(trains);

    }

    public void loadUniqueTrainNames() {
        // ignore trains without proper names (for example ignore train with name "8300")
        simpleStopArrivals.forEach(e->{
            String s = e.getTrip_short_name();
            for (int i = 0; i < s.length(); i++) {
                if (!Character.isDigit(s.charAt(i))) {
                    uniqueTrainNames.add(e.getTrip_short_name());
                    break;
                }
            }
        });

    }
}