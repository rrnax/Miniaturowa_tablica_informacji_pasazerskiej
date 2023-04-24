package dwr.MiniaturowaTablica.api.ZTM_GDANSK_Repository.Models.Display_;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
public class Display {

    private int displayCode;

    private String name;
    private int idStop1;
    private int idStop2;
    private int idStop3;
    private int idStop4;


}