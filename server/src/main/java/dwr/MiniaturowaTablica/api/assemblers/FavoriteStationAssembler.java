package dwr.MiniaturowaTablica.api.assemblers;

import dwr.MiniaturowaTablica.api.models.FavoriteStation;
import dwr.MiniaturowaTablica.api.models.User;
import dwr.MiniaturowaTablica.api.payload.request.NewFavoriteStopRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class FavoriteStationAssembler {

    public Long createId(List<FavoriteStation> list) {
        long id = 0;
        HashSet<Long> set = new HashSet<>();
        for (FavoriteStation el : list) {
            set.add(el.getId());
        }

        long max = list.stream().mapToLong(FavoriteStation::getId).max().orElse(0);

        for (long i = 0L; i <= max + 1; i++) {
            if (!set.contains(i)) {
                id = i;
                break;
            }
        }

        return id;
    }

    public FavoriteStation toFavoriteStation(NewFavoriteStopRequest request, User user) {
        List<FavoriteStation> list = user.getFavoriteStations().stream().toList();
        return new FavoriteStation(
                createId(list),
                request.getCityName(),
                request.getStopName(),
                request.getStopIds(),
                request.getStatus()
        );
    }
}
