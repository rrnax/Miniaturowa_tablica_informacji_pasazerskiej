package dwr.MiniaturowaTablica.api.PKP;

import dwr.MiniaturowaTablica.api.PKP.Arrivals.SSA;
import dwr.MiniaturowaTablica.api.PKP.Arrivals.SSAService;
import dwr.MiniaturowaTablica.api.PKP.Arrivals.StopTimes;
import dwr.MiniaturowaTablica.api.PKP.Arrivals.StopTimesWithTripInfo;
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


    @Autowired
    private StopPKPService stopPKPService;

    @Autowired
    private SSAService SSAService;

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
        Criteria dateCriteria = Criteria.where("service_id").gte(LocalDate.now().toString());
        query.addCriteria(dateCriteria);
        Criteria timeCriteria = Criteria.where("estimatedTime").gte(LocalTime.now().toString());
        query.addCriteria(timeCriteria);
        query.with(Sort.by(
                Sort.Order.asc("service_id"), // sortuj po dacie w kolejności rosnącej
                Sort.Order.asc("estimatedTime") // sortuj po czasie w kolejności rosnącej
        ));
        return mongoOperations.find(query, SSA.class);
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
}
