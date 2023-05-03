package dwr.MiniaturowaTablica.api.ZTM.cities.gdansk.Models.Departure_;

import org.springframework.stereotype.Service;

@Service
public class DepartureAssembler {
    
    public DepartureDTO toDepartureDTO(Departure departure, int id)
    {
        DepartureDTO departureDTO = new DepartureDTO();

        departureDTO.setId(departure.getId());
        departureDTO.setDelayInSeconds(departure.getDelayInSeconds());
        departureDTO.setEstimatedTime(departure.getEstimatedTime());
        departureDTO.setHeadsign(departure.getHeadsign());
        departureDTO.setRouteId(departure.getRouteId());
        departureDTO.setScheduledTripStartTime(departure.getScheduledTripStartTime());
        departureDTO.setTripId(departure.getTripId());
        departureDTO.setStatus(departure.getStatus());
        departureDTO.setTheoreticalTime(departure.getTheoreticalTime());
        departureDTO.setTimestamp(departure.getTimestamp());
        departureDTO.setTrip(departure.getTrip());
        departureDTO.setVehicleCode(departure.getVehicleCode());
        departureDTO.setVehicleId(departure.getVehicleId());
        departureDTO.setVehicleService(departure.getVehicleService());

        departureDTO.setStopID_fromRequest(id);

        return departureDTO;
    }
        
    }
    

