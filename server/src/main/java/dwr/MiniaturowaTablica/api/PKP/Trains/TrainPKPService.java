package dwr.MiniaturowaTablica.api.PKP.Trains;

import dwr.MiniaturowaTablica.api.PKP.Stops.Stop;
import dwr.MiniaturowaTablica.api.PKP.Stops.StopPKPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainPKPService {

    @Autowired
    private TrainMongoRepository trainMongoRepository;

    public void saveAllTrains(List<Train> trains) {
        trainMongoRepository.saveAll(trains);
    }
    public List<Train> getAllTrains() {
        return trainMongoRepository.findAll();
    }
    public void deleteAll(){
        trainMongoRepository.deleteAll();
    }
}
