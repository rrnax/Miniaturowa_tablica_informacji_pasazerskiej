package dwr.MiniaturowaTablica.api.PKP;

import dwr.MiniaturowaTablica.api.PKP.Trains.BestTrainWatcher;
import dwr.MiniaturowaTablica.api.PKP.Trains.TrainStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dwr.MiniaturowaTablica.api.ZTM.ztmRepository.convertToJson;
@RequestMapping("/api/pkp")
@RestController
public class PKPController {
    @Autowired
    private PKPRepository pkpRepository;

    @Autowired
    private BestTrainWatcher bestTrainWatcher;


    @GetMapping("/stops/{stopID}") // get all PKP stops arrivals
    private ResponseEntity<String> getTimeTable(@PathVariable("stopID") String stopID) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("{\"departures\": " + convertToJson(pkpRepository.allInfoForStop(stopID)).toString()+"}");
    }

    @GetMapping("/stops") // get all PKP stops
    private ResponseEntity<String> getAllStops() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertToJson(pkpRepository.getAllStops()));
    }

    @GetMapping("/trains/getBest")
    private ResponseEntity<String> getBestTrains(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertToJson(bestTrainWatcher.getBestTrains()));
    }
    @GetMapping("/trains/info")
    private ResponseEntity<String> getTrainsInfo(){
        TrainStats trainStats=pkpRepository.getTrainsInfo();
        trainStats.setAverageSpeed(String.valueOf(bestTrainWatcher.getAverageSpeed()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertToJson(trainStats));
    }

}

