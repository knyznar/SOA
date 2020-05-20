package pl.edu.agh.soa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Course {
    private String name;
    private Integer ects;

    @JsonCreator
    public Course(@JsonProperty(value = "name")String name,
                  @JsonProperty(value = "ects") Integer ects) {
        this.name = name;
        this.ects = ects;
    }
}
