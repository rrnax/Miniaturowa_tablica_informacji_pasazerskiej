package dwr.MiniaturowaTablica.api.PKP.Models.Stops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StopPKPService {

    @Autowired
    private StopPKPRepository stopPKPRepository;

    public void saveAllStops(List<Stop> stops) {
        stopPKPRepository.saveAll(stops);
    }
    public List<Stop> getAllStops() {
        return stopPKPRepository.findAll();
    }
    public void deleteAll(){
        stopPKPRepository.deleteAll();
    }
}