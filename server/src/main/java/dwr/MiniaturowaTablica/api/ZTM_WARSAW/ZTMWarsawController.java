package dwr.MiniaturowaTablica.api.ZTM_WARSAW;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dwr.MiniaturowaTablica.api.repository.DisplaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Modifier;

@RequestMapping("/api/ztm/warszawa")
@RestController
public class ZTMWarsawController {
    @Autowired
    DisplaysRepository displaysRepository;


    @GetMapping("/displays")
    private ResponseEntity<String> getAlldisplays() throws IOException { // get info about displays (przystanki)

        displaysRepository.saveAll(ZTMWarsawRepository.getAllDisplays());


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertToJson(ZTMWarsawRepository.getAllDisplays()));
    }

    private String convertToJson(Object o){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
        return gson.toJson(o);
    }
    @GetMapping("/test")
    private ResponseEntity<String> Test() throws IOException { // get info about displays (przystanki
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("test");
    }
}
