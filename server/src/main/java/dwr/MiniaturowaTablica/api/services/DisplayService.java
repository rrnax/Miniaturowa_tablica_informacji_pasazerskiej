package dwr.MiniaturowaTablica.api.services;

import dwr.MiniaturowaTablica.api.models.Display;
import dwr.MiniaturowaTablica.api.repository.DisplayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DisplayService {
    private final DisplayRepository displayRepository;
    public List<Display> getAllDisplays(){return displayRepository.findAll();}
}
