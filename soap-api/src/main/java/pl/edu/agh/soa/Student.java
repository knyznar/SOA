package pl.edu.agh.soa;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class Student {
    private String firstName;
    private String lastName;
    private String album;
    private String avatar;
    private List<Course> courses;


    @XmlElementWrapper(name = "courses")
    @XmlElement(name = "course")
    public List<Course> getCourses() {
        return courses;
    }

    @JsonCreator
    public Student(@JsonProperty(value = "firstName") String firstName,
                   @JsonProperty(value = "lastName") String lastName,
                   @JsonProperty(value = "album") String album,
                   @JsonProperty(value = "avatar") String avatar,
                   @JsonProperty(value = "courses") List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.album = album;
        this.avatar = avatar;
        this.courses = courses;
    }
}

