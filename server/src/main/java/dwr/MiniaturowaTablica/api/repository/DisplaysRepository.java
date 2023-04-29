package dwr.MiniaturowaTablica.api.repository;

import dwr.MiniaturowaTablica.api.models.ztm.DisplayDTO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DisplaysRepository {
    void saveAll(List<DisplayDTO> list);
    void save(DisplayDTO display);
    void deleteALL();

    List<DisplayDTO> getAll();
    Optional<DisplayDTO> findByDisplayCode(int displayCode);
}

@Repository
interface SpringDisplaysRepository extends MongoRepository<DisplayDTO, ObjectId> {
    Optional<DisplayDTO> findByDisplayCode(int displayCode);
}

@Repository
class DisplaysRepositoryImpl implements DisplaysRepository {
    private final SpringDisplaysRepository springDisplaysRepository;
    private final MongoOperations mongoOperations;

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
    public Optional<DisplayDTO> findByDisplayCode(int displayCode) {
        return springDisplaysRepository.findByDisplayCode(displayCode);
    }
}


