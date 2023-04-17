package dwr.tablica.api2.ZTMApiDownload.Models.Departure_;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DeparturesRepository {
    void saveAll (List<DepartureDTO> list);
    void save (DepartureDTO departureDTO);


    void deleteALL();
}

@Repository
interface SpringDeparturesRepository extends MongoRepository<DepartureDTO, ObjectId>{

}

@Repository
class DeparturesRepositoryImpl implements DeparturesRepository {
    private final SpringDeparturesRepository springDeparturesRepository;
    private final MongoOperations mongoOperations;

    DeparturesRepositoryImpl(SpringDeparturesRepository springDeparturesRepository, MongoOperations mongoOperations) {
        this.springDeparturesRepository = springDeparturesRepository;
        this.mongoOperations = mongoOperations;
    }


    @Override
    public void saveAll(List<DepartureDTO> list)
    {
        springDeparturesRepository.saveAll(list);
    }

    @Override
    public void save(DepartureDTO departureDTO) {
        springDeparturesRepository.save(departureDTO);
    }

    @Override
    public void deleteALL() {
        springDeparturesRepository.deleteAll();
    }



}