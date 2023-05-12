package dwr.MiniaturowaTablica.api.PKP.Models.Arrivals;

import dwr.MiniaturowaTablica.api.PKP.Models.Stops.Stop;
import dwr.MiniaturowaTablica.api.PKP.Models.Stops.StopPKPRepository;
import dwr.MiniaturowaTablica.api.PKP.Models.Stops.StopPKPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleStopArrivalsService {
    @Autowired
    private SimpleStopArrivalsRepository simpleStopArrivalsRepository;


    public void saveAllSSA(List<SimpleStopArrivals> ssa) {
        simpleStopArrivalsRepository.saveAll(ssa);
    }
    public List<SimpleStopArrivals> getAllSSA() {
        return simpleStopArrivalsRepository.findAll();
    }
    public void deleteSSA(){
        simpleStopArrivalsRepository.deleteAll();
    }
}
