package dwr.MiniaturowaTablica.api.Depot;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DepotService {
    private final DepotRepository depotRepository;
    public List<Depot> getAllDepots() {
        return depotRepository.findAll();
    }
}
