package dwr.MiniaturowaTablica.api.PKP;

import dwr.MiniaturowaTablica.api.PKP.Models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class PKPData implements CommandLineRunner {
    public List<Transfer> transfers = new ArrayList<>();
    public List<Stop> stops = new ArrayList<>();
    public List<StopTimes> stopTimes = new ArrayList<>();
    public List<Trip> trips = new ArrayList<>();
    public List<StopTimesWithTripInfo> stopTimesWithTripInfos = new ArrayList<>();


    //LOAD STATIC DATA
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start PKP");
        loadTransfers();
        loadStops();
        loadStopTimes();
        loadTrips();
        loadStopTimesWithTripInfo();
        stopTimesWithTripInfos.forEach(e->{
            e.print();
        });
        System.out.println("koniec PKP");

    }


    private void loadStopTimesWithTripInfo() {
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
            File file = new File(".\\src\\main\\resources\\static\\PKPdata\\transfers.txt");
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
        try {
            // STOPS
            //stop_id,stop_name,stop_lat,stop_lon,stop_IBNR
            File file = new File(".\\src\\main\\resources\\static\\PKPdata\\stops.txt");
            Scanner scanner = new Scanner(file);
            // pomijamy pierwszą linię z nagłówkiem
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                Stop stop = new Stop(parts[0],parts[1], parts[2], parts[3]);
                stops.add(stop);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void loadStopTimes(){
        try {
            // STOP TIMES
            //trip_id,stop_sequence,stop_id,arrival_time,departure_time,platform,official_dist_traveled
            File file = new File(".\\src\\main\\resources\\static\\PKPdata\\stop_times.txt");
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
            File file = new File(".\\src\\main\\resources\\static\\PKPdata\\trips.txt");
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


}

