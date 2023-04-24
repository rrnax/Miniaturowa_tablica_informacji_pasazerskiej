package dwr.MiniaturowaTablica.api.services;

import dwr.MiniaturowaTablica.api.models.Departure;
import dwr.MiniaturowaTablica.api.repository.DepartureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DepartureService {
    private final DepartureRepository departureRepository;
    public List<Departure> getAllDepartures() {return departureRepository.findAll();}
}
