package pl.edu.agh.soa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Book {
    private String name;

    @JsonCreator
    public Book(@JsonProperty(value = "name") String name) {
        this.name = name;
    }
}
