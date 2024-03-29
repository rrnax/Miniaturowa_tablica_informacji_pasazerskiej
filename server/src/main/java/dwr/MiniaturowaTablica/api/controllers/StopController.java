package dwr.MiniaturowaTablica.api.controllers;

import dwr.MiniaturowaTablica.api.models.Stop;
import dwr.MiniaturowaTablica.api.services.StopService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stops")
@AllArgsConstructor
public class StopController {
   private final StopService stopService;

   @GetMapping("/all")
   public List<Stop> fetchAllStops() {
      return stopService.getAllStops();
   }
}
