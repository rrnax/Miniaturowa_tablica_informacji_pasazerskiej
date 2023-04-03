package dwr.MiniaturowaTablica.api.controllers;

import dwr.MiniaturowaTablica.api.models.Departure;
import dwr.MiniaturowaTablica.api.services.DepartureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 200)
@RestController
@RequestMapping("/api/departures")
@AllArgsConstructor
public class DepartureController {
   private final DepartureService departureService;

   @GetMapping("/all")
   public List<Departure> fetchAllDepots() {
      return departureService.getAllDepartures();
   }
}
