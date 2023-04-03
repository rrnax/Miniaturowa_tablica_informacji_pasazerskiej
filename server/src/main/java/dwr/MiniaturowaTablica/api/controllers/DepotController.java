package dwr.MiniaturowaTablica.api.controllers;
// class For handling API requests

import dwr.MiniaturowaTablica.api.models.Depot;
import dwr.MiniaturowaTablica.api.services.DepotService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/depots")
@AllArgsConstructor
public class DepotController {

    private final DepotService depotService;

    @GetMapping("/all")
    public List<Depot> fetchAllDepots() {
        return depotService.getAllDepots();
    }
}
