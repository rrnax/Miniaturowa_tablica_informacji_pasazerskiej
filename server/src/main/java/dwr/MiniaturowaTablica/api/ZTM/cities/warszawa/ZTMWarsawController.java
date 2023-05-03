package dwr.MiniaturowaTablica.api.ZTM.cities.warszawa;

import dwr.MiniaturowaTablica.api.ZTM.Displays.DisplaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.ZTMWarsawRepository.convertToJson;

@RequestMapping("/api/ztm/warszawa")
@RestController
public class ZTMWarsawController {
    @Autowired
    DisplaysRepository displaysRepository;


    @GetMapping("/displays")
    private ResponseEntity<String> getAlldisplays() throws IOException { // get info about displays (przystanki)

        displaysRepository.saveAll(ZTMWarsawRepository.getAllDisplays());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertToJson(ZTMWarsawRepository.getAllDisplays()));
    }

    @GetMapping("/displays/{name}") // get all displays ( with different drivingDirection) with requested name
    private ResponseEntity<String> getAlldisplaysByName(@PathVariable("name") String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertToJson(displaysRepository.findAllByName(name)));
    }

//    @GetMapping("/lines/{name}") // get all displays ( with different drivingDirection) with requested name
//    private ResponseEntity<String> getAlldisplaysByName(@PathVariable("name") String name) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(convertToJson(displaysRepository.findAllByName(name)));
//    }











    @GetMapping("/test")
    private ResponseEntity<String> Test() throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("test");
    }
}
