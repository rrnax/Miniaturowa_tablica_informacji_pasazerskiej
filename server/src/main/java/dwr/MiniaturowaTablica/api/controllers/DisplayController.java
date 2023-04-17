package dwr.MiniaturowaTablica.api.controllers;

import dwr.MiniaturowaTablica.api.models.Display;
import dwr.MiniaturowaTablica.api.services.DisplayService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/displays")
@AllArgsConstructor
public class DisplayController {
   private final DisplayService displayService;

   @GetMapping("/all")
   public List<Display> fetchAllDisplays() {
      return displayService.getAllDisplays();
   }
}
