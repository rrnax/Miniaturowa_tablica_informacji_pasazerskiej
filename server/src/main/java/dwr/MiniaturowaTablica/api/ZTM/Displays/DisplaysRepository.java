package dwr.MiniaturowaTablica.api.ZTM.Displays;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dwr.MiniaturowaTablica.api.ZTM.cities.warszawa.Models.Displays.WarsawDisplay;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface DisplaysRepository {
    void saveAll(List<DisplayDTO> list);
    void save(DisplayDTO display);
    void deleteALL();

    List<DisplayDTO> getAll();
    Optional<DisplayDTO> findByDisplayCode(int displayCode);
    List<DisplayDTO> findAllByNamePrefix (String prefix);
    List<DisplayDTO> findAllByName (String name);
}

@Repository
interface SpringDisplaysRepository extends MongoRepository<DisplayDTO, ObjectId> {
    Optional<DisplayDTO> findByDisplayCode(String displayCode);

}

@Repository
class DisplaysRepositoryImpl implements DisplaysRepository {

    private final SpringDisplaysRepository springDisplaysRepository;
    private final MongoOperations mongoOperations;
    @Autowired
    DisplaysRepositoryImpl(SpringDisplaysRepository springDisplaysRepository, MongoOperations mongoOperations) {
        this.springDisplaysRepository = springDisplaysRepository;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void saveAll(List<DisplayDTO> list) {
        springDisplaysRepository.saveAll(list);
    }

    @Override
    public void save(DisplayDTO display) {
        springDisplaysRepository.save(display);
    }

    @Override
    public void deleteALL() {
        springDisplaysRepository.deleteAll();
    }

    @Override
    public List<DisplayDTO> getAll() {
        return springDisplaysRepository.findAll();
    }

    @Override
    public List<DisplayDTO> findAllByNamePrefix(String prefix) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^"+prefix));
        return mongoOperations.find(query,DisplayDTO.class);
    }

    @Override
    public List<DisplayDTO> findAllByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^"+name+"$"));
        return mongoOperations.find(query,DisplayDTO.class);
    }

    @Override
    public Optional<DisplayDTO> findByDisplayCode(int displayCode) {
        return springDisplaysRepository.findByDisplayCode(String.valueOf(displayCode));
    }




}


