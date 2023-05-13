package dwr.MiniaturowaTablica.api.services;

import dwr.MiniaturowaTablica.api.assemblers.FavoriteStationAssembler;
import dwr.MiniaturowaTablica.api.models.FavoriteStation;
import dwr.MiniaturowaTablica.api.models.User;
import dwr.MiniaturowaTablica.api.payload.request.NewFavoriteStopRequest;
import dwr.MiniaturowaTablica.api.repository.UserRepository;
import dwr.MiniaturowaTablica.api.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FavoriteStationService {

    private final FavoriteStationAssembler assembler;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    public Set<FavoriteStation> add(NewFavoriteStopRequest request, String jwt) {
        String userName = jwtUtils.getUserNameFromJwtToken(jwtUtils.headerToToken(jwt));
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        FavoriteStation newFavoriteStation = assembler.toFavoriteStation(request, user);

        Set<FavoriteStation> userFavoriteStations = user.getFavoriteStations();
        userFavoriteStations.add(newFavoriteStation);

        return userRepository.save(user).getFavoriteStations();
    }

    public Set<FavoriteStation> delete(Long id, String jwt) {
        String userName = jwtUtils.getUserNameFromJwtToken(jwtUtils.headerToToken(jwt));
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Set<FavoriteStation> userStations = user.getFavoriteStations().stream()
                .filter(it -> !it.getId().equals(id))
                .collect(Collectors.toSet());
        user.setFavoriteStations(userStations);

        userRepository.save(user);
        return userStations;
    }

    public FavoriteStation updateStatus(Long id, String jwt) {
        String userName = jwtUtils.getUserNameFromJwtToken(jwtUtils.headerToToken(jwt));
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Optional<FavoriteStation> userStation = user.getFavoriteStations().stream()
                .filter(it -> it.getId().equals(id)).findFirst();
        FavoriteStation station = null;
        if (userStation.isPresent()) {
            station = userStation.get();
            station.setStatus(station.getStatus().equals(false));
        }

        Set<FavoriteStation> userStations = user.getFavoriteStations().stream()
                .filter(it -> !it.getId().equals(id))
                .collect(Collectors.toSet());
        if (userStation.isPresent())
            userStations.add(station);

        user.setFavoriteStations(userStations);
        userRepository.save(user);
        return station;
    }

    public Set<FavoriteStation> getByUser(String jwt) {
        String userName = jwtUtils.getUserNameFromJwtToken(jwtUtils.headerToToken(jwt));
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return user.getFavoriteStations();
    }
}
