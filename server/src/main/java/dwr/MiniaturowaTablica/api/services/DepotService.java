package dwr.MiniaturowaTablica.api.services;

import dwr.MiniaturowaTablica.api.models.Depot;
import dwr.MiniaturowaTablica.api.repository.DepotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DepotService {
   private final DepotRepository depotRepository;

   public List<Depot> getAllDepots() {
      return depotRepository.findAll();
   }
}
