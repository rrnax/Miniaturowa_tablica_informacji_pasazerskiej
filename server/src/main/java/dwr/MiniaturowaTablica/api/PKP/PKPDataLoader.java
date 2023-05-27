package dwr.MiniaturowaTablica.api.PKP;

import dwr.MiniaturowaTablica.api.PKP.Trains.BestTrainWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
public class PKPDataLoader implements CommandLineRunner {
    @Autowired
    private PKPRepository pkpRepository;

    @Autowired
    private BestTrainWatcher bestTrainWatcher;

    @Autowired
    private Environment env;

    private boolean reloadDB;


    //LOAD STATIC DATA
    @Override
    public void run(String... args) throws Exception {
        reloadDB = env.getProperty("db.settings.reloadPKPDB", Boolean.class);

        if(reloadDB) System.out.println("LOADING PKP DATA FROM .TXT TO DATABASE, PLEASE WAIT...");
        else System.out.println("LOADING PKP FROM DATABASE...");

        if(reloadDB){
            //1.CLEAR DB COLLECTIONS
            //2.LOAD DATA FROM TXT
            //3.SAVE DATA TO DB
            System.out.println("LOADING STOPS...");
            pkpRepository.loadStops();
            System.out.println("LOADING TRANSFERS...");
            pkpRepository.loadTransfers();
            System.out.println("LOADING STOP TIMES...");
            pkpRepository.loadStopTimes();
            System.out.println("LOADING TRIPS...");
            pkpRepository.loadTrips();
            System.out.println("LOADING STOP TIMES WITH TRIP INFO...");
            pkpRepository.loadStopTimesWithTripInfo();
            System.out.println("LOADING STOPS ARRIVALS...");
            pkpRepository.loadSimpleStopArrivals();
            System.out.println("LOADING TRAINS...");
            pkpRepository.loadTrains();
         }
        else {
            // JUST LOAD DATA FROM DB
            pkpRepository.loadStopsFromDB();
            pkpRepository.loadSimpleStopArrivalsFromDB();
            System.out.println("LOADING TRAINS...");
            pkpRepository.loadTrains();
        }
        System.out.println("PKP LOADED!");
        bestTrainWatcher.run();

    }

}

