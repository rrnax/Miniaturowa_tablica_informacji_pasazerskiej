package dwr.MiniaturowaTablica.api.controllers;


import dwr.MiniaturowaTablica.api.models.FavoriteStation;
import dwr.MiniaturowaTablica.api.payload.request.NewFavoriteStopRequest;
import dwr.MiniaturowaTablica.api.services.FavoriteStationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/favorite/stop")
@AllArgsConstructor
public class FavoriteStationController {

    private final FavoriteStationService service;

    @PostMapping("/add")
    public ResponseEntity<Set<FavoriteStation>> addFavoriteStop(
            @RequestBody @Valid NewFavoriteStopRequest newFavoriteStopRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt
    ) {
        Set<FavoriteStation> addedFavorite = service.add(newFavoriteStopRequest, jwt);
        return ResponseEntity.ok(addedFavorite);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Set<FavoriteStation>> deleteFavoriteStop(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt
    ) {
        Set<FavoriteStation> deletedFavorite = service.delete(id, jwt);
        return ResponseEntity.ok(deletedFavorite);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FavoriteStation> updateFavoriteStop(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt
    ) {
        FavoriteStation updatedFavorite = service.updateStatus(id, jwt);
        return ResponseEntity.ok(updatedFavorite);
    }

    @GetMapping("/getAll/by/user")
    public ResponseEntity<Set<FavoriteStation>> getAllByUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt
    ) {
        Set<FavoriteStation> userFavorite = service.getByUser(jwt);
        return ResponseEntity.ok(userFavorite);
    }

}
