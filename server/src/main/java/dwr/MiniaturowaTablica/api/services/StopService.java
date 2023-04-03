package dwr.MiniaturowaTablica.api.services;

import dwr.MiniaturowaTablica.api.models.Stop;
import dwr.MiniaturowaTablica.api.repository.StopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StopService {
    private final StopRepository stopRepository;

    public List<Stop> getAllStops(){return stopRepository.findAll();}

}
