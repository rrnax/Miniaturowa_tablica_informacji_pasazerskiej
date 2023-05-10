package dwr.MiniaturowaTablica.api.PKP;

import dwr.MiniaturowaTablica.api.PKP.Models.Stop;
import dwr.MiniaturowaTablica.api.PKP.Models.StopTimes;
import dwr.MiniaturowaTablica.api.PKP.Models.StopTimesWithTripInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class PKPRepository {
    @Autowired
    private PKPData pkpData;

    public List<StopTimes> AllStopTimesForStopId(String stopId){
        List<StopTimes> filteredStopTimes = pkpData.stopTimes.stream()
                .filter(stopTime -> stopTime.getStop_id().equals(stopId)).collect(Collectors.toList());
        return  filteredStopTimes;
    }

    public List<StopTimesWithTripInfo> allInfoForStop(String stopId){
        List<StopTimesWithTripInfo> filteredStopTimes = pkpData.stopTimesWithTripInfos.stream()
                .filter(stopTime -> stopTime.getStopTimes().getStop_id().equals(stopId)).collect(Collectors.toList());
        return  filteredStopTimes;
    }
    public List<Stop> getAllStops(){
        return  pkpData.stops;
    }




}
