package dwr.MiniaturowaTablica.api.controllers;

import com.google.gson.Gson;
import dwr.MiniaturowaTablica.api.models.Display;
import dwr.MiniaturowaTablica.api.services.DisplayService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/displays")
@AllArgsConstructor
public class DisplayController {
   private final DisplayService displayService;
   private final Gson gson;
   private final String[] CitiesWithTrainStation      = new String[] {"Torun","Gdańsk","Warszawa","Kraków","Bydgoszcz"};
   private final String[] CitiesWithPublicTransport   = new String[] {"Warszawa","Gdańsk"};
   @GetMapping("/all")
   public List<Display> fetchAllDisplays() {
      return displayService.getAllDisplays();
   }


   @GetMapping("/all/publicTransport")
   public ResponseEntity<String> fetchAllCitiesWithPublicTransport() {
      return ResponseEntity.ok(gson.toJson(CitiesWithPublicTransport));
   }
   @GetMapping("/all/trains")
   public ResponseEntity<String> fetchAllCitiesWithTrainStation() {
      return ResponseEntity.ok(gson.toJson(CitiesWithTrainStation));
   }
}
