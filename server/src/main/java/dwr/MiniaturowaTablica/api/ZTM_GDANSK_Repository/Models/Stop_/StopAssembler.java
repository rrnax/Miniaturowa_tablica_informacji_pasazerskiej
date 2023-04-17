package dwr.tablica.api2.ZTMApiDownload.Models.Stop_;

import org.springframework.stereotype.Service;

@Service
public class StopAssembler {

    public StopDTO toStopDTO(Stop stop)
    {
        StopDTO stopDTO = new StopDTO();


        stopDTO.setStopId(stop.getStopId());
        stopDTO.setStopCode(stop.getStopCode());
        stopDTO.setStopName(stop.getStopName());
        stopDTO.setStopShortName(stop.getStopShortName());
        stopDTO.setStopDesc(stop.getStopDesc());
        stopDTO.setDate(stop.getDate());
        stopDTO.setStopLat(stop.getStopLat());
        stopDTO.setStopLon(stop.getStopLon());

        return stopDTO;
    }

}
