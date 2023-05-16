package dwr.MiniaturowaTablica.api.PKP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static dwr.MiniaturowaTablica.api.ZTM.ztmRepository.convertToJson;
@RequestMapping("/api/pkp")
@RestController
public class PKPController {
    @Autowired
    private PKPRepository pkpRepository;


    @GetMapping("/stops/{stopID}") // get all PKP stops arrivals
    private ResponseEntity<String> getTimeTable(@PathVariable("stopID") String stopID) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertToJson(pkpRepository.allInfoForStop(stopID)));
    }

    @GetMapping("/stops") // get all PKP stops
    private ResponseEntity<String> getAllStops() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertToJson(pkpRepository.getAllStops()));
    }

}

