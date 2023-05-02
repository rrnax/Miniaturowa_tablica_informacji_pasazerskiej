package dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Departure_.Departure;
import dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Departure_.DepartureAssembler;
import dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Departure_.DepartureDTO;
import dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Departure_.DeparturesRepository;
import dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Display_.Display;
import dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Display_.DisplayAssembler;
import dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Display_.DisplayDTO;
import dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Display_.DisplaysRepository;
import dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Stop_.StopDTO;
import dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Stop_.StopsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/ztm/gdansk")
@RestController
public class ZTMController {

   @Autowired
   DepartureAssembler departureAssembler;
   @Autowired
   DisplayAssembler displayAssembler;
   @Autowired
   StopsRepository stopsRepository;
   @Autowired
   DisplaysRepository displaysRepository;
   @Autowired
   DeparturesRepository departuresRepository;
   @Autowired
   ZTMRepository ZTMRepository;
   GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
   Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();

   @GetMapping("/displays")
   private ResponseEntity<String> getAlldisplays() { // get info about displays (przystanki)

      displaysRepository.saveAll(gson.fromJson(ZTMRepository.getAllDisplays(), new TypeToken<List<DisplayDTO>>() {
      }.getType()));
      return ResponseEntity
            .status(HttpStatus.OK)
            .body(ZTMRepository.getAllDisplays());
   }

   @GetMapping("/displays/stops") // get info about stops (slupki z przystankow)
   private ResponseEntity<String> getAllStops() {
      return ResponseEntity
            .status(HttpStatus.OK)
            .body(ZTMRepository.getAllStops());
   }

   @GetMapping("/time/{stopid}")   // get info about depratures from requested stop
   private ResponseEntity<String> getAllPlatformsByPosts(@PathVariable("stopid") int stopid) {
      return ResponseEntity
            .status(HttpStatus.OK)
            .body(ZTMRepository.getTimeDeparturesFromStop(stopid));

   }

   @GetMapping("/info/{displayCode}") // get info about departures from display (from couple of stops)
   private ResponseEntity<String> getInfoAboutLinesFromDisplay(@PathVariable("displayCode") int displayCode) {
      return ResponseEntity
            .status(HttpStatus.OK)
            .body(ZTMRepository.getInfoAboutLinesFromDisplay(displayCode));
   }

   @GetMapping("/info/{displayCode}/{lineId}")
   // get info about departures from display (from couple of stops), only for requested line
   private ResponseEntity<String> getInfoAboutLinesFromDisplay(@PathVariable("displayCode") int displayCode, @PathVariable("lineId") int lineId) {
      return ResponseEntity
            .status(HttpStatus.OK)
            .body(ZTMRepository.getInfoAboutLinesFromDisplay(displayCode, lineId));
   }

   // pamietac w postach o headerze Content-Type application/json , dane w body jako json!
   @PostMapping("/stops/addone") // add one stopdto to mongo database
   private ResponseEntity<String> addStop(@RequestBody StopDTO stopDTO) {
      stopsRepository.save(stopDTO);
      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("added successfully");
   }

   @PostMapping("/stops/addlist")
   private ResponseEntity<String> addStops(@RequestBody List<StopDTO> stopDTOs) // add list of stopdto to mongo database
   {
      stopsRepository.deleteALL();
      stopsRepository.saveAll(stopDTOs);
      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("added successfully");
   }

   @DeleteMapping("/stops/deleteall")  // delete all stopdto from mongo database
   private ResponseEntity<String> deleteStops() {
      stopsRepository.deleteALL();
      return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("deleted successfully");
   }

   @PostMapping("/displays/addone") // add one display to mongo database
   private ResponseEntity<String> addDisplay(@RequestBody Display display) {
      DisplayDTO displayDTO = displayAssembler.toDisplayDTO(display);
      displaysRepository.save(displayDTO);
      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("added successfully");
   }

   @PostMapping("/displays/addlist")  // add list of display to mongo database
   private ResponseEntity<String> addDisplays(@RequestBody List<Display> displays) {
      displaysRepository.deleteALL();
      List<DisplayDTO> displaysToSend = new ArrayList<>();

      for (Display display : displays) {
         DisplayDTO displayDTO = displayAssembler.toDisplayDTO(display);
         displaysToSend.add(displayDTO);
      }

      displaysRepository.saveAll(displaysToSend);
      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("added successfully");
   }

   @DeleteMapping("/displays/deleteall") // delete displays from mongo database
   private ResponseEntity<String> deleteDisplays() {
      displaysRepository.deleteALL();
      return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("deleted successfully");
   }

   @PostMapping("/departures/addone") // add one departureDTO to mongo database - ONLY TO DEBUG
   private ResponseEntity<String> addDeparture(@RequestBody DepartureDTO departureDTO) {
      departuresRepository.save(departureDTO);
      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("added successfully");
   }

   @PostMapping("/departures/addlist") // add list departureDTO to mongo database - ONLY TO DEBUG
   private ResponseEntity<String> addDepartures(@RequestBody List<DepartureDTO> departuresDTO) {
      departuresRepository.deleteALL();
      departuresRepository.saveAll(departuresDTO);
      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("added successfully");
   }

   @PostMapping("/departures/addlist/{stopid}") // add list departureDTO from ZTM API to mongo database - ONLY TO DEBUG
   private ResponseEntity<String> addDepartures(@PathVariable("stopid") int stopid) // TODO ogarnac czy nie wywoluje za duzo razy departureDTO
   {
      String list = ZTMRepository.getTimeDeparturesFromStop(stopid);


      List<Departure> list2_objects = gson.fromJson(list, new TypeToken<List<Departure>>() {
      }.getType());
      List<DepartureDTO> listToSend = new ArrayList<>();
      System.out.println(list2_objects.size());
      for (Departure departure : list2_objects) {
         DepartureDTO dto = departureAssembler.toDepartureDTO(departure, stopid);
         listToSend.add(dto);

      }


      departuresRepository.deleteALL();
      departuresRepository.saveAll(listToSend);
      return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("added successfully");
   }

   @DeleteMapping("/departures/deleteall") // delete all departures from mongo database
   private ResponseEntity<String> deleteDepartures() {
      departuresRepository.deleteALL();
      return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body("deleted successfully");
   }


}