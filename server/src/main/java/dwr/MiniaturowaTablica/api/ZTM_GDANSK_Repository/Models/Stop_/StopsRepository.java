package dwr.tablica.api2.ZTMApiDownload.Models.Stop_;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StopsRepository {
    void saveAll (List<StopDTO> list);
    void save (StopDTO stopDTO);
    void deleteALL();
}

@Repository
interface SpringStopsRepository extends MongoRepository<StopDTO, ObjectId>{

}

@Repository
class StopsRepositoryImpl implements StopsRepository {
    private final SpringStopsRepository springStopsRepository;
    private final MongoOperations mongoOperations;

    StopsRepositoryImpl(SpringStopsRepository springStopsRepository, MongoOperations mongoOperations) {
        this.springStopsRepository = springStopsRepository;
        this.mongoOperations = mongoOperations;
    }


    @Override
    public void saveAll(List<StopDTO> list)
    {
        springStopsRepository.saveAll(list);
    }

    @Override
    public void save(StopDTO stopDTO) {
        springStopsRepository.save(stopDTO);
    }

    @Override
    public void deleteALL() {
            springStopsRepository.deleteAll();
    }



}