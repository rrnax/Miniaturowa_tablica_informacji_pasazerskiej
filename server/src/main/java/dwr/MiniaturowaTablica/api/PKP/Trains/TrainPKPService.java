package dwr.MiniaturowaTablica.api.PKP.Trains;

import dwr.MiniaturowaTablica.api.PKP.Stops.Stop;
import dwr.MiniaturowaTablica.api.PKP.Stops.StopPKPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class TrainPKPService {

    @Autowired
    private TrainMongoRepository trainMongoRepository;

    @Autowired
    private MongoOperations mongoOperations;

    public void saveAllTrains(List<Train> trains) {
        trains.sort(Comparator.comparingInt(Train::getDistanceTraveled).reversed());
        trainMongoRepository.saveAll(trains);
    }

    public List<Train> getAllTrains() {
        return trainMongoRepository.findAll();
    }

    public void deleteAll(){
        trainMongoRepository.deleteAll();
    }

    public List<Train> getFirst10Trains() {
        Sort sort = Sort.by(Sort.Direction.DESC, "distanceTraveled");
        Pageable pageable = PageRequest.of(0, 100, sort);
        List<Train> trains = trainMongoRepository.findAll(pageable).getContent();
        return trains;
    }

    public void updateTrainSchedule(String trainId, List<Schedule> scheduleList, Schedule lastStopSchedule){
        Query query = new Query(Criteria.where("_id").is(trainId));
        Update update = new Update().set("scheduleList", scheduleList).set("lastStopSchedule", lastStopSchedule);
        mongoOperations.updateFirst(query, update, Train.class);
    }



}
