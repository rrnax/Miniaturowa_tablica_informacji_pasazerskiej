package dwr.MiniaturowaTablica.api.PKP.Arrivals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SSAService {
    @Autowired
    private SSAMongoRepository SSAMongoRepository;


    public void saveAllSSA(List<SSA> ssa) {
        SSAMongoRepository.saveAll(ssa);
    }
    public List<SSA> getAllSSA() {
        return SSAMongoRepository.findAll();
    }
    public void deleteSSA(){
        SSAMongoRepository.deleteAll();
    }

    public void getTodaySSA(){

    }

}
