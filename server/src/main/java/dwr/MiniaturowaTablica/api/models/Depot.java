package dwr.MiniaturowaTablica.api.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Depot {
    @Id
    private String id;
    private String name;

    public Depot(String name) {
        this.name = name;
    }
}
