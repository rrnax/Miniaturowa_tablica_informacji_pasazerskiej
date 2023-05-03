package dwr.MiniaturowaTablica.api.ZTM;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static dwr.MiniaturowaTablica.api.ZTM.ztmRepository.convertToJson;


@RequestMapping("/api/ztm")
@RestController
public class ztmController {
    public static final List<String> citiesList = Arrays.asList("warszawa","gdansk");

    @GetMapping("/cities")
    private ResponseEntity<String> getAllCities() throws IOException { // get wszystkie miasta
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertToJson(citiesList));
    }

}
